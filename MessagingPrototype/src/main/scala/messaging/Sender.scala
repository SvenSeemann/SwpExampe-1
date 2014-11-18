package messaging

trait Sender{
  def send(message:String, recipient:Int) = {
    Server.deliver(new Message(message), recipient)
  }
}