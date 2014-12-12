package fviv;

import fviv.model.Employee;
import fviv.model.EmployeeRepository;
import fviv.model.Expense;
import fviv.model.ExpenseRepository;
import fviv.ticket.Ticket;
import fviv.ticket.TicketRepository;

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
	private final TicketRepository ticketRepository;
	
	@Autowired
	public FvivDataInitializer (EmployeeRepository employeeRepository, UserAccountManager userAccountManager, ExpenseRepository expenseRepository, TicketRepository ticketRepository) {
		
		Assert.notNull(employeeRepository, "EmployeeRepository must not be null!");
		
		this.employeeRepository = employeeRepository;
		this.userAccountManager = userAccountManager;
		this.expenseRepository = expenseRepository;
		this.ticketRepository = ticketRepository;
	}
	
	@Override
	public void initialize() {
		initializeUsers(userAccountManager, employeeRepository);
		initializeExpenses(expenseRepository);
		initializeTickets(ticketRepository);
	}
	
	private void initializeTickets(TicketRepository ticketRepository) {
		Ticket ticket1 = new Ticket(true, false);
		Ticket ticke2 = new Ticket(false, true);
		ticketRepository.save(ticket1);
		ticketRepository.save(ticke2);
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
		UserAccount employeeAccount2 = userAccountManager.create("merkel", "123", employeeRole);
		UserAccount employeeAccount3 = userAccountManager.create("wurst", "123", employeeRole);
		UserAccount employeeAccount4 = userAccountManager.create("white", "123", employeeRole);
		UserAccount employeeAccount5 = userAccountManager.create("müller", "123", employeeRole);
		
		Employee employee1 = new Employee(employeeAccount1, "Gates", "Bill", "Bill.Gates@Microsoft.com", "0190CallBill");
		Employee employee2 = new Employee(employeeAccount2, "Merkel", "Angela", "Angie@Bundestag.de", "0123456789");
		Employee employee3 = new Employee(employeeAccount3, "Wurst", "Hans", "Hans.Wurst@fviv.de", "0351/777888");
		Employee employee4 = new Employee(employeeAccount4, "White", "Walter", "Walter.White@Kochkurse.de", "BetterCallSaul");
		Employee employee5 = new Employee(employeeAccount5, "Müller", "Thomas", "Thomas.Müller@Weltmeister.de", "20304050");
	
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
