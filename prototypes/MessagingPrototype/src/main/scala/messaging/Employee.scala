package messaging

import javax.persistence.{Entity, GeneratedValue, Id}

/**
 * Created by justusadam on 19/11/14.
 */
@Entity
class Employee{

  @Id
  @GeneratedValue
  private var id:Int = _
  private var name:String = _
  private var sends:Boolean = _
  private var receives:Boolean = _

  def this(name:String) = {
    this()
    this.name = name
  }

  def this(id:Int, name:String, sends:Boolean, receives:Boolean) = {
    this()
    this.sends = sends
    this.receives = receives
    this.id = id
    this.name = name
  }

  def this(name:String, sends:Boolean, receives:Boolean) = {
    this()
    this.sends = sends
    this.receives = receives
    this.name = name
  }

  def getId = id

  def canSend = sends

  def canReceive = receives

  def getName = name
}
