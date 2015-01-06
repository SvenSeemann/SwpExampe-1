package fviv.areaPlanner;

import org.springframework.data.repository.CrudRepository;

public interface ObjectRepository extends CrudRepository<Objekt, String> {
	Objekt findByName(String name);
}
