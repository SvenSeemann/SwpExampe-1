package fviv;

import org.salespointframework.core.DataInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fviv.model.Employee;
import fviv.model.EmployeeRepository;
import fviv.model.Expense;
import fviv.model.ExpenseRepository;

@Component
public class FVIVDataInitializer implements DataInitializer{
	private final EmployeeRepository employeeRepository;
	private final ExpenseRepository expenseRepository;
	
	@Autowired
	public FVIVDataInitializer(EmployeeRepository employeeRepository, ExpenseRepository expenseRepository){
		this.employeeRepository = employeeRepository;
		this.expenseRepository = expenseRepository;
		initialize();
	}
	
	public void initialize(){
		initializeEmployees(employeeRepository);
		initializeExpenses(expenseRepository);
	}
	
	private void initializeEmployees(EmployeeRepository employeeRepository){
		Employee employee1 = new Employee("Gates", "Bill", "BillGates@Microsoft.com", "0190CallBill");
		Employee employee2 = new Employee("Merkel", "Angela", "Angie@Bundestag.de", "0123456789");
		employeeRepository.save(employee1);
		employeeRepository.save(employee2);
	}
	
	private void initializeExpenses(ExpenseRepository expenseRepository){
		Expense expense1 = new Expense("catering", 1500f);
		Expense expense2 = new Expense("catering", 800.50f);
		Expense expense3 = new Expense("salary", 98.50f);
		Expense expense4 = new Expense("salary", 8.50f);
		Expense expense5 = new Expense("rent", 8000f);
		Expense expense6 = new Expense("rent", 12000f);
		
		expenseRepository.save(expense1);
		expenseRepository.save(expense2);
		expenseRepository.save(expense3);
		expenseRepository.save(expense4);
		expenseRepository.save(expense5);
		expenseRepository.save(expense6);
	}
}
