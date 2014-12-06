package messaging

import javax.persistence.Entity

/**
 * Created by justusadam on 18/11/14.
 *
 * Testclass for Receiver
 */
@Entity
class Manager(name:String) extends Employee(name, true, true){
}
