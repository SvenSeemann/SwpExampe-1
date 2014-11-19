package messaging

import java.util.Date


/**
 * Created by justusadam on 18/11/14.
 *
 * A String message that can be sent.
 */
class Message(val message:String, val sender:String, val recipient:Int, val sent:Date = new Date){

  var received:Option[Date] = None
  var read = false

  override def toString = {
    sender + " [" + sent.toString + "]" + ":" + message
  }

  def readMessage = {
    read = true

    message
  }
}