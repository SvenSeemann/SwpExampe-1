package messaging;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by justusadam on 09/12/14.
 */
public class SendMessageForm {

    @NotEmpty
    private String message;

    @NotEmpty
    private long sender;

    @NotEmpty
    private long receiver;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getSender() {
        return sender;
    }

    public void setSender(long sender) {
        this.sender = sender;
    }

    public long getReceiver() {
        return receiver;
    }

    public void setReceiver(long receiver) {
        this.receiver = receiver;
    }
}
