package fviv.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import fviv.model.Finance.Reference;

public interface FinanceRepository extends CrudRepository<Finance, Long>{
	Finance findById(long id);
	Iterable<Finance> findByReference(Reference reference);
}
