package messaging

import scala.collection.mutable


/**
 * Created by justusadam on 19/11/14.
 *
 * Container for wrapping messages sent to a user
 */
class Inbox {

  var messages:List[Message] = List[Message]()

  def store(message:Message):Boolean = {
    messages = messages :+ message
    true
  }

  def fetchAll:List[Message] = {
    messages.toList
  }

  def delete(message:Message):Unit = {
    messages = messages.filter({a => a != message})
  }

  def hasNewMessages = {
    messages.nonEmpty
  }

  def fetchNew = {
    messages.filter({x => !x.read})
  }
}
