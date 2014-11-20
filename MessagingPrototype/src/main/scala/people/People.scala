package people

/**
  * Created by justusadam on 19/11/14.
  */
object People {
   private val _people:Map[Int, Employee] = Map(
     1 -> new Manager("manager"),
     2 -> new Caterer(2, "Caterer")
   )

   def people = _people
 }
