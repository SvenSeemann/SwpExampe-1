package messaging

/**
 * Created by justusadam on 20/11/14.
 */
abstract class DBServer extends Server{
  override def deliver(message: Message) = true

  override def fetch(user: Int): Inbox

  override def addReceiver(receiver: Receiver): Unit = {}
}
