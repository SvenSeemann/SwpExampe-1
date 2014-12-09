package fviv.messaging

import fviv.messaging.errors.NoSuchUserError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean

import scala.collection.JavaConversions


/**
 * Created by justusadam on 20/11/14.
 */
@Bean
@Autowired
class DBServer(val postOffice:PostOffice) extends Server{

  override def deliver(message: Message) = {
    postOffice.save(message)
    true
  }

  override def fetch(user: Int): Inbox = new ListInbox(JavaConversions.iterableAsScalaIterable(postOffice.findByRecipient(user)))
}