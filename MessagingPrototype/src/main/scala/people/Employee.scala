package people

/**
 * Created by justusadam on 19/11/14.
 */
class Employee(private val _id:Int, private val _name:String) extends Person{

  def name = _name
  def id = _id
}
