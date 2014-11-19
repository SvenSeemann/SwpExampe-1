package messaging

import java.util.Date


/**
 * Created by justusadam on 18/11/14.
 *
 * A String message that can be sent.
 */
class Message(val message:String, val sender:String, val recipient:Int, val date:Date = new Date){

  override def toString = {
    date.toString + ":" + message
  }
}