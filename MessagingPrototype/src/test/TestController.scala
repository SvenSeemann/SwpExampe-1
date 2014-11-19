import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{PathVariable, RequestMapping}
import org.springframework.ui.Model

/**
 * Created by justusadam on 19/11/14.
 */

@Controller
@Autowired
class TestController {

  @RequestMapping(Array("/choose/{id}"))
  def page(@PathVariable("id") id:Int, model:Model) = {
    People.people.get(id) match {
      case None => "error"
      case Some(x) =>
        model.addAttribute("name", x.name)
    }
  }
}
