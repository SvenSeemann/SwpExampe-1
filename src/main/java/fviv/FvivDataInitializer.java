package fviv;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import fviv.festival.Festival;
import fviv.festival.FestivalRepository;
import fviv.model.Employee;
import fviv.model.EmployeeRepository;
import fviv.model.Expense;
import fviv.model.ExpenseRepository;
import fviv.model.Expense.ExpenseType;
import fviv.ticket.Ticket;
import fviv.ticket.TicketRepository;

import org.salespointframework.core.DataInitializer;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;

@Component
public class FvivDataInitializer implements DataInitializer {

	private final EmployeeRepository employeeRepository;
	private final UserAccountManager userAccountManager;
	private final ExpenseRepository expenseRepository;
	private final FestivalRepository festivalRepository;
	private final TicketRepository ticketRepository;

	@Autowired
	public FvivDataInitializer(EmployeeRepository employeeRepository,
			UserAccountManager userAccountManager,
			ExpenseRepository expenseRepository,
			TicketRepository ticketRepository, 
			FestivalRepository festivalRepository) {

		Assert.notNull(employeeRepository,
				"EmployeeRepository must not be null!");
		this.employeeRepository = employeeRepository;
		this.userAccountManager = userAccountManager;
		this.expenseRepository = expenseRepository;
		this.ticketRepository = ticketRepository;
		this.festivalRepository = festivalRepository;
	}

	@Override
	public void initialize() {
		initializeUsers(userAccountManager, employeeRepository);
		initializeExpenses(expenseRepository);
		initializeTickets(ticketRepository);
		try {
			initializeFestivals(festivalRepository);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void initializeFestivals(FestivalRepository festivalRepository2)
			throws ParseException {
		DateFormat format = new SimpleDateFormat("d, MMMM, yyyy", Locale.GERMAN);
		Date date1 = format.parse("2, Januar, 2010");
		Date date2 = format.parse("4, März, 2012");

		Festival festival1 = new Festival(date1, date2, "Wonderland", "Dresden EnergieVerbund Arena",
				"Avicii, Linkin Park", 5, (long) 55.0);
		Festival festival2 = new Festival(date1, date2, "Rock am Ring", "Berlin in deiner Mom",
				"Netflix", 6 , (long) 12.0);
		festivalRepository.save(festival1);
		festivalRepository.save(festival2);

	}

	private void initializeTickets(TicketRepository ticketRepository) {
		Ticket ticket1 = new Ticket(true, false, "Wonderland");
		Ticket ticke2 = new Ticket(false, true, "Rock am Ring");
		ticketRepository.save(ticket1);
		ticketRepository.save(ticke2);
	}

	private void initializeUsers(UserAccountManager userAccountManager,
			EmployeeRepository employeeRepository) {

		final Role bossRole = new Role("ROLE_BOSS");
		final Role managerRole = new Role("ROLE_MANAGER");
		final Role catererRole = new Role("ROLE_CATERER");
		final Role employeeRole = new Role("ROLE_EMPLOYEE");

		UserAccount boss = userAccountManager.create("boss", "123", bossRole);
		UserAccount manager = userAccountManager.create("manager", "123",
				managerRole);
		UserAccount caterer = userAccountManager.create("caterer", "123",
				catererRole);
		userAccountManager.save(boss);
		userAccountManager.save(manager);
		userAccountManager.save(caterer);

		// Create employees
		UserAccount employeeAccount1 = userAccountManager.create("gates",
				"123", employeeRole);
		UserAccount employeeAccount2 = userAccountManager.create("merkel",
				"123", employeeRole);
		UserAccount employeeAccount3 = userAccountManager.create("wurst",
				"123", employeeRole);
		UserAccount employeeAccount4 = userAccountManager.create("white",
				"123", employeeRole);
		UserAccount employeeAccount5 = userAccountManager.create("müller",
				"123", employeeRole);

		Employee employee1 = new Employee(employeeAccount1, "Gates", "Bill",
				"Bill.Gates@Microsoft.com", "0190CallBill");
		Employee employee2 = new Employee(employeeAccount2, "Merkel", "Angela",
				"Angie@Bundestag.de", "0123456789");
		Employee employee3 = new Employee(employeeAccount3, "Wurst", "Hans",
				"Hans.Wurst@fviv.de", "0351/777888");
		Employee employee4 = new Employee(employeeAccount4, "White", "Walter",
				"Walter.White@Kochkurse.de", "BetterCallSaul");
		Employee employee5 = new Employee(employeeAccount5, "Müller", "Thomas",
				"Thomas.Müller@Weltmeister.de", "20304050");

		// Save to repository

		employeeRepository.save(employee1);
		employeeRepository.save(employee2);
		employeeRepository.save(employee3);
		employeeRepository.save(employee4);
		employeeRepository.save(employee5);

		// Save to userAccountManager
		userAccountManager.save(employeeAccount1);
		userAccountManager.save(employeeAccount2);
		userAccountManager.save(employeeAccount3);
		userAccountManager.save(employeeAccount4);
		userAccountManager.save(employeeAccount5);
	}

	private void initializeExpenses(ExpenseRepository expenseRepository) {
		// Create expenses
		/*Expense expense1 = new Expense(ExpenseType.CATERING, 1500f);
		Expense expense2 = new Expense(ExpenseType.CATERING, 800.50f);
		Expense expense3 = new Expense(ExpenseType.SALARY, 98.50f);
		Expense expense4 = new Expense(ExpenseType.SALARY, 8.50f);
		Expense expense5 = new Expense(ExpenseType.RENT, 5000f);
		Expense expense6 = new Expense(ExpenseType.RENT, 2600f);
		Expense expense7 = new Expense(ExpenseType.SALARY, 13.80f);
		Expense expense8 = new Expense(ExpenseType.CATERING, 473f);
		Expense expense9 = new Expense(ExpenseType.SALARY, 860.4f);
		Expense expense10 = new Expense(ExpenseType.DEPOSIT, 10000f);

		// Save to repository
		expenseRepository.save(expense1);
		expenseRepository.save(expense2);
		expenseRepository.save(expense3);
		expenseRepository.save(expense4);
		expenseRepository.save(expense5);
		expenseRepository.save(expense6);
		expenseRepository.save(expense7);
		expenseRepository.save(expense8);
		expenseRepository.save(expense9);
		expenseRepository.save(expense10);*/
	}

}
