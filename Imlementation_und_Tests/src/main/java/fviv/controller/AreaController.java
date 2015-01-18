package fviv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fviv.areaPlanner.PlanningItemsRepository;

/**
 * @author Maximilian Schwarze
 */

@PreAuthorize("hasRole('ROLE_BOSS')")
@Controller
public class AreaController {
	private long festivalId;
	
	@Autowired
	public AreaController(PlanningItemsRepository planningItems) {
	}
	
	@RequestMapping("/planning/{fid}")
	public String getHtml(Model model, @PathVariable("fid") long fid) {	
		model.addAttribute("festivalId", fid);
		return "planning";
	}
}
