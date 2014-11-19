package messaging

import scala.collection.mutable

/**
 * Created by justusadam on 18/11/14.
 */
object Server {
  private val receivers:mutable.Map[Int, Inbox] = new mutable.HashMap[Int, Inbox]

  def addReceiver(receiver:Receiver) = {
    receivers += ((receiver.id, new Inbox))
  }

  def deliver(message:Message) = {
    receivers.get(message.recipient) match {
      case None => false
      case Some(x) => x.store(message)
        true
    }
  }

  def fetch(user:Int):List[Message] = {
    receivers.get(user) match {
      case None => List[Message]() // TODO return something better
      case Some(x) => x.fetchAll
    }
  }
}