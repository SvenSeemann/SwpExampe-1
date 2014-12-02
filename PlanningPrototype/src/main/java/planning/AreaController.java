package planning;

import planning.Coords.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AreaController {
	private PlanningRepository planningRepository;

	@Autowired
	public AreaController(PlanningRepository planningRepository) {
		this.planningRepository = planningRepository;
	}

	@RequestMapping("/")
	public String getHtml(Model modelMap) {
		modelMap.addAttribute("getCoords", planningRepository);
		return "planer";
	}
	
	@ModelAttribute("size")
	public String size(){
		//if (planningRepository.findByName("Areal").)
		if (planningRepository.findAll().iterator().hasNext()) {
			return "width: " + planningRepository.findByName("Areal").getWidth() + "px;" + "height:" + planningRepository.findByName("Areal").getHeight() + "px;";
		} else {
			return "width: 0px;" + "height: 0px" ;
		}
	}
	
	@RequestMapping(value = "/newArea", method = RequestMethod.POST)
	public String scheissEgal(@RequestParam("width") int width,
			@RequestParam("height") int height) {
		planningRepository.save(new Coords("Areal", width, height, 0, 0, Type.AREA));
		return "redirect:/";
	}
}
