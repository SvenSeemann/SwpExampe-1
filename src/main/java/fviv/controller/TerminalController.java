package fviv.controller;

import fviv.areaPlanner.AreaItemsRepository;
import fviv.festival.FestivalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class TerminalController {
	private AreaItemsRepository areaItems;
	private FestivalRepository festivalRepository;
	private long fid;
	
	@Autowired
	public TerminalController(AreaItemsRepository areaItems, FestivalRepository festivalRepository) {
		this.areaItems = areaItems;
		this.festivalRepository = festivalRepository;
	}

	@ModelAttribute("festivalId")
	public String festivalId() {
		return "" + fid;
	}
	
	@RequestMapping("/terminal")
	public String giveMeATermial(Model model) {
		model.addAttribute("festivals", festivalRepository.findAll());
	//	model.addAttribute("irgendwas", areaItems.findByFestivalId(festivalId));
		return "terminal";
	}
	
	@RequestMapping(value = "/terminal/show/area/{festivalId}", method = RequestMethod.POST)
	public String showArea(Model model, @PathVariable("festivalId") long festivalId) {
		model.addAttribute("irgendwas", areaItems.findByFestival(festivalRepository.findOne(festivalId)));
		fid = festivalId;
		return "redirect:/terminal";
	}
}
