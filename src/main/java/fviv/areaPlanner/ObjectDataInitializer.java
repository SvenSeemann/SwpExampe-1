package fviv.areaPlanner;

import static org.joda.money.CurrencyUnit.*;

import java.util.Arrays;
import java.util.List;

import org.salespointframework.core.DataInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import fviv.areaPlanner.Planningitems;

@Component
public class ObjectDataInitializer implements DataInitializer {
	private final ItemsForPlanerRepository objectRepository;

	@Autowired
	public ObjectDataInitializer(ItemsForPlanerRepository objectRepository) {
		this.objectRepository = objectRepository;
	}

	@Override
	public void initialize() {
		initializeObjects(objectRepository);
	}

	private void initializeObjects(ItemsForPlanerRepository objcetRepository) {
		// new Objekt(width, height, cost)
		objectRepository.save(new Planningitems("kleine Buehne", 10, 5, 200.25f));
		objectRepository.save(new Planningitems("mittlere Buehne", 20, 10, 200.25f));
		objectRepository.save(new Planningitems("grosse Buehne", 40, 20, 200.25f));
		objectRepository.save(new Planningitems("WC", 2, 2, 200.25f));
		objectRepository.save(new Planningitems("Beh WC", 5, 5, 200.25f));
		objectRepository.save(new Planningitems("Bad", 12, 3, 200.25f));
		objectRepository.save(new Planningitems("Bierwagen", 5, 4, 200.25f));
		objectRepository.save(new Planningitems("Essplatz", 2, 2, 200.25f));
		objectRepository.save(new Planningitems("Muell", 1, 2, 200.25f));
	}
}
