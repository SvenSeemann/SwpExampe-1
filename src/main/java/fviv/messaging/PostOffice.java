package fviv.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import fviv.user.Role;
import fviv.user.User;
import fviv.user.UserRepository;

import java.util.Optional;
import java.lang.Iterable;

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

    public Iterable<Message> getMessages(long userId) {
        Optional<User> x = users.findOne(userId);
        if (x.isPresent()) return getMessages(x.get());
        else throw new SecurityException();
    }

    public Iterable<Message> getMessages(User user) {
        if (canReceive(user)) {
            return repo.findByRecipient(user.getId());
        } else {
            throw new SecurityException();
        }
    }

    public boolean sendMessage(long sender, long receiver, String message) {
        if (canSend(sender) && canReceive(receiver)){
            repo.save(new Message(message, sender, receiver));
            return true;
        }
        return false;
    }

    public boolean hasRole(User user, Role role) {
        return user.getRoles().contains(role);
    }

    public boolean hasRole(long userId, Role role) {
        Optional<User> x = users.findOne(userId);

        return x.isPresent() && hasRole(x.get(), role);
    }

    public boolean canSend(User user) {
        return hasRole(user, senderRole);
    }

    public boolean canSend(long userId) {
        return hasRole(userId, senderRole);
    }

    public boolean canReceive(User user) {
        return hasRole(user, receiverRole);
    }

    public boolean canReceive(long userId) {
        return hasRole(userId, receiverRole);
    }

}
