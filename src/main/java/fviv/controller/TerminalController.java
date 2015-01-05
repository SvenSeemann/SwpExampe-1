package fviv.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fviv.areaPlanner.Coords;
import fviv.areaPlanner.MList;
import fviv.areaPlanner.PlanningRepository;


@Controller
public class TerminalController {
	private PlanningRepository planningRepository;
	
	@Autowired
	public TerminalController(PlanningRepository planningRepository) {
		this.planningRepository = planningRepository;
	}

	@RequestMapping("/terminal")
	public String giveMeATermial(Model modelMap) {
		modelMap.addAttribute("irgendwas", planningRepository);
		return "terminal";
	}
}
