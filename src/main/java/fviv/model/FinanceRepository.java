package fviv.model;

import org.springframework.data.repository.CrudRepository;

import fviv.model.Finance.Reference;

public interface FinanceRepository extends CrudRepository<Finance, Long>{
	Finance findById(long id);
	Iterable<Finance> findByReference(Reference reference);
	Iterable<Finance> findByFinanceType(String financeType);
}
