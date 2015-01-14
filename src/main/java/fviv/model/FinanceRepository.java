package fviv.model;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import fviv.model.Finance.FinanceType;
import fviv.model.Finance.Reference;

public interface FinanceRepository extends CrudRepository<Finance, Long>{
	Finance findById(long id);
	Collection<Finance> findByReference(Reference reference);
	Collection<Finance> findByFinanceType(FinanceType financeType);
	Collection<Finance> findByFestivalId (long FestivalId);
}
