package fviv.areaPlanner;

import org.springframework.data.repository.CrudRepository;

public interface PlanningItemsRepository extends CrudRepository<PlanningItem, String> {
	PlanningItem findByName(String name);
}
