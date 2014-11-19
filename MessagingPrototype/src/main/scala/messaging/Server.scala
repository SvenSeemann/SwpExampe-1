package messaging

import scala.collection.mutable

/**
 * Created by justusadam on 18/11/14.
 */
object Server {
  private val receivers:mutable.Map[Int, mutable.MutableList[Message]] = new mutable.HashMap[Int, mutable.MutableList[Message]]

  def addReceiver(receiver:Receiver) = {
    receivers += ((receiver.id, new mutable.MutableList[Message]))
  }

  def deliver(message:Message) = {
    receivers.get(message.recipient) match {
      case None => false
      case Some(x) => x += message
        true
    }
  }

  def fetch(user:Int) = {
    receivers.get(user)
  }
}