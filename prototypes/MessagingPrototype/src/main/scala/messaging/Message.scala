package messaging

import java.time.LocalDateTime
import javax.persistence._
import org.hibernate.annotations.Type


/**
 * Created by justusadam on 18/11/14.
 *
 * A String message that can be sent.
 * Generally the "sent" date should not be set manually
 */
@Entity
class Message{

  private var message:String = _
  private var sender:Integer = _
  private var recipient:Integer = _

  @Type(`type`="org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
  var sent:LocalDateTime = LocalDateTime.now()

  @Type(`type`="org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
  var dateReceived:LocalDateTime = _
  var read = false

  @GeneratedValue
  @Id
  var id:Int = _

  def this(message:String, sender:Integer, recipient:Integer) {
    this()
    this.sender = sender
    this.recipient = recipient
    this.message = message
  }

  def getMessage = message

  def setMessage(message:String) = this.message = message

  def getSender = sender

  def setSender(sender:Integer) = this.sender = sender

  def getRecipient = recipient

  def setRecipient(recipient:Integer) = this.recipient = recipient

  def getDateReceived = dateReceived

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