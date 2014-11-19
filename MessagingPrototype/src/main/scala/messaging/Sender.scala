package messaging


/**
 * Created by justusadam on 18/11/14.
 *
 * Base Trait for a sender of messages
 */
trait Sender{
  def name:String

  def send(message:String, recipient:Int) = {
    Server.deliver(new Message(message, name, recipient))
  }
}