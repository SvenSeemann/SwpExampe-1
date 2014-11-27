package planning;
import org.springframework.data.repository.CrudRepository;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.data.repository.CrudRepository;

public interface PlanningRepository extends CrudRepository<Coords, Long>{
	Coords findById(long id);
}
