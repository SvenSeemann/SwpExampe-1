package messaging;

import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * Created by justusadam on 09/12/14.
 */
@Entity
public class Message {

    @GeneratedValue
    @Id
    private long id;

    private String message;

    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
    private LocalDateTime dateSent;

    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
    private LocalDateTime dateReceived;

    private long sender;

    private long receiver;

    private boolean read = false;

    @Deprecated
    public Message(){}

    public Message(String message, LocalDateTime dateSent, long sender, long receiver) {
        this.message = message;
        this.dateSent = dateSent;
        this.sender = sender;
        this.receiver = receiver;
    }

    public Message(String message, long sender, long receiver) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.dateSent = LocalDateTime.now();
    }

    public LocalDateTime getDateSent() {
        return dateSent;
    }

    public LocalDateTime getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(LocalDateTime dateReceived) {
        this.dateReceived = dateReceived;
    }

    public long getSender() {
        return sender;
    }

    public long getReceiver() {
        return receiver;
    }

    public long getId() {
        return id;
    }

    public String readMessage(){
        this.read = true;
        return this.message;
    }

    public boolean isRead() {
        return read;
    }

    @Override
    public String toString(){
        return sender + "  [" + dateReceived.toString() + "]  " + message;
    }
}
