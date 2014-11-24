package messaging

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean


/**
 * Created by justusadam on 20/11/14.
 */
@Bean
@Autowired
abstract class DBServer(val repo:PostOffice) extends Server{

  override def deliver(message: Message) = true

  override def fetch(user: Int): Inbox

  override def addReceiver(receiver: Receiver): Unit = {}
}