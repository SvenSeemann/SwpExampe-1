package fviv.controller;

import fviv.areaPlanner.AreaItemsRepository;
import fviv.festival.Festival;
import fviv.festival.FestivalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TerminalController {
	private AreaItemsRepository areaItems;
	private FestivalRepository festivalRepository;
	private long fid;
	private Festival selected;
	private String mode;
	private long selectedId;
	private ScheduleController scheduleController;

	@Autowired
	public TerminalController(AreaItemsRepository areaItems,
			FestivalRepository festivalRepository,
			ScheduleController scheduleController) {
		this.areaItems = areaItems;
		this.festivalRepository = festivalRepository;
		this.scheduleController = scheduleController;
	}

	@ModelAttribute("selectedFestival")
	public String selectedFestival() {
		if (selected == null) {
			return "(noch kein Festival ausgewählt)";
		} else {
			selectedId = selected.getId();
			return selected.getFestivalName();
		}
	}

	@ModelAttribute("festivalId")
	public String festivalId() {
		return "" + fid;
	}

	/**
	 *
	 * @return "plan" or "schedule" as mode in the UI
	 */
	@ModelAttribute("view")
	public String view() {
		return mode;
	}

	@RequestMapping("/terminal")
	public String giveMeATermial(Model model) {
		model.addAttribute("festivals", festivalRepository.findAll());
		// model.addAttribute("irgendwas",
		// areaItems.findByFestivalId(festivalId));
		return "terminal";
	}

	@RequestMapping(value = "/terminal/select/festival", method = RequestMethod.POST)
	public String showAreas(@RequestParam("festival") long festivalId) {
		selected = this.festivalRepository.findById(festivalId);
		return "redirect:/terminal";
	}

	@RequestMapping(value = "/terminal/show/schedule", method = RequestMethod.POST)
	public String showPlan(Model model) {
		mode = "schedule";
		model.addAttribute("stages", scheduleController.getEvents(selected));
		return "redirect:/terminal";
	}

	@RequestMapping(value = "/terminal/show/area", method = RequestMethod.POST)
	public String showArea(Model model) {
		if (selected != null) {
			model.addAttribute("irgendwas",
					areaItems.findByFestival(festivalRepository
							.findOne(selected.getId())));
			fid = selected.getId();
			mode = "plan";
			return "redirect:/terminal";
		} else {
			return "redirect:/terminal";
		}
	}

	@RequestMapping(value = "/terminal/show/festivals", method = RequestMethod.POST)
	public String choosefestival() {
		mode = "default";
		return "redirect:/terminal";
	}

}
