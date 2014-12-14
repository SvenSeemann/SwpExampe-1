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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import fviv.model.EmployeeRepository;
import fviv.model.Employee;
import fviv.model.Expense;
import fviv.model.ExpenseRepository;
import fviv.model.Registration;

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

	@ModelAttribute("managermode")
	public String managermode() {
		return mode;
	}

	@RequestMapping({ "/manager" })
	public String index(ModelMap modelMap) {
		float salaryTotal = 0, cateringTotal = 0, rentTotal = 0, deposit = 0;

		Iterable<UserAccount> userAccounts = userAccountManager.findAll();
		LinkedList<UserAccount> bossAccounts = new LinkedList<UserAccount>();
		LinkedList<UserAccount> managerAccounts = new LinkedList<UserAccount>();
		LinkedList<UserAccount> catererAccounts = new LinkedList<UserAccount>();
		LinkedList<UserAccount> employeeAccounts = new LinkedList<UserAccount>();
		LinkedList<Role> roles = new LinkedList<Role>();
		roles.add(new Role("ROLE_BOSS"));
		roles.add(new Role("ROLE_MANAGER"));
		roles.add(new Role("ROLE_CATERER"));
		roles.add(new Role("ROLE_EMPLOYEE"));

		
		// Sort accounts by Role
		for (UserAccount userAccount : userAccounts) {
			if (userAccount.hasRole(new Role("ROLE_BOSS")))
				bossAccounts.add(userAccount);
			if (userAccount.hasRole(new Role("ROLE_MANAGER")))
				managerAccounts.add(userAccount);
			if (userAccount.hasRole(new Role("ROLE_CATERER")))
				catererAccounts.add(userAccount);
			if (userAccount.hasRole(new Role("ROLE_EMPLOYEE")))
				employeeAccounts.add(userAccount);
		}

		// Add accounts to modelMap
		modelMap.addAttribute("roles", roles);
		modelMap.addAttribute("bossAccounts", bossAccounts);
		modelMap.addAttribute("managerAccounts", managerAccounts);
		modelMap.addAttribute("catererAccounts", catererAccounts);
		modelMap.addAttribute("employeeAccounts", employeeAccounts);

		// Data needed for registration of new employees
		modelMap.addAttribute("employeelist", employeeRepository.findAll());
		modelMap.addAttribute("registration", new Registration());

		// Finances
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

	@RequestMapping("/newEmployee")
	public String newEmployee(
			@ModelAttribute(value = "registration") @Valid Registration registration,
			BindingResult results) {
		// Return to checkEmployees if new employee data are invalid or
		// incomplete
		if (results.hasErrors())
			return "redirect:/manager";

		final Role employeeRole = new Role("ROLE_EMPLOYEE");

		// Create useraccount
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

	@RequestMapping("/deleteEmployee")
	public String deleteEmployee(@RequestParam("employeeId") long employeeId) {
		if (employeeRepository.findById(employeeId) == null) {
			return "redirect:/manager";
		}

		Employee deleteThisEmployee = employeeRepository.findById(employeeId);
		userAccountManager.disable(deleteThisEmployee.getUserAccount().getId());
		;
		employeeRepository.delete(deleteThisEmployee);

		return "redirect:/manager";
	}
	
	@RequestMapping("/changeRole")
	public String changeRole(@RequestParam("userNameChangeRole") String userName){
		System.out.println("Selected username: "+userName);
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
