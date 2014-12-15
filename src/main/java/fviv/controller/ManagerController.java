package fviv.controller;

import java.util.LinkedList;

import javax.validation.Valid;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import fviv.model.EmployeeRepository;
import fviv.model.Employee;
import fviv.model.Expense;
import fviv.model.ExpenseRepository;
import fviv.model.Registration;

/**
 * @author Hendric Eckelt
 */

@PreAuthorize("hasRole('ROLE_MANAGER')")
@Controller
public class ManagerController {
	private String mode = "";
	private final EmployeeRepository employeeRepository;
	private final ExpenseRepository expenseRepository;
	private final UserAccountManager userAccountManager;

	@Autowired
	public ManagerController(EmployeeRepository employeeRepository,
			ExpenseRepository expenseRepository,
			UserAccountManager userAccountManager) {
		this.employeeRepository = employeeRepository;
		this.expenseRepository = expenseRepository;
		this.userAccountManager = userAccountManager;
	}

	// String managermode for th:switch to decide which div to display
	@ModelAttribute("managermode")
	public String managermode() {
		return mode;
	}

	// Main mapping for the manager functions
	@RequestMapping({ "/manager" })
	public String index(ModelMap modelMap) {
		// Floats used as sum for each type of expense
		float salaryTotal = 0, cateringTotal = 0, rentTotal = 0, deposit = 0;

		// List of available roles for the account management
		LinkedList<Role> allRoles = new LinkedList<Role>();
		allRoles.add(new Role("ROLE_BOSS"));
		allRoles.add(new Role("ROLE_MANAGER"));
		allRoles.add(new Role("ROLE_CATERER"));
		allRoles.add(new Role("ROLE_EMPLOYEE"));
		allRoles.add(new Role("MESSAGE_SENDER"));
		allRoles.add(new Role("MESSAGE_RECEIVER"));

		// Add accounts and roles to modelMap
		modelMap.addAttribute("roles", allRoles);
		modelMap.addAttribute("userAccounts", userAccountManager.findAll());
		// Add list of all employees and an empty registration to the modelMap
		modelMap.addAttribute("employeelist", employeeRepository.findAll());
		modelMap.addAttribute("registration", new Registration());
		// Add finances by type to the modelMap
		modelMap.addAttribute("salary",
				expenseRepository.findByExpenseType("salary"));
		modelMap.addAttribute("catering",
				expenseRepository.findByExpenseType("catering"));
		modelMap.addAttribute("rent",
				expenseRepository.findByExpenseType("rent"));

		// Calculate total amounts of each expense type
		for (Expense exp : expenseRepository.findByExpenseType("salary")) {
			salaryTotal += exp.getAmount();
		}

		for (Expense exp : expenseRepository.findByExpenseType("catering")) {
			cateringTotal += exp.getAmount();
		}

		for (Expense exp : expenseRepository.findByExpenseType("rent")) {
			rentTotal += exp.getAmount();
		}

		for (Expense exp : expenseRepository.findByExpenseType("deposit")) {
			deposit += exp.getAmount();
		}

		// Add deposit and total amounts to modelMap
		modelMap.addAttribute("deposit", deposit);
		modelMap.addAttribute("salaryTotal", salaryTotal);
		modelMap.addAttribute("cateringTotal", cateringTotal);
		modelMap.addAttribute("rentTotal", rentTotal);

		return "manager";
	}

	@RequestMapping("/Employees")
	public String employees() {
		mode = "employees";
		return "redirect:/manager";
	}

	// Mapping to add a new employee to the repository
	@RequestMapping("/newEmployee")
	public String newEmployee(
			@ModelAttribute(value = "registration") @Valid Registration registration,
			BindingResult results) {
		// Redirect if entered registration information are not valid
		if (results.hasErrors())
			return "redirect:/manager";

		// Create useraccount
		final Role employeeRole = new Role("ROLE_EMPLOYEE");
		UserAccount employeeAccount = userAccountManager.create(
				registration.getLastname(), "123", employeeRole);

		// Create employee
		Employee employee = new Employee(employeeAccount,
				registration.getLastname(), registration.getFirstname(),
				registration.getEmail(), registration.getPhone());

		// Add employee and useraccount to the specific repositories
		userAccountManager.save(employeeAccount);
		employeeRepository.save(employee);

		return "redirect:/manager";
	}

