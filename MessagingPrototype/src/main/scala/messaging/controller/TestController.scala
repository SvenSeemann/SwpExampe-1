package messaging.controller

import messaging.{MessageForm, Message, Receiver, Sender}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.{RequestMethod, RequestParam, PathVariable, RequestMapping}
import people.{Office, People, Employee, Caterer}

import scala.collection.JavaConversions

/**
 * Created by justusadam on 19/11/14.
 */

@Controller
@Autowired
class TestController {

  @RequestMapping(Array("/messaging/choose"))
  def page(model:Model) = {
    model addAttribute ("people", JavaConversions.asJavaIterable(People.people.values))
    "overview"
  }


  @RequestMapping(Array("/messaging/choose/{id}"))
  def page(@PathVariable("id") id:Int, model:Model) = {
    def messageAggregate(x:Receiver) = {
      x.fetchMessages map {m:Message => m.toString}
    }

    People.people.get(id) match {
      case None => "error"
      case Some(x) =>
        model addAttribute("name", x.name)
        model addAttribute("messaging", new MessageForm(classOf[Receiver] isAssignableFrom x.getClass, classOf[Sender] isAssignableFrom x.getClass, x match {
          case m:Receiver => messageAggregate(m)
          case _ => List[String]()
        }))
        x match {
          case m: Sender => model addAttribute("receivers", JavaConversions asJavaIterable(
          People.people.values filter {
            case s: Receiver => true
            case _ => false
          }
          ))
          case _ =>
        }
        "choose"
    }
  }
  @RequestMapping(value=Array("/messaging/send"), method = Array(RequestMethod.GET, RequestMethod.POST))
  def send(@RequestParam recipient:Int, @RequestParam message:String, @RequestParam sender:Int, model:Model) = {
    People.get(sender) match {
      case Some(x:Sender) =>
        x send (message, recipient)
        "redirect:/messaging/choose"
      case _ => "redirect:/error"
    }
  }

  @RequestMapping(value = Array("/messaging/add"), method = Array(RequestMethod.POST))
  def add(@RequestParam kind:String, @RequestParam name:String): String = {
    People add (kind match {
        case "caterer" => new Caterer(People.new_id, name)
        case "office" => new Office(People.new_id, name)
        case "employee" => new Employee(People.new_id, name)
        case _ => throw new IllegalArgumentException
    })
    "redirect:/messaging/choose"
  }
}
