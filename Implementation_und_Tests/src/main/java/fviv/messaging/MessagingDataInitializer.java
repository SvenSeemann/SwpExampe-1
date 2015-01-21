package fviv.messaging;

import fviv.user.Roles;
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
        users.add(create_user("jeremy", password, "Jeremy", "Kyle", Roles.receiver));
        users.add(create_user("john", password, "John", "Doe", Roles.sender));
        users.add(create_user("james", password, "James", "de Franco"));
        users.add(create_user("derp", password, "Derp", "McDerpson", Roles.receiver, Roles.sender));
        users.add(create_user("lilly", password, "Lilly", "Evans", Roles.sender));
        users.add(create_user("steve", password, "Steve", "Jobs", Roles.receiver, Roles.sender));
        users.add(create_user("stefan", password, "Stefan", "Gumhold", Roles.receiver));
        users.add(create_user("gerhard", password, "Gerhard", "Weber", Roles.sender));

        users.forEach(userAccountManager::save);
    }

    private UserAccount create_user(String identifier, String password, String firstname, String lastname, Role...roles) {
        UserAccount user = userAccountManager.create(identifier, password, roles);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        return user;
    }
}
