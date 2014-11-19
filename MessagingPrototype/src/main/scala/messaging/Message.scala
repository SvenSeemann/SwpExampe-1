package messaging

import java.util.Date


/**
 * Created by justusadam on 18/11/14.
 *
 * A String message that can be sent.
 * Generally the "sent" date should not be set manually
 */
class Message(val message:String, val sender:String, val recipient:Int, val sent:Date = new Date){

  var dateReceived:Option[Date] = None
  var read = false
  
  def received:Boolean = {
    dateReceived match {
      case None => false
      case Some(_) => true
    }
  }

  override def toString = {
    sender + " [" + sent.toString + "]" + ":" + message
  }

  def readMessage = {
    read = true

    message
  }
}