package fviv.messaging;

import org.salespointframework.core.SalespointRepository;
import org.salespointframework.useraccount.UserAccount;

/**
 * Created by justusadam on 09/12/14.
 */
public interface MessageRepository extends SalespointRepository<Message, Long>{
    public Iterable<Message> findByRecipient(UserAccount recipient);
}
