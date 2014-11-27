package messaging.controller

import messaging._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.{RequestMethod, RequestParam, PathVariable, RequestMapping}

import scala.collection.{mutable, JavaConversions}

/**
 * Created by justusadam on 19/11/14.
 */

@Controller
class TestController() {

  var postOffice:PostOffice = _
  var people:People = _

  @Autowired
  def this(postOffice:PostOffice, people:People) {
    this()
    this.postOffice = postOffice
    this.people = people
  }

  @RequestMapping(Array("/messaging/choose"))
  def page(model:Model) = {
    model addAttribute ("people", people.findAll())
    "overview"
  }


  @RequestMapping(Array("/messaging/choose/{id}"))
  def page(@PathVariable("id") id:Int, model:Model) = {
    def messageAggregate(x:Employee) = {
      JavaConversions.iterableAsScalaIterable(postOffice.findByRecipient(x.getId)).map(m => m.toString)
    }

    val operson = people.findOne(id)
    if (!operson.isPresent) {
      "error"
    } else {
      val x = operson.get()
      model addAttribute("name", x.getName)
      x match {
        case x:Employee =>
          model addAttribute("messaging", new MessageForm(x.canReceive, x.canSend, x.canReceive match {
            case true => messageAggregate(x)
            case false => List[String]()
          }))
        x.canSend match {
          case false =>
          case true =>
              var l = new mutable.MutableList[Employee]
              for (person <- JavaConversions.iterableAsScalaIterable(people.findAll())) {
                person match {
                  case s:Employee => s.canReceive match {
                    case true => l += person
                    case false =>
                  }
                  case _ =>
                }
              }
              model addAttribute("receivers", JavaConversions asJavaIterable l)
          }
        case _ =>
      }

      "choose"
    }
  }
  @RequestMapping(value=Array("/messaging/send"), method = Array(RequestMethod.GET, RequestMethod.POST))
  def send(@RequestParam recipient:Int, @RequestParam message:String, @RequestParam sender:Int, model:Model) = {
    val operson = people.findOne(sender)
    if (!operson.isPresent) "redirect:/error"
    else {
      operson.get() match {
        case x:Employee => x.canSend match{
          case true =>
            val orec = people.findOne(recipient)
            orec.isPresent match {
              case true =>
                postOffice.save(new Message(message, x.getId, orec.get.getId))
                "redirect:/messaging/choose"
              case false => "redirect:/error"
            }
          case false => "redirect:/error"

        }
        case _ => "redirect:/error"
      }
    }
  }

  @RequestMapping(value = Array("/messaging/add"), method = Array(RequestMethod.POST))
  def add(@RequestParam kind:String, @RequestParam name:String): String = {
    val person = kind match {
        case "caterer" => new Employee(name, true, false)
        case "office" => new Employee(name, false, true)
        case "employee" => new Employee(name)
        case _ => throw new IllegalArgumentException
    }
    people.save(person)
    "redirect:/messaging/choose"
  }
}
