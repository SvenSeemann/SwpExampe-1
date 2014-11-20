package messaging

import java.util.Date

import people.People

import scala.collection.mutable
import messaging.errors.NoSuchUserError

/**
 * Created by justusadam on 18/11/14.
 *
 * Server Object that distributes the messages
 */
object Server {
  private val receivers:mutable.Map[Int, Inbox] = mutable.Map[Int, Inbox](People.people.values.filter {
    case p: Receiver => true
    case _ => false
  }.map(e => (e.id, new Inbox)).toSeq: _*)

  def addReceiver(receiver:Receiver) = {
    receivers.contains(receiver.id) match {
      case false => receivers += ((receiver.id, new Inbox))
      case true => Unit
    }

  }

  def deliver(message:Message) = {
    receivers.get(message.recipient) match {
      case None => throw new NoSuchUserError(message.recipient)
      case Some(x) =>
        message.dateReceived match {
          case None => message.dateReceived = Option(new Date())
          case Some(_) => Unit
        }
        x.store(message)
    }
  }

  def fetch(user:Int):Inbox = {
    receivers.get(user) match {
      case None => new Inbox
      case Some(x) => x
    }
  }
}