package fviv.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fviv.festival.FestivalRepository;
import fviv.festival.Festival;
// by niko // festivalerstellungscontroller

@Controller
@PreAuthorize("hasRole('ROLE_BOSS')")

public class CreateController {
	private final FestivalRepository festivalRepository;
	private String mode = "festival";


	@Autowired
	public CreateController(FestivalRepository festivalRepository) {
		this.festivalRepository = festivalRepository;
	}

	@RequestMapping({ "/festival" })
	public String index(ModelMap modelMap) {
		mode="festival";
		modelMap.addAttribute("festivallist", festivalRepository.findAll());

		return "festival";
	}
	@RequestMapping({ "/showFestival" })
	public String showFestival() {
		mode="showFestivals";

		return "redirect:/festival";
	}
	
		@ModelAttribute("festivalmode")
		public String festivalmode() {
			return mode;
		}

	@RequestMapping(value = "/newFestival", method = RequestMethod.POST)
	public String newFestival(@RequestParam("festivalName") String festivalName,
			@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate,
			@RequestParam("actors") String actors,
			@RequestParam("maxVisitors") int maxVisitors,
			@RequestParam("location") String location,
			@RequestParam("preisTag") long preisTag) throws ParseException {

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dateStart = format.parse(startDate);
		Date dateEnd = format.parse(endDate);
		
		Festival festival = new Festival(dateStart, dateEnd, festivalName, location,
				actors, (int) maxVisitors , (long) preisTag);
		
	
		festivalRepository.save(festival);
		return "redirect:/festival";

	}

}