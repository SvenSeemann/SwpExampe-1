package messaging

import scala.collection.mutable
import messaging.errors.NoSuchUserError

/**
 * Created by justusadam on 18/11/14.
 *
 * Server Object that distributes the messages
 */
object Server {
  private val receivers:mutable.Map[Int, Inbox] = new mutable.HashMap[Int, Inbox]

  def addReceiver(receiver:Receiver) = {
    receivers += ((receiver.id, new Inbox))
  }

  def deliver(message:Message) = {
    receivers.get(message.recipient) match {
      case None => throw new NoSuchUserError(message.recipient)
      case Some(x) => x.store(message)
    }
  }

  def fetch(user:Int):Inbox = {
    receivers.get(user) match {
      case None => throw new NoSuchUserError(user)
      case Some(x) => x
    }
  }
}