package fviv.areaPlanner;

import org.springframework.data.repository.CrudRepository;

public interface ItemsForPlanerRepository extends CrudRepository<Planningitems, String> {
	Planningitems findByName(String name);
}
