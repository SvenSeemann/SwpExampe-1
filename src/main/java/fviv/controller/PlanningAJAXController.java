package fviv.controller;

import java.util.LinkedList;

import fviv.festival.Festival;
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
import fviv.location.Location;
import fviv.location.LocationRepository;
import fviv.model.Finance;
import fviv.model.Finance.FinanceType;
import fviv.model.Finance.Reference;
import fviv.model.FinanceRepository;

@RestController
@PreAuthorize("hasAnyRole('ROLE_BOSS','ROLE_SENDER','ROLE_RECEIVER')")
public class PlanningAJAXController {
	private static final String IS_AJAX_HEADER = "X-Requested-With=XMLHttpRequest";
	private PlanningItemsRepository planningItems;
	private AreaItemsRepository areaItems;
	private FestivalRepository festivalRepository;
	private FinanceRepository financeRepository;

	@Autowired
	public PlanningAJAXController(AreaItemsRepository areaItems,
			PlanningItemsRepository itemsForPlanerRepository,
			FinanceRepository financeRepository,
			FestivalRepository festivalRepository,
			LocationRepository locationRepository) {
		super();
		this.festivalRepository = festivalRepository;
		this.areaItems = areaItems;
		this.planningItems = itemsForPlanerRepository;
		this.financeRepository = financeRepository;
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
			@RequestParam("left") float left, @RequestParam("top") float top,
			@RequestParam("festival") long festivalId) {

		int width = 0;
		int height = 0;
		
		switch (typ) {
		case "TOILET":
			if (name.equals("WC")) {
				width = planningItems.findByName("WC").getWidth();
				height = planningItems.findByName("WC").getHeight();
			}
			if (name.equals("Beh WC")) {
				width = planningItems.findByName("Beh WC").getWidth();
				height = planningItems.findByName("Beh WC").getHeight();
			}
			if (name.equals("Bad")) {
				width = planningItems.findByName("Bad").getWidth();
				height = planningItems.findByName("Bad").getHeight();
			}

			areaItems.save(new AreaItem(Type.TOILET, (name), width, height,
					left, top, festivalRepository.findOne(festivalId)));
			break;

		case "STAGE":
			if (name.equals("kleine Buehne")) {
				width = planningItems.findByName("kleine Buehne").getWidth();
				height = planningItems.findByName("kleine Buehne").getHeight();
			}
			if (name.equals("mittlere Buehne")) {
				width = planningItems.findByName("mittlere Buehne").getWidth();
				height = planningItems.findByName("mittlere Buehne")
						.getHeight();
			}
			if (name.equals("grosse Buehne")) {
				width = planningItems.findByName("grosse Buehne").getWidth();
				height = planningItems.findByName("grosse Buehne").getHeight();
			}

			areaItems.save(new AreaItem(Type.STAGE, (name), width, height,
					left, top, festivalRepository.findOne(festivalId)));
			break;

		case "CATERING":
			if (name.equals("Cateringstand")) {
				width = planningItems.findByName("Cateringstand").getWidth();
				height = planningItems.findByName("Cateringstand").getHeight();
			}
			if (name.equals("Essplatz")) {
				width = planningItems.findByName("Essplatz").getWidth();
				height = planningItems.findByName("Essplatz").getHeight();
			}
			if (name.equals("Muell")) {
				width = planningItems.findByName("Muell").getWidth();
				height = planningItems.findByName("Muell").getHeight();
			}

			areaItems.save(new AreaItem(Type.CATERING, (name), width, height,
					left, top, festivalRepository.findOne(festivalId)));
			break;

		case "CAMPING":
			areaItems.save(new AreaItem(Type.CAMPING, (name), width, height,
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
