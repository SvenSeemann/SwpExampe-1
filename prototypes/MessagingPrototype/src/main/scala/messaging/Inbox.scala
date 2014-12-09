package fviv.messaging

/**
 * Created by justusadam on 21/11/14.
 */
trait Inbox {
  def store(message:Message):Boolean

  def fetchAll:List[Message]

  def delete(message:Message):Unit

  def hasNewMessages:Boolean

  def fetchNew:List[Message]
}
