package fviv.festival;

import org.springframework.data.repository.CrudRepository;


public interface FestivalRepository extends CrudRepository<Festival, Long>{
	Festival findById(long id);

}