	// Mapping to delete an employee from the repository
	@RequestMapping("/deleteEmployee")
	public String deleteEmployee(@RequestParam("employeeId") long employeeId) {
		// Redirect if employee doesn't exists
		if (employeeRepository.findById(employeeId) == null) {
			return "redirect:/manager";
		}

		// Get employee, disable his account and delete him from the repository
		Employee deleteThisEmployee = employeeRepository.findById(employeeId);
		userAccountManager.disable(deleteThisEmployee.getUserAccount().getId());
		employeeRepository.delete(deleteThisEmployee);

		return "redirect:/manager";
	}

	// Mapping to add a certain role to a useraccount
	@RequestMapping(params = "save", value = "/editAccount", method = RequestMethod.POST)
	public String addRole(@RequestParam("userNameEdit") String userName,
			@RequestParam("roles") String role) {

		// Define a role by the given string "role"
		final Role addRole = new Role(role);

		// Redirect if the useraccount doesn't exist or already have the role
		// attached
		if (!(userAccountManager.findByUsername(userName).isPresent())
				|| userAccountManager.findByUsername(userName).get()
						.hasRole(addRole)) {
			return "redirect:/manager";
		}

		// Add role to the useraccount and save it
		userAccountManager.findByUsername(userName).get().add(addRole);
		userAccountManager.save(userAccountManager.findByUsername(userName)
				.get());
		return "redirect:/manager";
	}

	// Mapping to delete a certain role from a useraccount
	@RequestMapping(params = "delete", value = "/editAccount", method = RequestMethod.POST)
	public String deleteRole(@RequestParam("userNameEdit") String userName,
			@RequestParam("roles") String role) {

		// Define a role by the given string "role"
		final Role deleteRole = new Role(role);

		// Redirect if the useraccount doesn't exist or doesn't have the role
		// attached
		if (!(userAccountManager.findByUsername(userName).isPresent())
				|| !(userAccountManager.findByUsername(userName).get()
						.hasRole(deleteRole))) {
			return "redirect:/manager";
		}

		// Delete role from the useraccount and save it
		userAccountManager.findByUsername(userName).get().remove(deleteRole);
		userAccountManager.save(userAccountManager.findByUsername(userName)
				.get());

		return "redirect:/manager";
	}

	// Mapping to activate a certain account
	@RequestMapping(params = "enable", value = "/editAccount", method = RequestMethod.POST)
	public String enableAccount(@RequestParam("userNameEdit") String userName) {

		// Redirect if the useraccount doesn't exist or is already enabled
		if (!(userAccountManager.findByUsername(userName).isPresent())
				|| userAccountManager.findByUsername(userName).get()
						.isEnabled()) {
			return "redirect:/manager";
		}

		// Enable useraccount and save it
		userAccountManager.enable(userAccountManager.findByUsername(userName)
				.get().getIdentifier());
		userAccountManager.save(userAccountManager.findByUsername(userName)
				.get());

		return "redirect:/manager";
	}

	// Mapping to deactivate a certain account
	@RequestMapping(params = "disable", value = "/editAccount", method = RequestMethod.POST)
	public String disableAccount(@RequestParam("userNameEdit") String userName) {

		// Redirect if the useraccount doesn't exist or is already disabled
		if (!(userAccountManager.findByUsername(userName).isPresent())
				|| !(userAccountManager.findByUsername(userName).get()
						.isEnabled())) {
			return "redirect:/manager";
		}

		// Disable useraccount and save it
		userAccountManager.disable(userAccountManager.findByUsername(userName)
				.get().getIdentifier());
		userAccountManager.save(userAccountManager.findByUsername(userName)
				.get());

		return "redirect:/manager";
	}

	@RequestMapping("/Finances")
	public String finances() {
		mode = "finances";
		return "redirect:/manager";
	}

	@RequestMapping("/Accountmanagement")
	public String newLogin() {
		mode = "accounts";
		return "redirect:/manager";
	}

	@RequestMapping("/Terminal")
	public String terminal() {
		mode = "terminal";
		return "redirect:/manager";
	}
}
