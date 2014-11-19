package messaging


/**
 * Created by justusadam on 19/11/14.
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
}
