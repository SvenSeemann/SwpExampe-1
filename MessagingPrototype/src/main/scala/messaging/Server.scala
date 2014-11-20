package messaging

/**
 * Created by justusadam on 18/11/14.
 *
 * Server Object that distributes the messages
 */
abstract class Server {
  def deliver(message:Message)

  def addReceiver(receiver:Receiver)

  def fetch(user:Int):ListInbox
}


object Server extends Server{
  private val proxy = new MapServer

  override def deliver(message: Message): Unit = proxy.deliver(message)

  override def fetch(user: Int): Inbox = proxy.fetch(user)

  override def addReceiver(receiver: Receiver): Unit = proxy.addReceiver(receiver)
}