package fviv;

import static org.joda.money.CurrencyUnit.EUR;

import java.text.ParseException;

import fviv.festival.Festival;
import fviv.festival.FestivalRepository;
import fviv.location.Location;
import fviv.location.LocationRepository;
import fviv.model.*;
import fviv.model.Employee.Departement;
import fviv.model.Finance.FinanceType;
import fviv.model.Finance.Reference;
import fviv.ticket.Ticket;
import fviv.ticket.TicketRepository;
import fviv.user.Roles;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

@Component
public class FvivDataInitializer implements DataInitializer {

	private final EmployeeRepository employeeRepository;
	private final UserAccountManager userAccountManager;
	private final FestivalRepository festivalRepository;
	private final TicketRepository ticketRepository;
	private final FinanceRepository financeRepository;
	private final EventsRepository eventsRepository;
	private final ArtistsRepository artistsRepository;
	private final LocationRepository locationRepository;

	@Autowired
	public FvivDataInitializer(EmployeeRepository employeeRepository,
			UserAccountManager userAccountManager,
			TicketRepository ticketRepository,
			FestivalRepository festivalRepository,
			FinanceRepository financeRepository,
			ArtistsRepository artistsRepository,
			EventsRepository eventsRepository,
			LocationRepository locationRepository) {
			Assert.notNull(employeeRepository,
				"EmployeeRepository must not be null!");
		this.employeeRepository = employeeRepository;
		this.userAccountManager = userAccountManager;
		this.ticketRepository = ticketRepository;
		this.festivalRepository = festivalRepository;
		this.financeRepository = financeRepository;
		this.artistsRepository = artistsRepository;
		this.eventsRepository = eventsRepository;
		this.locationRepository = locationRepository;
	}

	@Override
	public void initialize() {
		initializeUsers();
		initializeFinances();
		initializeTickets();
		initializeLocations();
		try {
			initializeFestivals();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		initializeLineup();
	}

	private void initializeFestivals()
			throws ParseException {
				
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LL-dd");
		LocalDate date1 = LocalDate.parse("2014-12-30", formatter);
		LocalDate date2 = LocalDate.parse("2015-01-03", formatter);

		Festival festival1 = new Festival(date1, date2, "Wonderland",1 ,
				"Avicii, Linkin Park", 500000, (long) 55.0, "manager");
		Festival festival2 = new Festival(date2, date1, "Rock am Ring", 2,
				"Netflix", 69999 , (long) 12.0, "manager");

		
		UserAccount festivalAccount1 = userAccountManager.create("festival1" , "123", Roles.guest);
		UserAccount festivalAccount2 = userAccountManager.create("festival2" , "123", Roles.guest);
		
	//	festival1.setUserAccount(festivalAccount1);
	//	festival2.setUserAccount(festivalAccount2);
					
		userAccountManager.save(festivalAccount1);
		userAccountManager.save(festivalAccount2);
			
		festivalRepository.save(festival1);
		festivalRepository.save(festival2);
	
	}
	private void initializeLocations()
			 {
		Location location1 = new Location("Wunderland", 400, 300, 20000, "aasdf");
		Location location2 = new Location("Rock am Ring", 200, 500, 50000, "aasdf");
		Location location3 = new Location("Festival ist toll", 2000, 3000, 10000, "aasdf");
		Location location4 = new Location("Namen sind unwichtig", 1000, 1400, 9000, "aasdf");
		Location location5 = new Location("Boom", 5000, 3000, 5, "aasdf");
		locationRepository.save(location1);
		locationRepository.save(location2);
		locationRepository.save(location3);
		locationRepository.save(location4);
		locationRepository.save(location5);

		

	}

	private void initializeTickets() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LL-dd");
		LocalDate date = LocalDate.parse("2014-12-30", formatter);
		LocalDate date1 = LocalDate.parse("2014-12-31", formatter);
//true(tages), already checked in
		Ticket ticket1 = new Ticket(true, false, "Wonderland", date);
		Ticket ticket3 = new Ticket(true, true, "Wonderland", date1);
		Ticket ticket4 = new Ticket(true, true, "Wonderland", date);
		Ticket ticket5 = new Ticket(true, true, "Wonderland", date);
		Ticket ticket6 = new Ticket(true, true, "Wonderland", date);
		Ticket ticket7 = new Ticket(false, true, "Wonderland", null);
		Ticket ticket8 = new Ticket(true, false, "Wonderland", date);

		Ticket ticke2 = new Ticket(false, true, "Rock am Ring", null);
		
		ticketRepository.save(ticket1);
		ticketRepository.save(ticke2);
		ticketRepository.save(ticket3);
		ticketRepository.save(ticket4);
		ticketRepository.save(ticket5);
		ticketRepository.save(ticket6);
		ticketRepository.save(ticket7);
		ticketRepository.save(ticket8);



	}

