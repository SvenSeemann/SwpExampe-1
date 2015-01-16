package fviv.controller;

import fviv.festival.FestivalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fviv.areaPlanner.AreaItem;
import fviv.areaPlanner.PlanningItem;
import fviv.areaPlanner.AreaItemsRepository;
import fviv.areaPlanner.PlanningItemsRepository;
import fviv.areaPlanner.AreaItem.Type;
import fviv.location.LocationRepository;

@RestController
@PreAuthorize("hasAnyRole('ROLE_BOSS','ROLE_SENDER','ROLE_RECEIVER')")
public class PlanningAJAXController {
	private static final String IS_AJAX_HEADER = "X-Requested-With=XMLHttpRequest";
	private PlanningItemsRepository planningItems;
	private AreaItemsRepository areaItems;
	private FestivalRepository festivalRepository;

	@Autowired
	public PlanningAJAXController(AreaItemsRepository areaItems,
			PlanningItemsRepository itemsForPlanerRepository,
			FestivalRepository festivalRepository,
			LocationRepository locationRepository) {
		super();
		this.festivalRepository = festivalRepository;
		this.areaItems = areaItems;
		this.planningItems = itemsForPlanerRepository;
	}

	@RequestMapping(value = "/isThereAnything", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public Iterable<AreaItem> rebuildPlaner(
			@RequestParam("request") String request,
			@RequestParam("festival") long festivalId) {
		return areaItems.findByFestival(festivalRepository.findOne(festivalId));
	}

	@RequestMapping(value = "/newObject", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public Iterable<AreaItem> newObject(@RequestParam("typ") String typ,
			@RequestParam("name") String name,
			@RequestParam("width") int width,
			@RequestParam("height") int height,
			@RequestParam("left") float left, @RequestParam("top") float top,
			@RequestParam("festival") long festivalId) {

		switch (typ) {
		case "TOILET":
			areaItems.save(new AreaItem(Type.TOILET, (name), width, height,
					left, top, festivalRepository.findOne(festivalId)));
			break;

		case "STAGE":
			areaItems.save(new AreaItem(Type.STAGE, (name), width, height,
					left, top, festivalRepository.findOne(festivalId)));
			break;

		case "CATERING":
			areaItems.save(new AreaItem(Type.CATERING, (name), width, height,
					left, top, festivalRepository.findOne(festivalId)));
			break;

		case "CAMPING":
			areaItems.save(new AreaItem(Type.CAMPING, (name), width, height,
					left, top, festivalRepository.findOne(festivalId)));
			break;

		case "BLOCKED":
			areaItems.save(new AreaItem(Type.BLOCKED, (name), width, height,
					left, top, festivalRepository.findOne(festivalId)));
			break;
		}
		return areaItems.findByFestival(festivalRepository.findOne(festivalId));
	}

	@RequestMapping(value = "/getValues", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public Iterable<PlanningItem> giveMeValuesOfObjects(
			@RequestParam("request") String request) {
		return planningItems.findAll();
	}

	@RequestMapping(value = "/terminal/show/area/{fid}", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public Iterable<AreaItem> giveMeAllEntries(
			@RequestParam("request") String request,
			@PathVariable("fid") long festivalId) {
		return areaItems.findByFestival(festivalRepository.findOne(festivalId));
	}

	@RequestMapping(value = "/deleteExistingOnSave", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public void deleteExistingOnSave(@RequestParam("festival") long festivalId) {
		for (AreaItem areaItem : areaItems.findByFestival(festivalRepository
				.findOne(festivalId))) {
			if (!(areaItem.getName().equals("Areal")))
				areaItems.delete(areaItem);
		}
	}
}
