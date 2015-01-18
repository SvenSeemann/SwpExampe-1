package fviv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FestivalController {
	
	public FestivalController () {
		
	}
	
	@RequestMapping({"/", "/index"})
	public String index() {
		return "index";
	}
}
