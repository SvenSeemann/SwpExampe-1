package fviv;

import org.salespointframework.core.DataInitializer;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fviv.model.StaffRepository;
import fviv.model.Employee;
import fviv.model.Expense;
import fviv.model.ExpenseRepository;

// Creates dummy data for test purposes
@Component
public class ManagerDataInitializer implements DataInitializer{
	private final StaffRepository staffRepository;
	private final ExpenseRepository expenseRepository;
	private final UserAccountManager userAccountManager;
	
	@Autowired
	public ManagerDataInitializer(StaffRepository staffRepository, ExpenseRepository expenseRepository, UserAccountManager userAccountManager){
		this.staffRepository = staffRepository;
		this.expenseRepository = expenseRepository;
		this.userAccountManager = userAccountManager;
		initialize();
	}
	
	public void initialize(){
		initializeStaff(userAccountManager, staffRepository);
		initializeExpenses(expenseRepository);
	}
	
	private void initializeStaff(UserAccountManager userAccountManager, StaffRepository staffRepository){		
		final Role employeeRole = new Role("ROLE_EMPLOYEE");
		
		//Create employees
		UserAccount employeeAccount1 = userAccountManager.create("gates", "123", employeeRole);
		UserAccount employeeAccount2 = userAccountManager.create("merkel", "123", employeeRole);
		UserAccount employeeAccount3 = userAccountManager.create("wurst", "123", employeeRole);
		UserAccount employeeAccount4 = userAccountManager.create("white", "123", employeeRole);
		UserAccount employeeAccount5 = userAccountManager.create("müller", "123", employeeRole);
		Employee employee1 = new Employee(employeeAccount1, "0190CallBill");
		Employee employee2 = new Employee(employeeAccount2, "0123456789");
		Employee employee3 = new Employee(employeeAccount3, "0351/777888");
		Employee employee4 = new Employee(employeeAccount4, "0351/666555");
		Employee employee5 = new Employee(employeeAccount5, "20304050");
				
		//Save to repository
		staffRepository.save(employee1.getUserAccount());
		staffRepository.save(employee2.getUserAccount());
		staffRepository.save(employee3.getUserAccount());
		staffRepository.save(employee4.getUserAccount());
		staffRepository.save(employee5.getUserAccount());
		
		//Save to userAccountManager
		userAccountManager.save(employeeAccount1);
		userAccountManager.save(employeeAccount2);
		userAccountManager.save(employeeAccount3);
		userAccountManager.save(employeeAccount4);
		userAccountManager.save(employeeAccount5);
	}
	
	private void initializeExpenses(ExpenseRepository expenseRepository){
		//Create expenses
		Expense expense1 = new Expense("catering", 1500f);
		Expense expense2 = new Expense("catering", 800.50f);
		Expense expense3 = new Expense("salary", 98.50f);
		Expense expense4 = new Expense("salary", 8.50f);
		Expense expense5 = new Expense("rent", 5000f);
		Expense expense6 = new Expense("rent", 2600f);
		Expense expense7 = new Expense("salary", 13.80f);
		Expense expense8 = new Expense("catering", 473f);
		Expense expense9 = new Expense("salary", 860.4f);
		Expense expense10 = new Expense("deposit", 10000f);
		
		//Save to repository
		expenseRepository.save(expense1);
		expenseRepository.save(expense2);
		expenseRepository.save(expense3);
		expenseRepository.save(expense4);
		expenseRepository.save(expense5);
		expenseRepository.save(expense6);
		expenseRepository.save(expense7);
		expenseRepository.save(expense8);
		expenseRepository.save(expense9);
		expenseRepository.save(expense10);
	}
}
