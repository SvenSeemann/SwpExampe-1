package people

import javax.persistence.{GeneratedValue, Id, Entity}

/**
 * Created by justusadam on 19/11/14.
 */
@Entity
protected class Employee(@Id
                         @GeneratedValue
                         private var _id:Int,
                         private val _name:String)
  extends Person{

  @Deprecated
  def this() = {
    this(_,_)
  }

  def name = _name
  def id = _id

  def getId = _id

  def getName = _name
}
