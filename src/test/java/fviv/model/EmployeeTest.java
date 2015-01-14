package fviv.model;

import org.junit.Test;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;

import fviv.AbstractIntegrationTests;
import fviv.model.Employee.Departement;
import fviv.user.Roles;

/**
 * @author Hendric Eckelt
 */

public class EmployeeTest extends AbstractIntegrationTests {
	@Autowired
	UserAccountManager userAccountManager;
	@Autowired
	EmployeeRepository employeeRepository;

	@Test
	public void newEmployeeTest() {
		// Create a new Employee
		UserAccount testAccount = userAccountManager.create("testAccount",
				"123", Roles.employee);
		userAccountManager.save(testAccount);
		Employee testEmployee = new Employee(testAccount, "TestMan", "Der",
				"TestMan@Fviv.de", "0351Test", Departement.MANAGEMENT);
		testEmployee.setId(800);
		employeeRepository.save(testEmployee);

		// Validate id
		assertThat(testEmployee.getId(), is(800L));
		
		// Validate departement
		assertThat(
				testEmployee.getDepartement().equals(Departement.MANAGEMENT),
				is(true));
		
		// Validate that the employee is saved to the repository
		for(Employee employee : employeeRepository.findByPhone("0351Test")){
			assertThat(employee.getUserAccount().equals(testEmployee.getUserAccount()), is(true));
		}
	}
}
