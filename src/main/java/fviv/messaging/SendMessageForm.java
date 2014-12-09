package fviv.messaging;

import org.hibernate.validator.constraints.NotEmpty;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountIdentifier;

/**
 * Created by justusadam on 09/12/14.
 */
public class SendMessageForm {

    @NotEmpty
    private String message;

    @NotEmpty
    private UserAccountIdentifier sender;

    @NotEmpty
    private UserAccount receiver;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserAccountIdentifier getSender() {
        return sender;
    }

    public void setSender(UserAccountIdentifier sender) {
        this.sender = sender;
    }

    public UserAccount getReceiver() {
        return receiver;
    }

    public void setReceiver(UserAccount receiver) {
        this.receiver = receiver;
    }
}
