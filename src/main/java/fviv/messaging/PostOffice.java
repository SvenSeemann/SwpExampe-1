package fviv.messaging;

import fviv.user.UserRepository;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by justusadam on 09/12/14.
 */
@Component
public class PostOffice {

    private final UserRepository users;

    private final MessageRepository repo;

    public static final Role senderRole = new Role("MESSAGE_SENDER");

    public static final Role receiverRole = new Role("MESSAGE_RECEIVER");

    @Autowired
    public PostOffice(UserRepository users, MessageRepository repo) {
        this.users = users;
        this.repo = repo;
    }

    public Iterable<Message> getMessages(UserAccountIdentifier userId) {
        Optional<UserAccount> x = users.findOne(userId);
        if (x.isPresent()) return getMessages(x.get());
        else throw new SecurityException();
    }

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

    public List<Message> getMessages(UserAccount user, LocalDateTime fromDate) {
        Iterable<Message> messages = getMessages(user);
        List<Message> out = new LinkedList<>();

        for (Message message : messages) {
            if (message.getDate().isAfter(fromDate)){
                out.add(message);
            }
        }
        return out;
    }

    public boolean sendMessage(UserAccount sender, UserAccount receiver, String message) {
        if (canSend(sender) && canReceive(receiver)){
            repo.save(new Message(message, sender, receiver));
            return true;
        }
        return false;
    }

    public boolean hasRole(UserAccount user, Role role) {
        return user.hasRole(role);
    }

    public boolean canSend(UserAccount user) {
        return hasRole(user, senderRole);
    }

    public boolean canReceive(UserAccount userId) {
        return hasRole(userId, receiverRole);
    }

    public Iterable<UserAccount> getRecipients(){
        List<UserAccount> acc = new LinkedList<>();

        for (UserAccount user : users.findAll())  if (user.hasRole(receiverRole)) acc.add(user);

        return acc;

    }

}
