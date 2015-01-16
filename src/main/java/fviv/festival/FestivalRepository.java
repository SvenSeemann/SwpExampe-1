package fviv.festival;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.repository.CrudRepository;


public interface FestivalRepository extends CrudRepository<Festival, Long>{
	Festival findById(long id);
	Iterable<Festival> findByFestivalName(String festivalName);
	Collection<Festival> findByStartDatum(LocalDate start);
}