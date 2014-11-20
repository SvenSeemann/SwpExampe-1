package people

import javax.persistence.{GeneratedValue, Id, Entity}

/**
 * Created by justusadam on 19/11/14.
 */
@Entity
protected class Employee extends Person{

  @Id
  @GeneratedValue
  private var _id:Int = _
  private var _name:String = _

  def this(id:Int, name:String) = {
    this()
    _id = id
    _name = name
  }


  def name = _name
  def id = _id

  def getId = _id

  def getName = _name
}
