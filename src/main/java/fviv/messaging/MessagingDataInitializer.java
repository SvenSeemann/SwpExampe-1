package fviv.messaging;

import fviv.user.UserRepository;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by justusadam on 09/12/14.
 */
@Component
public class MessagingDataInitializer implements DataInitializer {

    private UserRepository userRepository;

    private UserAccountManager userAccountManager;

    @Autowired
    public MessagingDataInitializer(UserRepository userRepository, UserAccountManager userAccountManager){
        this.userRepository = userRepository;
        this.userAccountManager = userAccountManager;
    }

    @Override
    public void initialize() {
        String password = "123";

        List<UserAccount> users = new LinkedList<>();
        users.add(create_user("jeremy", password, "Jeremy", "Kyle", PostOffice.receiverRole));
        users.add(create_user("john", password, "John", "Doe", PostOffice.senderRole));
        users.add(create_user("james", password, "James", "de Franco"));
        users.add(create_user("derp", password, "Derp", "McDerpson", PostOffice.receiverRole, PostOffice.senderRole));
        users.add(create_user("lilly", password, "Lilly", "Evans", PostOffice.senderRole));
        users.add(create_user("steve", password, "Steve", "Jobs", PostOffice.receiverRole, PostOffice.senderRole));
        users.add(create_user("stefan", password, "Stefan", "Gumhold", PostOffice.receiverRole));
        users.add(create_user("gerhard", password, "Gerhard", "Weber", PostOffice.senderRole));

        users.forEach(userAccountManager::save);
    }

    private UserAccount create_user(String identifier, String password, String firstname, String lastname, Role...roles) {
        UserAccount user = userAccountManager.create(identifier, password, roles);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        return user;
    }
}
