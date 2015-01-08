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

import fviv.areaPlanner.Coords;
import fviv.areaPlanner.MList;
import fviv.areaPlanner.Planningitems;
import fviv.areaPlanner.PlanningRepository;
import fviv.areaPlanner.ItemsForPlanerRepository;
import fviv.areaPlanner.Coords.Type;

@RestController
public class PlanningAJAXController {
	private static final String IS_AJAX_HEADER = "X-Requested-With=XMLHttpRequest";

	private ItemsForPlanerRepository itemsForPlanerRepository;
	private PlanningRepository planningRepository;

	@Autowired
	public PlanningAJAXController(PlanningRepository planningRepository,
			ItemsForPlanerRepository itemsForPlanerRepository) {
		super();
		this.planningRepository = planningRepository;
		this.itemsForPlanerRepository = itemsForPlanerRepository;
	}

	@RequestMapping(value = "/isThereAnything", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public Iterable<Coords> rebuildPlaner(
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
		Coords area = planningRepository.findByName("Areal");
		if (area == null) {
			planningRepository.save(new Coords(Type.AREA, "Areal", width,
					height, 0, 0, factor));
		} else {
			planningRepository.deleteAll();
			planningRepository.save(new Coords(Type.AREA, "Areal", width,
					height, 0, 0, factor));
		}
		return true;
	}

	@RequestMapping(value = "/newObject", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public Iterable<Coords> newObject(@RequestParam("typ") String typ,
			@RequestParam("name") String name,
			@RequestParam("width") int width,
			@RequestParam("height") int height,
			@RequestParam("left") float left, @RequestParam("top") float top) {
		System.out.println(planningRepository.findByName("Areal"));
		if (planningRepository.findByName("Areal") != null) {
			System.out.println("wtf");
			switch (typ) {
			case "TOILET":
				planningRepository.save(new Coords(Type.TOILET, (name), width,
						height, left, top));
				break;
			case "STAGE":
				planningRepository.save(new Coords(Type.STAGE, (name), width,
						height, left, top));
				break;
			case "CATERING":
				planningRepository.save(new Coords(Type.CATERING, (name),
						width, height, left, top));
				break;
			case "CAMPING":
				planningRepository.save(new Coords(Type.CAMPING, (name), width,
						height, left, top));
			}
			return planningRepository.findAll();
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/getValues", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public Iterable<Planningitems> giveMeValuesOfObjects(
			@RequestParam("request") String request) {
		return itemsForPlanerRepository.findAll();
	}

	@RequestMapping(value = "/terminal", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public Iterable<Coords> giveMeAllEntries(
			@RequestParam("request") String request) {
		return planningRepository.findAll();
	}
}
