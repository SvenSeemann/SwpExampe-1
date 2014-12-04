package fviv;

import org.salespointframework.core.DataInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fviv.model.Employee;
import fviv.model.EmployeeRepository;
import fviv.model.Expense;
import fviv.model.ExpenseRepository;
import fviv.model.Ticket;
import fviv.model.TicketRepository;

// Creates dummy data for test purposes
@Component
public class FVIVDataInitializer implements DataInitializer{
	private final EmployeeRepository employeeRepository;
	private final ExpenseRepository expenseRepository;
	private final TicketRepository ticketRepository;
	
	@Autowired
	public FVIVDataInitializer(EmployeeRepository employeeRepository, ExpenseRepository expenseRepository, TicketRepository ticketRepository){
		this.employeeRepository = employeeRepository;
		this.expenseRepository = expenseRepository;
		this.ticketRepository = ticketRepository;
		initialize();
	}
	
	public void initialize(){
		initializeEmployees(employeeRepository);
		initializeExpenses(expenseRepository);
		initializeTickets(ticketRepository);
	}
	
	
	private void initializeTickets(TicketRepository ticketRepository) {
		//Create Tickets
				Ticket ticket1 = new Ticket("Tageskarte", "Wunderland", "AVICII");
				Ticket ticket2 = new Ticket("Wochenkarte", "Wunderland", "klaus");
				Ticket ticket3 = new Ticket("CAAAAAAAAMPINGKARTE", "Wunderland", "AVICII");
				Ticket ticket4 = new Ticket("Tageskarte", "Wunderland", "AVICII");
				Ticket ticket5 = new Ticket("Tageskarte", "Wunderland", "marie");
				
				//Save to repository
				ticketRepository.save(ticket1);
				ticketRepository.save(ticket2);
				ticketRepository.save(ticket3);
				ticketRepository.save(ticket4);
				ticketRepository.save(ticket5);
	}

	private void initializeEmployees(EmployeeRepository employeeRepository){		
		//Create employees
		Employee employee1 = new Employee("Gates", "Bill", "BillGates@Microsoft.com", "0190CallBill");
		Employee employee2 = new Employee("Merkel", "Angela", "Angie@Bundestag.de", "0123456789");
		Employee employee3 = new Employee("Wurst", "Hans", "HansWurst@FVIV.de", "0351/777888");
		Employee employee4 = new Employee("White", "Walter", "Walter@Kochkurse.de", "0351/666555");
		Employee employee5 = new Employee("Müller", "Thomas", "Thomas.Müller@Weltmeister.de", "20304050");
		
		//Save to repository
		employeeRepository.save(employee1);
		employeeRepository.save(employee2);
		employeeRepository.save(employee3);
		employeeRepository.save(employee4);
		employeeRepository.save(employee5);
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
