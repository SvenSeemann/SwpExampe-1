package people

import messaging.Receiver

/**
 * Created by justusadam on 20/11/14.
 */
class Office(_id:Int, _name:String) extends Employee(_id, _name) with Receiver{

}
