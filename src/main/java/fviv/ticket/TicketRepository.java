package fviv.ticket;

import java.util.List;

import java.time.LocalDate;
import org.springframework.data.repository.CrudRepository;


public interface TicketRepository extends CrudRepository<Ticket, Long>{
	Ticket findById(long id);
	List<Ticket> findByChecked(boolean checked);
	List<Ticket> findByTagesticketdate(LocalDate date);
	List<Ticket> findByFestivalName(String FestivalName);

}
