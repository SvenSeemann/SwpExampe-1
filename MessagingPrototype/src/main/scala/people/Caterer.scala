package people

import messaging.Sender

/**
 * Created by justusadam on 18/11/14.
 *
 * Testclass or sender
 */
class Caterer(id:Int, name:String) extends Employee(id, name) with Sender{
}
