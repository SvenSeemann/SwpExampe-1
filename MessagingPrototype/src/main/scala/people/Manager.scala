package people

/**
 * Created by justusadam on 18/11/14.
 *
 * Testclass for Receiver
 */

import messaging.Receiver


class Manager(name:String) extends Employee(1, name) with Receiver{
}