	private void initializeUsers() {
		UserAccount bossAccount = userAccountManager.create("boss", "123",
				Roles.boss);
		bossAccount.setEmail("Boss@Fviv.de");
		bossAccount.setFirstname("Der");
		bossAccount.setLastname("Boss");
		UserAccount managerAccount = userAccountManager.create("manager",
				"123", Roles.manager);
		managerAccount.setEmail("Manager@Fviv.de");
		managerAccount.setFirstname("Der");
		managerAccount.setLastname("Manager");
		UserAccount catererAccount = userAccountManager.create("caterer",
				"123", Roles.caterer);
		catererAccount.setEmail("Caterer@Fviv.de");
		catererAccount.setFirstname("Der");
		catererAccount.setLastname("Caterer");
		UserAccount leaderAccount = userAccountManager.create("leader", "123", Roles.leader);
		leaderAccount.setEmail("Festivalleiter@Fviv.de");
		leaderAccount.setFirstname("Der");
		leaderAccount.setLastname("Festivalleiter");
		userAccountManager.save(bossAccount);
		userAccountManager.save(managerAccount);
		userAccountManager.save(catererAccount);
		userAccountManager.save(leaderAccount);

		// Create employees
		UserAccount employeeAccount1 = userAccountManager.create("gates",
				"123", Roles.employee);
		UserAccount employeeAccount2 = userAccountManager.create("merkel",
				"123", Roles.employee);
		UserAccount employeeAccount3 = userAccountManager.create("wurst",
				"123", Roles.employee);
		UserAccount employeeAccount4 = userAccountManager.create("white",
				"123", Roles.employee);
		UserAccount employeeAccount5 = userAccountManager.create("müller",
				"123", Roles.employee);

		Employee employee1 = new Employee(employeeAccount1, "Gates", "Bill",
				"Bill.Gates@Microsoft.com", "0190CallBill",
				Departement.MANAGEMENT);
		Employee employee2 = new Employee(employeeAccount2, "Merkel", "Angela",
				"Angie@Bundestag.de", "0123456789", Departement.CLEANING);
		Employee employee3 = new Employee(employeeAccount3, "Wurst", "Hans",
				"Hans.Wurst@fviv.de", "0351/777888", Departement.SECURITY);
		Employee employee4 = new Employee(employeeAccount4, "White", "Walter",
				"Walter.White@Kochkurse.de", "BetterCallSaul",
				Departement.MANAGEMENT);
		Employee employee5 = new Employee(employeeAccount5, "Müller", "Thomas",
				"Thomas.Müller@Weltmeister.de", "20304050",
				Departement.CATERING);

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

	private void initializeFinances() {

		// Create expenses
		financeRepository.save(new Finance(1, Reference.EXPENSE, Money.of(EUR,
				13.80), FinanceType.SALARY));
		financeRepository.save(new Finance(2, Reference.EXPENSE, Money.of(EUR,
				680.40), FinanceType.SALARY));
		financeRepository.save(new Finance(1, Reference.EXPENSE, Money.of(EUR,
				5600.00), FinanceType.RENT));
		financeRepository.save(new Finance(2, Reference.EXPENSE, Money.of(EUR,
				2400.00), FinanceType.RENT));

	}

	private void initializeLineup() {
		Artist artist = new Artist(100000, Money.of(CurrencyUnit.EUR, 20),
				"Dude", 20000);
		Artist artist2 = new Artist(1000000, Money.of(CurrencyUnit.EUR, 20),
				"Dudette", 20000);

		artistsRepository.save(artist);
		artistsRepository.save(artist2);

		Festival festival = festivalRepository.findById(1);
		Festival festival2 = festivalRepository.findById(2);

		eventsRepository.save(new Event(LocalDateTime.of(2024, 12, 26, 1, 1, 1), LocalDateTime.of(2014, 12, 26, 1, 1, 0), artist, festival));
		eventsRepository.save(new Event(LocalDateTime.of(2024, 12, 26, 1, 4, 1), LocalDateTime.of(2014, 12, 26, 1, 5, 0), artist2, festival));

		eventsRepository.save(new Event(LocalDateTime.of(2024, 12, 28, 1, 1, 1), LocalDateTime.of(2014, 12, 26, 1, 1, 0), artist, festival2));
		eventsRepository.save(new Event(LocalDateTime.of(2024, 12, 28, 1, 4, 1), LocalDateTime.of(2014, 12, 26, 1, 5, 0), artist2, festival2));
	}
}
