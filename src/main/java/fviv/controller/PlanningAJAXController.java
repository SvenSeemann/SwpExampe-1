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
	private PlanningItemsRepository planningItems;
	private AreaItemsRepository areaItems;

	@Autowired
	public PlanningAJAXController(AreaItemsRepository areaItems,
			PlanningItemsRepository itemsForPlanerRepository) {
		super();
		this.areaItems = areaItems;
		this.planningItems = itemsForPlanerRepository;
	}

	@RequestMapping(value = "/isThereAnything", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public Iterable<AreaItem> rebuildPlaner(
			@RequestParam("request") String request) {
		if (areaItems.findAll() != null) {
			return areaItems.findAll();
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/newArea", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public boolean newAreal(@RequestParam("width") int width,
			@RequestParam("height") int height,
			@RequestParam("faktor") float factor) {
		AreaItem area = areaItems.findByName("Areal");
		if (area == null) {
			areaItems.save(new AreaItem(Type.AREA, "Areal", width,
					height, 0, 0, factor));
		} else {
			areaItems.deleteAll();
			areaItems.save(new AreaItem(Type.AREA, "Areal", width,
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

		if (planningItems.findByName("Areal") != null) {		//evtl areaItems
			switch (typ) {
			case "TOILET":
				areaItems.save(new AreaItem(Type.TOILET, (name), width,
						height, left, top));
				break;
			case "STAGE":
				areaItems.save(new AreaItem(Type.STAGE, (name), width,
						height, left, top));
				break;
			case "CATERING":
				areaItems.save(new AreaItem(Type.CATERING, (name),
						width, height, left, top));
				break;
			case "CAMPING":
				areaItems.save(new AreaItem(Type.CAMPING, (name), width,
						height, left, top));
			}
			return areaItems.findAll();
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/getValues", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public Iterable<PlanningItem> giveMeValuesOfObjects(
			@RequestParam("request") String request) {
		return planningItems.findAll();
	}

	@RequestMapping(value = "/terminal", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public Iterable<AreaItem> giveMeAllEntries(
			@RequestParam("request") String request) {
		return areaItems.findAll();
	}
}
