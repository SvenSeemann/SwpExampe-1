package messaging

/**
 * Created by justusadam on 18/11/14.
 */
trait Receiver {
  def id:Int

  def fetchMessages = {
    Server.fetch(id)
  }
}
