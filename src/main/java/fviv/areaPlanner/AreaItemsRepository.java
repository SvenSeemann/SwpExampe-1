package fviv.areaPlanner;

import fviv.areaPlanner.AreaItem.Type;
import fviv.festival.Festival;
import org.springframework.data.repository.CrudRepository;

public interface AreaItemsRepository extends CrudRepository<AreaItem, Long> {

	AreaItem findByName(String name);
	Iterable < AreaItem > findByType(Type type);
	Iterable <AreaItem> findByFestival(Festival festivalId);
}
