package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AreaController {
	// @Autowired
	public AreaController() {

	}

	@RequestMapping("/")
	public String getHtml(Model modelMap) {
		modelMap.addAttribute("registrationForm", new GetAreaSize());
		return "planer";
	}

	@RequestMapping(value = "/newArea", method = RequestMethod.POST)
	public String scheissEgal(@RequestParam("width") int width,
			@RequestParam("height") int height) {
		System.out.println(width + ", " + height);
		return "redirect:/";
	}
}
