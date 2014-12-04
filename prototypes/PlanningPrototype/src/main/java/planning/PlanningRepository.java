package planning;

import planning.Coords.Type;

import org.springframework.data.repository.CrudRepository;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.data.repository.CrudRepository;

public interface PlanningRepository extends CrudRepository<Coords, String> {

	Coords findByName(String name);
	Iterable < Coords > findByType(Type type);
}
