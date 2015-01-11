package fviv.model;

import fviv.festival.Festival;
import org.springframework.data.repository.CrudRepository;

/**
 * @author justusadam
 */
public interface EventsRepository extends CrudRepository<Event, Long> {
    public Iterable<Event> findByFestivalOrderByStartAsc(Festival festival);
}
