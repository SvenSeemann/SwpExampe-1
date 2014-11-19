package messaging

/**
 * Created by justusadam on 18/11/14.
 *
 * Base trait for a receiver of Messages
 */
trait Receiver {
  def id:Int

  def fetchMessages:List[Message] = {
    Server.fetch(id)
  }
}
