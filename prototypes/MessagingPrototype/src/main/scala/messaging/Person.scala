package fviv.messaging

/**
 * Created by justusadam on 19/11/14.
 *
 * Test base class for a people.Person
 */
class Person{
  private var name:String = _

  def getName = name

  def this(name:String) {
    this()
    this.name = name
  }
}
