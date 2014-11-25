package messaging

import java.time.LocalDateTime
import javax.persistence.{Id, GeneratedValue, Entity}
import org.hibernate.annotations.Type


/**
 * Created by justusadam on 18/11/14.
 *
 * A String message that can be sent.
 * Generally the "sent" date should not be set manually
 */
@Entity
class Message(val message:String, val sender:String, val recipient:Int,
              @Type(`type`="org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
              var sent:LocalDateTime = LocalDateTime.now()){

  @Type(`type`="org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
  var dateReceived:LocalDateTime = _
  var read = false

  @GeneratedValue
  @Id
  var id:Int = _

  def received:Boolean = {
    read
  }

  def getRecipient = recipient

  override def toString = {
    sender + " [" + sent.toString + "]" + ": " + message
  }

  def readMessage = {
    read = true

    message
  }
}