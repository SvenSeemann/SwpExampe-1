package fviv.messaging;

import fviv.user.UserRepository;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author justusadam
 * @version 0.3
 */
@Component
public class PostOffice {

    /**
     * Reference to the UserRepository, used for authorization and authentication.
     */
    private final UserRepository users;

    /**
     * Reference to the persistent storage for the messages.
     */
    private final MessageRepository repo;

    /**
     * Reference to the role a user needs to have in order to send messages.
     */
    public static final Role senderRole = new Role("MESSAGE_SENDER");

    /**
     * Reference to the role a user needs to have in order to receive messages.
     */
    public static final Role receiverRole = new Role("MESSAGE_RECEIVER");

    @Deprecated
    private static final ZoneId gmt = ZoneId.of("GMT");

    @Autowired
    public PostOffice(UserRepository users, MessageRepository repo) {
        this.users = users;
        this.repo = repo;
    }

    /**
     * Get the entire contents of a users Inbox/all Messages sent to user
     *
     * <p>This method will throw an exception if the user does not own the {@link #senderRole} thus removing the role
     * from a user that previously owned said role will result in the user being unable to retrieve any messages</p>
     *
     * @throws java.lang.SecurityException if user is not allowed to receive Messages.
     * @param user user for which to retrieve the messages
     * @return list of messages
     */
    public List<Message> getMessages(UserAccount user) {
        if (canReceive(user)) {
            List<Message> out = new LinkedList<>();
            for (Message message : repo.findByRecipient(user)) {
                out.add(message);
            }
            return out;
        } else {
            throw new SecurityException();
        }
    }

    /**
     * @inheritDoc
     */
    public List<Message> getMessages(UserAccount user, ZonedDateTime fromDate) {
        Iterable<Message> messages = getMessages(user);
        List<Message> out = new LinkedList<>();

        for (Message message : messages) {
            if (message.getDate().isAfter(fromDate)){
                System.out.println(message.getDate().toString() + " is after " + fromDate.toString());
                out.add(message);
            }
        }
        return out;
    }

    /**
     * Takes a message String and saves it in the database
     *
     * <p>Fails if either sender or receiver does not posses the required permissions to perform this action</p>
     * <p>Note that this method will fail without an exception returning false.</p>
     *
     * @param sender UserAccount of the person sending the message
     * @param receiver UserAccount of the person receiving the message
     * @param message String with the message contents
     * @return boolean indicating whether sending the message succeeded or failed
     */
    public boolean sendMessage(UserAccount sender, UserAccount receiver, String message) {
        if (canSend(sender) && canReceive(receiver)){
            repo.save(new Message(message, sender, receiver));
            return true;
        }
        return false;
    }

    /**
     * Takes a message String and saves it in the database
     *
     * <p>Fails if either sender or receiver does not posses the required permissions to perform this action.</p>
     * <p>Also fails if the recipient UserAccount is not in the database. 'receiver' will get the user by his
     * {@link org.salespointframework.useraccount.UserAccountIdentifier}</p>
     * <p>Note that this method will fail without an exception returning false.</p>
     *
     * @param sender UserAccount of the person sending the message
     * @param receiver String representing the recipients user identifier
     * @param message String with the message contents
     * @return boolean indicating whether sending the message succeeded or failed
     */
    public boolean sendMessage(UserAccount sender, String receiver, String message) {
        Optional<UserAccount> rec = users.findOne(new UserAccountIdentifier(receiver));
        return rec.isPresent() && sendMessage(sender, rec.get(), message);
    }

    /**
     * Helper method to check if user possesses the role.
     *
     * <p>This is a thin wrapper around Salespoints {@link org.salespointframework.useraccount.UserAccount#hasRole}</p>
     * @param user UserAccount in question
     * @param role Role in question
     * @return boolean indicating whether the user has the role in question
     */
    public boolean hasRole(UserAccount user, Role role) {
        return user.hasRole(role);
    }

    /**
     * Helper Method to check if the user can send messages.
     *
     * <p>Convenience method wrapping {@link #hasRole} with {@link #senderRole}</p>
     *
     * @param user user in question
     * @return boolean indicating whether the user can send messages
     */
    public boolean canSend(UserAccount user) {
        return hasRole(user, senderRole);
    }

    /**
     * Helper Method to check if the user can receive messages.
     *
     * <p>Convenience method wrapping {@link #hasRole} with {@link #receiverRole}</p>
     *
     * @param user user in question
     * @return boolean indicating whether the user can receive messages
     */
    public boolean canReceive(UserAccount user) {
        return hasRole(user, receiverRole);
    }

    /**
     * Get all recipients requestingUser can reach.
     *
     * @throws java.lang.SecurityException
     * @param requestingUser UserAccount of the user reaching out
     * @return List of recipients
     */
    public List<UserAccount> getRecipients(UserAccount requestingUser){
        if (!canSend(requestingUser)) throw new SecurityException();
        List<UserAccount> acc = new LinkedList<>();

        for (UserAccount user : users.findAll())  if (user.hasRole(receiverRole)) acc.add(user);

        return acc;
    }

}
