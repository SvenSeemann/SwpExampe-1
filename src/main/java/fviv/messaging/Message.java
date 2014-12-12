package fviv.messaging;

import org.hibernate.annotations.Type;
import org.salespointframework.useraccount.UserAccount;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.ZonedDateTime;

/**
 * Created by justusadam on 09/12/14.
 */
@Entity
public class Message {

    @GeneratedValue
    @Id
    private long id;

    private String message;

    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
    private ZonedDateTime date;

    @OneToOne
    private UserAccount sender;

    @OneToOne
    private UserAccount recipient;

    private boolean read = false;

    @Deprecated
    public Message(){}

    public Message(String message, ZonedDateTime date, UserAccount sender, UserAccount recipient) {
        this.message = message;
        this.date = date;
        this.sender = sender;
        this.recipient = recipient;
    }

    public Message(String message, UserAccount sender, UserAccount recipient) {
        this.message = message;
        this.date = ZonedDateTime.now();
        this.sender = sender;
        this.recipient = recipient;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public UserAccount getSender() {
        return sender;
    }

    public UserAccount getRecipient() {
        return recipient;
    }

    public long getId() {
        return id;
    }

    public String readMessage() {
        this.read = true;
        return this.message;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isRead() {
        return read;
    }

    @Override
    public String toString(){
        return sender + "  [" + date.toString() + "]  " + message;
    }
}
