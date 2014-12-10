package fviv.messaging


/**
 * Created by justusadam on 19/11/14.
 *
 * Container for wrapping messages sent to a user
 */
class ListInbox(var messages:List[Message] = List[Message]()) extends Inbox{

  def this(messages:Iterable[Message]) = {
    this(messages.toList)
  }

  def store(message:Message):Boolean = {
    messages = messages :+ message
    true
  }

  def fetchAll:List[Message] = {
    messages.toList
  }

  def delete(message:Message):Unit = {
    messages = messages filter {a => a != message}
  }

  def hasNewMessages = {
    messages.nonEmpty
  }

  def fetchNew = {
    messages filter {x => !x.read}
  }
}
