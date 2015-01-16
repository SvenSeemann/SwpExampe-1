package fviv.areaPlanner;

import static org.joda.money.CurrencyUnit.*;

import org.joda.money.Money;
import org.salespointframework.core.DataInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fviv.areaPlanner.PlanningItem;

@Component
public class PlanningItemDataInitializer implements DataInitializer {
	private final PlanningItemsRepository itemsRepository;

	@Autowired
	public PlanningItemDataInitializer(PlanningItemsRepository itemsRepository) {
		this.itemsRepository = itemsRepository;
	}

	@Override
	public void initialize() {
		initializeObjects(itemsRepository);
	}

	private void initializeObjects(PlanningItemsRepository itemsRepository) {
		// new Objekt(width, height, cost)
		itemsRepository.save(new PlanningItem("kleine Buehne", 10, 5, Money.of(EUR, 200.25)));
		itemsRepository.save(new PlanningItem("mittlere Buehne", 20, 10, Money.of(EUR, 400.25)));
		itemsRepository.save(new PlanningItem("grosse Buehne", 40, 20, Money.of(EUR, 500.72)));
		itemsRepository.save(new PlanningItem("WC", 2, 2, Money.of(EUR, 50.01)));
		itemsRepository.save(new PlanningItem("Beh WC", 5, 5, Money.of(EUR, 50.01)));
		itemsRepository.save(new PlanningItem("Bad", 12, 3, Money.of(EUR, 150.30)));
		itemsRepository.save(new PlanningItem("Cateringstand", 5, 4, Money.of(EUR, 230.00)));
		itemsRepository.save(new PlanningItem("Essplatz", 2, 2, Money.of(EUR, 20.64)));
		itemsRepository.save(new PlanningItem("Muell", 1, 2, Money.of(EUR, 12.50)));
	}
}
