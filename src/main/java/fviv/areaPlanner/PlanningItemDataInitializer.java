package fviv.areaPlanner;

import static org.joda.money.CurrencyUnit.*;

import java.util.Arrays;
import java.util.List;

import org.salespointframework.core.DataInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import fviv.areaPlanner.PlanningItem;

@Component
public class PlanningItemDataInitializer implements DataInitializer {
	private final ItemsForPlanerRepository itemsRepository;

	@Autowired
	public PlanningItemDataInitializer(ItemsForPlanerRepository itemsRepository) {
		this.itemsRepository = itemsRepository;
	}

	@Override
	public void initialize() {
		initializeObjects(itemsRepository);
	}

	private void initializeObjects(ItemsForPlanerRepository itemsRepository) {
		// new Objekt(width, height, cost)
		itemsRepository.save(new PlanningItem("kleine Buehne", 10, 5, 200.25f));
		itemsRepository.save(new PlanningItem("mittlere Buehne", 20, 10, 200.25f));
		itemsRepository.save(new PlanningItem("grosse Buehne", 40, 20, 200.25f));
		itemsRepository.save(new PlanningItem("WC", 2, 2, 200.25f));
		itemsRepository.save(new PlanningItem("Beh WC", 5, 5, 200.25f));
		itemsRepository.save(new PlanningItem("Bad", 12, 3, 200.25f));
		itemsRepository.save(new PlanningItem("Bierwagen", 5, 4, 200.25f));
		itemsRepository.save(new PlanningItem("Essplatz", 2, 2, 200.25f));
		itemsRepository.save(new PlanningItem("Muell", 1, 2, 200.25f));
	}
}
