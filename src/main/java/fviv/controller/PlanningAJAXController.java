package fviv.controller;

import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fviv.areaPlanner.AreaItem;
import fviv.areaPlanner.MList;
import fviv.areaPlanner.PlanningItem;
import fviv.areaPlanner.AreaItemsRepository;
import fviv.areaPlanner.PlanningItemsRepository;
import fviv.areaPlanner.AreaItem.Type;

@RestController
public class PlanningAJAXController {
	private static final String IS_AJAX_HEADER = "X-Requested-With=XMLHttpRequest";

	private PlanningItemsRepository itemsForPlanerRepository;
	private AreaItemsRepository planningRepository;

	@Autowired
	public PlanningAJAXController(AreaItemsRepository planningRepository,
			PlanningItemsRepository itemsForPlanerRepository) {
		super();
		this.planningRepository = planningRepository;
		this.itemsForPlanerRepository = itemsForPlanerRepository;
	}

	@RequestMapping(value = "/isThereAnything", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public Iterable<AreaItem> rebuildPlaner(
			@RequestParam("request") String request) {
		if (planningRepository.findAll() != null) {
			return planningRepository.findAll();
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/newArea", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public boolean newAreal(@RequestParam("width") int width,
			@RequestParam("height") int height,
			@RequestParam("faktor") float factor) {
		AreaItem area = planningRepository.findByName("Areal");
		if (area == null) {
			planningRepository.save(new AreaItem(Type.AREA, "Areal", width,
					height, 0, 0, factor));
		} else {
			planningRepository.deleteAll();
			planningRepository.save(new AreaItem(Type.AREA, "Areal", width,
					height, 0, 0, factor));
		}
		return true;
	}

	@RequestMapping(value = "/newObject", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public Iterable<AreaItem> newObject(@RequestParam("typ") String typ,
			@RequestParam("name") String name,
			@RequestParam("width") int width,
			@RequestParam("height") int height,
			@RequestParam("left") float left, @RequestParam("top") float top) {
		System.out.println(planningRepository.findByName("Areal"));
		if (planningRepository.findByName("Areal") != null) {
			System.out.println("wtf");
			switch (typ) {
			case "TOILET":
				planningRepository.save(new AreaItem(Type.TOILET, (name), width,
						height, left, top));
				break;
			case "STAGE":
				planningRepository.save(new AreaItem(Type.STAGE, (name), width,
						height, left, top));
				break;
			case "CATERING":
				planningRepository.save(new AreaItem(Type.CATERING, (name),
						width, height, left, top));
				break;
			case "CAMPING":
				planningRepository.save(new AreaItem(Type.CAMPING, (name), width,
						height, left, top));
			}
			return planningRepository.findAll();
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/getValues", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public Iterable<PlanningItem> giveMeValuesOfObjects(
			@RequestParam("request") String request) {
		return itemsForPlanerRepository.findAll();
	}

	@RequestMapping(value = "/terminal", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public Iterable<AreaItem> giveMeAllEntries(
			@RequestParam("request") String request) {
		return planningRepository.findAll();
	}
}
