package fviv;

import static org.joda.money.CurrencyUnit.EUR;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import fviv.festival.Festival;
import fviv.festival.FestivalRepository;
import fviv.model.Employee;
import fviv.model.EmployeeRepository;
import fviv.model.Finance;
import fviv.model.Finance.Reference;
import fviv.model.FinanceRepository;
import fviv.ticket.Ticket;
import fviv.ticket.TicketRepository;

import org.joda.money.Money;
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
	private final FestivalRepository festivalRepository;
	private final TicketRepository ticketRepository;
	private final FinanceRepository cateringFinances;
	private final FinanceRepository salaryFinances;
	private final FinanceRepository rentFinances;

	@Autowired
	public FvivDataInitializer(EmployeeRepository employeeRepository,
			UserAccountManager userAccountManager,
			TicketRepository ticketRepository,
			FestivalRepository festivalRepository,
			FinanceRepository cateringFinances,
			FinanceRepository salaryFinances, FinanceRepository rentFinances) {

		Assert.notNull(employeeRepository,
				"EmployeeRepository must not be null!");
		this.employeeRepository = employeeRepository;
		this.userAccountManager = userAccountManager;
		this.ticketRepository = ticketRepository;
		this.festivalRepository = festivalRepository;
		this.cateringFinances = cateringFinances;
		this.salaryFinances = salaryFinances;
		this.rentFinances = rentFinances;
	}

	@Override
	public void initialize() {
		initializeUsers(userAccountManager, employeeRepository);
		initializeFinances(cateringFinances, salaryFinances, rentFinances);
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
				"Avicii, Linkin Park", 500000, (long) 55.0);
		Festival festival2 = new Festival(date1, date2, "Rock am Ring", "Berlin in deiner Mom",
				"Netflix", 69999 , (long) 12.0);

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

	private void initializeFinances(FinanceRepository cateringFinances, FinanceRepository salaryFinances, FinanceRepository rentFinances) {
		
		// Create expenses
		salaryFinances.save(new Finance(Reference.EXPENSE, Money.of(EUR, 13.80)));
		salaryFinances.save(new Finance(Reference.EXPENSE, Money.of(EUR, 680.40)));
		rentFinances.save(new Finance(Reference.EXPENSE, Money.of(EUR, 5600.00)));
		rentFinances.save(new Finance(Reference.EXPENSE, Money.of(EUR, 2400.00)));
		 
	}
}
