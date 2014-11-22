package fviv.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ExpenseRepository extends CrudRepository<Expense, Long>{
	Expense findById(long id);
	List<Expense> findByExpenseType(String expenseType);
}
