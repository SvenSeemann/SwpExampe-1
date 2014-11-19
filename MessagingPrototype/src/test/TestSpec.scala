import messaging.Server
import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by justusadam on 18/11/14.
 */
class TestSpec extends FlatSpec with Matchers{

  "The sender" should "send a message to the Server" in {
    val sender = new Caterer
    val receiver = new Manager
    val testmessage = "Testmessage"
    Server.addReceiver(receiver)
    sender send (testmessage, receiver.id) should be (right = true)
    receiver.fetchMessages.get(0).message should be (testmessage)
    print(receiver.fetchMessages)
  }
}
