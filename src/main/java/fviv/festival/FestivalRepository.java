package fviv.festival;

import org.springframework.data.repository.CrudRepository;

public interface FestivalRepository extends CrudRepository<Festival, Long>{
	Iterable<Festival> findByManagerUserName(String managerUserName);
}