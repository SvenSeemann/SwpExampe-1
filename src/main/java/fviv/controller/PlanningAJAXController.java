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
			@RequestParam("height") int height,
			@RequestParam("faktor") float factor) {
		Coords area = planningRepository.findByName("Areal");
		if (area == null) {
			planningRepository.save(new Coords(Type.AREA, "Areal", width, height, 0, 0, factor));
			return true;
		} else {
			System.out.println("weite: " + area.getWidth() + "hoehe: "
					+ area.getHeight() + "faktor: " + area.getFactor());
			return false;
		}
	}

	@RequestMapping(value = "/newToilet", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public boolean newToilet(@RequestParam("name") String name, @RequestParam("width") int width,
			@RequestParam("height") int height, @RequestParam("left") int left,
			@RequestParam("top") int top) {
		if (planningRepository.findByName("Areal") != null) {
			int i = 1;
			while (planningRepository.findByName(name + i) != null) {
				i++;
			}
			planningRepository.save(new Coords(Type.TOILET, (name + i), width, height, left,
					top));
		}
		return true;
	}
	
	@RequestMapping(value = "/newStage", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public boolean newStage(@RequestParam("name") String name, @RequestParam("width") int width,
			@RequestParam("height") int height, @RequestParam("left") int left,
			@RequestParam("top") int top) {
		if (planningRepository.findByName("Areal") != null) {
			int i = 1;
			while (planningRepository.findByName(name + i) != null) {
				i++;
			}
			planningRepository.save(new Coords(Type.STAGE, (name + i), width, height, left,
					top));
		}
		return true;
	}
	
	@RequestMapping(value = "/newCatering", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public boolean newCatering(@RequestParam("name") String name, @RequestParam("width") int width,
			@RequestParam("height") int height, @RequestParam("left") int left,
			@RequestParam("top") int top) {
		if (planningRepository.findByName("Areal") != null) {
			int i = 1;
			while (planningRepository.findByName(name + i) != null) {
				i++;
			}
			planningRepository.save(new Coords(Type.CATERING, (name + i), width, height, left,
					top));
		}
		return true;
	}
	
	@RequestMapping(value = "/newCamping", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public boolean newCamping(@RequestParam("name") String name, @RequestParam("width") int width,
			@RequestParam("height") int height, @RequestParam("left") int left,
			@RequestParam("top") int top) {
		if (planningRepository.findByName("Areal") != null) {
			int i = 1;
			while (planningRepository.findByName(name + i) != null) {
				i++;
			}
			planningRepository.save(new Coords(Type.CAMPING, (name + i), width, height, left,
					top));
		}
		return true;
	}
}
