package messaging

import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by justusadam on 18/11/14.
 *
 * Server Object that distributes the messages
 */
abstract class Server {
  def deliver(message:Message):Boolean

  def fetch(user:Int):Inbox
}


object Server extends Server{

  private var _proxy:Server = _

  def proxy = _proxy

  @Autowired
  def proxy_=(server:Server) = {
    _proxy = server
  }

  override def deliver(message: Message) = {
    proxy deliver message
    true
  }

  override def fetch(user: Int): Inbox = proxy.fetch(user)
}