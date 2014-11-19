package messaging

trait Sender{
  def name:String

  def send(message:String, recipient:Int) = {
    Server.deliver(new Message(message, name, recipient))
  }
}