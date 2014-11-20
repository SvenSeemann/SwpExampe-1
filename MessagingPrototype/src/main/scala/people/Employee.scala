package people

import javax.persistence.Entity

/**
 * Created by justusadam on 19/11/14.
 */
@Entity
class Employee(private val _id:Int, private val _name:String) extends Person{

  def name = _name
  def id = _id

  def getId = _id

  def getName = _name
}
