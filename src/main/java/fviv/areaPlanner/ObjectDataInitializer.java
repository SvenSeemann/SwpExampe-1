package fviv.areaPlanner;

import static org.joda.money.CurrencyUnit.*;

import java.util.Arrays;
import java.util.List;

import org.salespointframework.core.DataInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import fviv.areaPlanner.Objekt;

@Component
public class ObjectDataInitializer implements DataInitializer {
	private final ObjectRepository objectRepository;

	@Autowired
	public ObjectDataInitializer(ObjectRepository objectRepository) {
		this.objectRepository = objectRepository;
	}

	@Override
	public void initialize() {
		initializeObjects(objectRepository);
	}

	private void initializeObjects(ObjectRepository objcetRepository) {
		// new Objekt(width, height, cost)
		objectRepository.save(new Objekt("kleine Buehne", 10, 5, 200.25f));
		objectRepository.save(new Objekt("mittlere Buehne", 20, 10, 200.25f));
		objectRepository.save(new Objekt("grosse Buehne", 40, 20, 200.25f));
		objectRepository.save(new Objekt("WC", 2, 2, 200.25f));
		objectRepository.save(new Objekt("Beh WC", 5, 5, 200.25f));
		objectRepository.save(new Objekt("Bad", 12, 3, 200.25f));
		objectRepository.save(new Objekt("Bierwagen", 5, 4, 200.25f));
		objectRepository.save(new Objekt("Essplatz", 2, 2, 200.25f));
		objectRepository.save(new Objekt("Muell", 1, 2, 200.25f));
	}
}
