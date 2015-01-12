package fviv.areaPlanner;

import fviv.areaPlanner.AreaItem.Type;
import org.springframework.data.repository.CrudRepository;

public interface AreaItemsRepository extends CrudRepository<AreaItem, String> {

	AreaItem findByName(String name);
	Iterable < AreaItem > findByType(Type type);
	Iterable <AreaItem> findByFestivalId(long festivalId);
}
