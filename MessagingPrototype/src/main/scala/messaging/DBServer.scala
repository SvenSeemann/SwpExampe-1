package messaging

/**
 * Created by justusadam on 20/11/14.
 */
class DBServer extends Server{
  override def deliver(message: Message): Unit = new ListInbox()

  override def fetch(user: Int): Inbox = ???

  override def addReceiver(receiver: Receiver): Unit = ???
}
