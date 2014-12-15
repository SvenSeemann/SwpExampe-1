package fviv.messaging;

import org.hibernate.validator.constraints.NotEmpty;
import org.salespointframework.useraccount.UserAccount;

/**
 * Created by justusadam on 09/12/14.
 */
public class SendMessageForm {

    @NotEmpty
    private String message;

    @NotEmpty
    private UserAccount recipient;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserAccount getRecipient() {
        return recipient;
    }

    public void setRecipient(UserAccount recipient) {
        this.recipient = recipient;
    }
}
