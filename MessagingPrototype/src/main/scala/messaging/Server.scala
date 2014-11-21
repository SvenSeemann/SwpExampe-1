package messaging

/**
 * Created by justusadam on 18/11/14.
 *
 * Server Object that distributes the messages
 */
abstract class Server {
  def deliver(message:Message):Boolean

  def addReceiver(receiver:Receiver)

  def fetch(user:Int):Inbox
}


object Server extends Server{
  private val proxy = new MapServer

  override def deliver(message: Message) = {
    proxy deliver message
    true
  }

  override def fetch(user: Int): Inbox = proxy.fetch(user)

  override def addReceiver(receiver: Receiver): Unit = proxy addReceiver receiver
}