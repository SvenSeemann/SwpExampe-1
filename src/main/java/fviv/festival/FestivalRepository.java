package fviv.festival;

import org.salespointframework.useraccount.UserAccount;
import org.springframework.data.repository.CrudRepository;

public interface FestivalRepository extends CrudRepository<Festival, Long>{
	Festival findById(long id);
	Festival findByUserAccount(UserAccount userAccount);
}