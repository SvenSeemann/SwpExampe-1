package fviv.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import fviv.model.Expense.ExpenseType;

public interface FinanceRepository extends CrudRepository<Expense, Long>{
	Expense findById(long id);
	List<Expense> findByExpenseType(ExpenseType expenseType);
}
