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
	private FinanceRepository financeRepository;
	private FestivalRepository festivalRepository;

	@Autowired
	public PlanningAJAXController(AreaItemsRepository areaItems,
			PlanningItemsRepository itemsForPlanerRepository,
			FinanceRepository financeRepository, FestivalRepository festivalRepository) {
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
		if (areaItems.findByFestival(festivalRepository.findOne(festivalId)) != null) {
			return areaItems.findByFestival(festivalRepository.findOne(festivalId));
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/newArea", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public boolean newAreal(@RequestParam("width") int width,
			@RequestParam("height") int height,
			@RequestParam("faktor") float factor,
			@RequestParam("festival") long festivalId) {
		AreaItem area = areaItems.findByName("Areal");
		if (area == null) {
			areaItems.save(new AreaItem(Type.AREA, "Areal", width,
					height, 0, 0, factor, festivalRepository.findOne(festivalId)));
		} else {
			areaItems.deleteAll();
			areaItems.save(new AreaItem(Type.AREA, "Areal", width,
					height, 0, 0, factor, festivalRepository.findOne(festivalId)));
		}

		return true;
	}

	@RequestMapping(value = "/newObject", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public Iterable<AreaItem> newObject(@RequestParam("typ") String typ,
			@RequestParam("name") String name,
			@RequestParam("width") int width,
			@RequestParam("height") int height,
			@RequestParam("left") float left, @RequestParam("top") float top,
			@RequestParam("festival") long festivalId) {

		if (areaItems.findByName("Areal") != null) {
			Finance finance = new Finance(festivalId,
					Reference.EXPENSE, planningItems.findByName(name)
					.getRentCost(), FinanceType.RENT);
			financeRepository.save(finance);
			switch (typ) {
			case "TOILET":
				areaItems.save(new AreaItem(Type.TOILET, (name), width,
						height, left, top, festivalRepository.findOne(festivalId)));
				break;
			case "STAGE":
				areaItems.save(new AreaItem(Type.STAGE, (name), width,
						height, left, top, festivalRepository.findOne(festivalId)));
				break;
			case "CATERING":
				areaItems.save(new AreaItem(Type.CATERING, (name),
						width, height, left, top, festivalRepository.findOne(festivalId)));
				break;
			case "CAMPING":
				areaItems.save(new AreaItem(Type.CAMPING, (name), width,
						height, left, top, festivalRepository.findOne(festivalId)));
			}
			return areaItems.findByFestival(festivalRepository.findOne(festivalId));
		} else {
			return null;
		}
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
}
