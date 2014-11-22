package messaging

import java.util.Date

import messaging.errors.NoSuchUserError
import people.People

import scala.collection.mutable

/**
 * Created by justusadam on 20/11/14.
 */
class MapServer extends Server{
  private val receivers:mutable.Map[Int, Inbox] = mutable.Map[Int, Inbox](People.people.values.filter {
    case p: Receiver => true
    case _ => false
  }.map(e => (e.id, new ListInbox)).toSeq: _*)

  def addReceiver(receiver:Receiver) = {
    receivers contains receiver.id match {
      case false => receivers += ((receiver.id, new ListInbox))
      case true =>
    }

  }

  def deliver(message:Message) = {
    receivers get message.recipient  match {
      case None => People.get(message.recipient) match {
        case Some(p:Receiver) =>
          addReceiver(p)
          deliver(message)
        case None => throw new NoSuchUserError
      }
      case Some(x) =>
        message.dateReceived match {
          case None => message.dateReceived = Option(new Date())
          case Some(_) =>
        }
        x.store(message)
    }
  }

  def fetch(user:Int):Inbox = {
    receivers get user match {
      case None => new ListInbox
      case Some(x) => x
    }
  }
}
