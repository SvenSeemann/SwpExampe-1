package fviv.user;

import org.salespointframework.useraccount.Role;

/**
 * @author justusadam
 */
public abstract class Roles {
    /**
     * Reference to the role a user needs to have in order to send messages.
     */
    public static final Role sender = new Role("MESSAGE_SENDER");

    /**
     * Reference to the role a user needs to have in order to receive messages.
     */
    public static final Role receiver = new Role("MESSAGE_RECEIVER");

    public static final Role manager = new Role("ROLE_MANAGER");

    public static final Role boss = new Role("ROLE_BOSS");

    public static final Role employee = new Role("ROLE_EMPLOYEE");
    
    public static final Role caterer = new Role("ROLE_CATERER");
    
    public static final Role guest = new Role("ROLE_GUEST");

    public static final Role leader = new Role("ROLE_LEADER");
}
