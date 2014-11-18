package messaging

import java.util.Date

class Message(val message:String, val date:Date = new Date){

  override def toString = {
    date.toString + ":" + message
  }
}