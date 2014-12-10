package fviv;

import fviv.model.Employee;
import fviv.model.EmployeeRepository;
import fviv.model.Expense;
import fviv.model.ExpenseRepository;

import org.salespointframework.core.DataInitializer;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class FvivDataInitializer implements DataInitializer {
	
	private final EmployeeRepository employeeRepository;
	private final UserAccountManager userAccountManager;
	private final ExpenseRepository expenseRepository;
	
	@Autowired
	public FvivDataInitializer (EmployeeRepository employeeRepository, UserAccountManager userAccountManager, ExpenseRepository expenseRepository) {
		
		Assert.notNull(employeeRepository, "EmployeeRepository must not be null!");
		
		this.employeeRepository = employeeRepository;
		this.userAccountManager = userAccountManager;
		this.expenseRepository = expenseRepository;
	}
	
	@Override
	public void initialize() {
		initializeUsers(userAccountManager, employeeRepository);
		initializeExpenses(expenseRepository);
	}
	
	private void initializeUsers(UserAccountManager userAccountManager, EmployeeRepository employeeRepository){
		
		final Role bossRole = new Role("ROLE_BOSS");
		final Role managerRole = new Role("ROLE_MANAGER");
		final Role catererRole = new Role("ROLE_CATERER");
		final Role employeeRole = new Role("ROLE_EMPLOYEE");
		
		UserAccount boss = userAccountManager.create("boss", "123", bossRole);
		UserAccount manager = userAccountManager.create("manager", "123", managerRole);
		UserAccount caterer = userAccountManager.create("caterer", "123", catererRole);
		userAccountManager.save(boss);
		userAccountManager.save(manager);
		userAccountManager.save(caterer);
				
		//Create employees
		UserAccount employeeAccount1 = userAccountManager.create("gates", "123", employeeRole);
		employeeAccount1.setFirstname("Bill");
		employeeAccount1.setLastname("Gates");
		employeeAccount1.setEmail("BillGates@Microsoft.com");
		UserAccount employeeAccount2 = userAccountManager.create("merkel", "123", employeeRole);
		employeeAccount2.setFirstname("Angela");
		employeeAccount2.setLastname("Merkel");
		employeeAccount2.setEmail("Angie@Bundestag");
		UserAccount employeeAccount3 = userAccountManager.create("wurst", "123", employeeRole);
		employeeAccount3.setFirstname("Hans");
		employeeAccount3.setLastname("Wurst");
		employeeAccount3.setEmail("HansWurst@fviv.de");
		UserAccount employeeAccount4 = userAccountManager.create("white", "123", employeeRole);
		employeeAccount4.setFirstname("Walter");
		employeeAccount4.setLastname("White");
		employeeAccount4.setEmail("Walter.White@Kochkurse.de");
		UserAccount employeeAccount5 = userAccountManager.create("müller", "123", employeeRole);
		employeeAccount5.setFirstname("Thomas");
		employeeAccount5.setLastname("Müller");
		employeeAccount5.setEmail("Thomas.Müller@Weltmeister.de");
		
		Employee employee1 = new Employee(employeeAccount1, "0190CallBill");
		Employee employee2 = new Employee(employeeAccount2, "0123456789");
		Employee employee3 = new Employee(employeeAccount3, "0351/777888");
		Employee employee4 = new Employee(employeeAccount4, "BetterCaulSaul");
		Employee employee5 = new Employee(employeeAccount5, "20304050");

		//Save to repository
		employeeRepository.save(employee1);
		employeeRepository.save(employee2);
		employeeRepository.save(employee3);
		employeeRepository.save(employee4);
		employeeRepository.save(employee5);
		
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
