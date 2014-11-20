package messaging

/**
 * Created by justusadam on 18/11/14.
 *
 * Base trait for a receiver of Messages
 */
trait Receiver {
  def id:Int

  def fetchMessages:List[Message] = {
    getInbox.fetchAll
  }

  def getInbox:ListInbox = {
    Server.fetch(id)
  }
}
