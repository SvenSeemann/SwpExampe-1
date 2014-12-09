package fviv.messaging;

import org.salespointframework.core.SalespointRepository;

/**
 * Created by justusadam on 09/12/14.
 */
public interface MessageRepository extends SalespointRepository<Message, Integer>{
    public Iterable<Message> findByRecipient(long recipient);
}
