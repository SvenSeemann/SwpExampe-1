package messaging

import java.sql.Date
import javax.persistence.{OneToOne, Id, GeneratedValue, Entity}


/**
 * Created by justusadam on 18/11/14.
 *
 * A String message that can be sent.
 * Generally the "sent" date should not be set manually
 */
@Entity
class Message(val message:String, val sender:String, val recipient:Int, @OneToOne val sent:Date = new Date(new java.util.Date().getTime)){
  @OneToOne
  var dateReceived:Date = _
  var read = false

  @GeneratedValue
  @Id
  var id:Int = _

  def received:Boolean = {
    read
  }

  override def toString = {
    sender + " [" + sent.toString + "]" + ": " + message
  }

  def readMessage = {
    read = true

    message
  }
}