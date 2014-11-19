package messaging

/**
 * Created by justusadam on 18/11/14.
 */
trait Receiver {
  def id:Int

  def fetchMessages:List[Message] = {
    Server.fetch(id)
  }
}
