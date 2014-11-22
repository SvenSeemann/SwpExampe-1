package fviv;

import org.salespointframework.core.DataInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fviv.model.Employee;
import fviv.model.EmployeeRepository;

@Component
public class FVIVDataInitializer implements DataInitializer{
	private final EmployeeRepository employeeRepository;
	
	@Autowired
	public FVIVDataInitializer(EmployeeRepository employeeRepository){
		this.employeeRepository = employeeRepository;
	}
	
	public void initialize(){
		initializeEmployees(employeeRepository);
	}
	
	private void initializeEmployees(EmployeeRepository employeeRepository){
		Employee employee1 = new Employee("Gates", "Bill", "BillGates@Microsoft.com", "0190CallBill");
		Employee employee2 = new Employee("Merkel", "Angela", "Angie@Bundestag.de", "0123456789");
		employeeRepository.save(employee1);
		employeeRepository.save(employee2);
		System.out.println("Employees: "+employeeRepository.findAll());
	}
}
