package fviv.messaging;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by justusadam on 09/12/14.
 */
public class SendMessageForm {

    @NotEmpty
    private String message;

    @NotEmpty
    private String recipient;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
