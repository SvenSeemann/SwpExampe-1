package fviv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fviv.areaPlanner.Coords;
import fviv.areaPlanner.PlanningRepository;
import fviv.areaPlanner.Coords.Type;


@RestController
public class PlanningAJAXController {
	private static final String IS_AJAX_HEADER = "X-Requested-With=XMLHttpRequest";

	private PlanningRepository planningRepository;

	@Autowired
	public PlanningAJAXController(PlanningRepository planningRepository) {
		super();
		this.planningRepository = planningRepository;
	}

	@RequestMapping(value = "/newArea", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public boolean neuesAreal(@RequestParam("width") int width,
			@RequestParam("height") int height, @RequestParam("faktor") float factor) {
		Coords area = planningRepository.findByName("Areal");
		if (area == null) {
			planningRepository.save(new Coords("Areal", width, height, 0, 0,
					Type.AREA, factor));
			return true;
		} else {
			System.out.println("weite: " + area.getWidth() + "hoehe: "
					+ area.getHeight() + "faktor: " + area.getFactor());
			return false;
		}
	}
}
