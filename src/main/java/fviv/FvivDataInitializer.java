package fviv;

import static org.joda.money.CurrencyUnit.EUR;

import java.text.ParseException;

import fviv.festival.Festival;
import fviv.festival.FestivalRepository;
import fviv.model.Employee;
import fviv.model.Employee.Departement;
import fviv.model.EmployeeRepository;
import fviv.model.Finance;
import fviv.model.Finance.FinanceType;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class FvivDataInitializer implements DataInitializer {

	private final EmployeeRepository employeeRepository;
	private final UserAccountManager userAccountManager;
	private final FestivalRepository festivalRepository;
	private final TicketRepository ticketRepository;
	private final FinanceRepository financeRepository;

	@Autowired
	public FvivDataInitializer(EmployeeRepository employeeRepository,
			UserAccountManager userAccountManager,
			TicketRepository ticketRepository, 
			FestivalRepository festivalRepository, 
			FinanceRepository financeRepository) {
		Assert.notNull(employeeRepository,
				"EmployeeRepository must not be null!");
		this.employeeRepository = employeeRepository;
		this.userAccountManager = userAccountManager;
		this.ticketRepository = ticketRepository;
		this.festivalRepository = festivalRepository;
		this.financeRepository = financeRepository;
	}

	@Override
	public void initialize() {
		initializeUsers(userAccountManager, employeeRepository);
		initializeFinances(financeRepository);
		initializeTickets(ticketRepository);
		try {
			initializeFestivals(festivalRepository);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void initializeFestivals(FestivalRepository festivalRepository2)
			throws ParseException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LL-dd");
		LocalDate date1 = LocalDate.parse("2014-12-30", formatter);
		LocalDate date2 = LocalDate.parse("2015-01-03", formatter);

		Festival festival1 = new Festival(date1, date2, "Wonderland", "Dresden EnergieVerbund Arena",
				"Avicii, Linkin Park", 500000, (long) 55.0);
		Festival festival2 = new Festival(date2, date1, "Rock am Ring", "Berlin in deiner Mom",
				"Netflix", 69999 , (long) 12.0);

		festivalRepository.save(festival1);
		festivalRepository.save(festival2);

	}


	private void initializeTickets(TicketRepository ticketRepository)  {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LL-dd");
		LocalDate date = LocalDate.parse("2005-12-30", formatter);
		Ticket ticket1 = new Ticket(true, false, "Wonderland", date);
		Ticket ticke2 = new Ticket(false, true, "Rock am Ring", null);
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
				"Bill.Gates@Microsoft.com", "0190CallBill", Departement.MANAGEMENT);
		Employee employee2 = new Employee(employeeAccount2, "Merkel", "Angela",
				"Angie@Bundestag.de", "0123456789", Departement.CLEANING);
		Employee employee3 = new Employee(employeeAccount3, "Wurst", "Hans",
				"Hans.Wurst@fviv.de", "0351/777888", Departement.SECURITY);
		Employee employee4 = new Employee(employeeAccount4, "White", "Walter",
				"Walter.White@Kochkurse.de", "BetterCallSaul", Departement.MANAGEMENT);
		Employee employee5 = new Employee(employeeAccount5, "Müller", "Thomas",
				"Thomas.Müller@Weltmeister.de", "20304050", Departement.CATERING);

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


	private void initializeFinances(FinanceRepository financeRepository) {
		
		// Create expenses
		financeRepository.save(new Finance(Reference.EXPENSE, Money.of(EUR, 13.80), FinanceType.SALARY));
		financeRepository.save(new Finance(Reference.EXPENSE, Money.of(EUR, 680.40), FinanceType.SALARY));
		financeRepository.save(new Finance(Reference.EXPENSE, Money.of(EUR, 5600.00), FinanceType.RENT));
		financeRepository.save(new Finance(Reference.EXPENSE, Money.of(EUR, 2400.00), FinanceType.RENT));
		 
	}
}
