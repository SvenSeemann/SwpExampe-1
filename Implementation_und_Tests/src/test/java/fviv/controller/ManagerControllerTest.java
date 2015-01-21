package fviv.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import fviv.AbstractIntegrationTests;
import fviv.model.Employee;
import fviv.model.Employee.Departement;
import fviv.model.EmployeeRepository;
import fviv.model.Registration;

/**
 * @author Hendric Eckelt
 */

public class ManagerControllerTest extends AbstractIntegrationTests {

	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	ManagerController controller;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	UserAccountManager userAccountManager;

	protected void login(String userName, String password) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				userName, password);
		SecurityContextHolder.getContext().setAuthentication(
				authenticationManager.authenticate(authentication));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void ManagerControllerIndexTest() {
		login("manager", "123");

		ModelMap modelMap = new ModelMap();
		String returnedView = controller.index(modelMap);

		// returnedView has to be manager
		assertThat(returnedView, is("manager"));

		// ModelMap "employeelist" must contain all employees from the
		// repository
		assertThat((Iterable<Object>) modelMap.get("employeelist"),
				is(iterableWithSize((int) employeeRepository.count())));

		// ModelMap "roles" must contain all available roles (currently 6)
		assertThat((Iterable<Object>) modelMap.get("roles"),
				is(iterableWithSize(8)));

		// ModelMap "userAccounts" must contain all userAccounts that exist
		int countUserAccounts = 0;
		for (@SuppressWarnings("unused")
		UserAccount userAccount : userAccountManager.findAll()) {
			countUserAccounts++;
		}
		assertThat((Iterable<Object>) modelMap.get("userAccounts"),
				is(iterableWithSize(countUserAccounts)));
	}

	@Test
	public void ManagerControllerNewEmployeeTest() {
		login("manager", "123");

		Registration registration = new Registration();
		registration.setLastname("Testman");
		registration.setFirstname("Super");
		registration.setEmail("Super.Testman@TestYourSoftware.de");
		registration.setPhone("0351/TestEverything");
		registration.setPassword("123");

		DataBinder dataBinder = new DataBinder(registration);
		BindingResult results = dataBinder.getBindingResult();
		String returnedView = controller.newEmployee(registration, results, "123");

		// returnedView has to be manager
		assertThat(returnedView, is("redirect:/management"));

		// Validate that the employee was created and added to the repository
		List<Employee> testEmployee = employeeRepository
				.findByPhone("0351/TestEverything");
		assertThat(testEmployee.get(0).getUserAccount().getLastname(),
				is("Testman"));
		assertThat(testEmployee.get(0).getUserAccount().getFirstname(),
				is("Super"));
		assertThat(testEmployee.get(0).getUserAccount().getEmail(),
				is("Super.Testman@TestYourSoftware.de"));
		assertThat(testEmployee.get(0).getPhone(), is("0351/TestEverything"));
	}

	@Test
	public void ManagerControllerDeleteEmployeeTest() {
		login("manager", "123");

		// Create employee for test purposes
		final Role testRole = new Role("ROLE_TEST");
		UserAccount testUserAccount = userAccountManager.create("testman",
				"123", testRole);
		userAccountManager.save(testUserAccount);
		Employee testEmployee = new Employee(testUserAccount, "testman",
				"super", "Super.Testman@TestYourSoftware.de",
				"0351/TestEverything", Departement.MANAGEMENT);
		employeeRepository.save(testEmployee);

		String returnedView = controller.deleteEmployee(testEmployee.getId());

		// returnedView has to be manager
		assertThat(returnedView, is("redirect:/management"));

		// TestEmployee must have been removed from the repository
		assertThat(employeeRepository.findByPhone("0351/TestEverything"),
				is(iterableWithSize(0)));
	}
	
	@Test
	public void ManagerControllerAddRoleTest(){
		login("manager", "123");
		ModelMap modelMap = new ModelMap();
		Role managerRole = new Role("ROLE_MANAGER");
		Role employeeRole = new Role("ROLE_EMPLOYEE");
		
		// Create a test account
		UserAccount testAccount = userAccountManager.create("testAccountAddRole", "123", employeeRole);
		userAccountManager.save(testAccount);
		
		// Switch to edit mode for the test account and add the manager role
		controller.editAccount("testAccountAddRole", modelMap);
		String returnedView = controller.addRole("ROLE_MANAGER");
		
		// returnedView has to be manager
		assertThat(returnedView, is("redirect:/management"));
		
		// testAccount must have both roles assigned
		assertThat(userAccountManager.findByUsername("testAccountAddRole").get().hasRole(managerRole), is(true));
		assertThat(userAccountManager.findByUsername("testAccountAddRole").get().hasRole(employeeRole), is(true));
	}
	
	@Test
	public void ManagerControllerDeleteRoleTest(){
		login("manager", "123");
		ModelMap modelMap = new ModelMap();
		Role managerRole = new Role("ROLE_MANAGER");
		
		// Create a test account
		UserAccount testAccount = userAccountManager.create("testAccountDeleteRole", "123", managerRole);
		userAccountManager.save(testAccount);
		
		// Test if manager role is attached
		assertThat(userAccountManager.findByUsername("testAccountDeleteRole").get().hasRole(managerRole), is(true));
		
		// Switch to edit mode for the test account and delete the manager role
		controller.editAccount("testAccountDeleteRole", modelMap);
		String returnedView = controller.deleteRole("ROLE_MANAGER");
		
		// returnedView has to be manager
		assertThat(returnedView, is("redirect:/management"));
		
		// Test if manager role got deleted
		assertThat(userAccountManager.findByUsername("testAccountDeleteRole").get().hasRole(managerRole), is(false));
	}
}
