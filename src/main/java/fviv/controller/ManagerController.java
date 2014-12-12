package fviv.controller;

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
class ManagerFunctionsController{
	private final EmployeeRepository employeeRepository;
	private final ExpenseRepository expenseRepository;
	private final UserAccountManager userAccountManager;
	
	@Autowired
	public ManagerFunctionsController(EmployeeRepository employeeRepository, ExpenseRepository expenseRepository, UserAccountManager userAccountManager){
		this.employeeRepository = employeeRepository;
		this.expenseRepository = expenseRepository;
		this.userAccountManager = userAccountManager;
	}
	
	@RequestMapping({"/manager"})
	public String index(){
		return "manager";
	}
	
	@RequestMapping("/checkEmployees")
	public String checkEmployees(ModelMap modelMap){
		modelMap.addAttribute("employeelist", employeeRepository.findAll());
		modelMap.addAttribute("registration", new Registration());
		return "checkEmployees";
	}	
	
	@RequestMapping("/newEmployee")
	public String newEmployee(@ModelAttribute(value="registration") @Valid Registration registration, BindingResult results){
		//Return to checkEmployees if new employee data are invalid or incomplete
		if(results.hasErrors())return "redirect:/checkEmployees";
		
		final Role employeeRole = new Role("ROLE_EMPLOYEE");
		
		//Create useraccount
		UserAccount employeeAccount = userAccountManager.create(registration.getLastname(), "123", employeeRole);
		
		//Create employee
		Employee employee = new Employee(employeeAccount, registration.getLastname(), registration.getFirstname(), 
										registration.getEmail(), registration.getPhone());
		
		//Add employee and useraccount to the specific repositories
		userAccountManager.save(employeeAccount);
		employeeRepository.save(employee);
		
		return "redirect:/checkEmployees";
	}
	
	@RequestMapping("/deleteEmployee")
	public String deleteEmployee(@RequestParam("eId") long eId){
		if(employeeRepository.findById(eId) == null){
			return "redirect:/checkEmployees";
		}
		
		Employee deleteThisEmployee = employeeRepository.findById(eId);
		userAccountManager.disable(deleteThisEmployee.getUserAccount().getId());;
		employeeRepository.delete(deleteThisEmployee);
		
		return "redirect:/checkEmployees";
	}
	
	@RequestMapping("/checkFinances")
	public String checkFinances(ModelMap modelMap){
		float salaryTotal = 0, cateringTotal = 0, rentTotal = 0, deposit = 0;
		modelMap.addAttribute("salary", expenseRepository.findByExpenseType("salary"));
		modelMap.addAttribute("catering", expenseRepository.findByExpenseType("catering"));
		modelMap.addAttribute("rent", expenseRepository.findByExpenseType("rent"));
		
		for(Expense exp : expenseRepository.findByExpenseType("salary")){
			salaryTotal += exp.getAmount();
		}
		
		for(Expense exp : expenseRepository.findByExpenseType("catering")){
			cateringTotal += exp.getAmount();
		}
		
		for(Expense exp : expenseRepository.findByExpenseType("rent")){
			rentTotal += exp.getAmount();
		}
		
		for(Expense exp : expenseRepository.findByExpenseType("deposit")){
			deposit += exp.getAmount();
		}
		
		modelMap.addAttribute("deposit", deposit);
		modelMap.addAttribute("salaryTotal", salaryTotal);
		modelMap.addAttribute("cateringTotal", cateringTotal);
		modelMap.addAttribute("rentTotal", rentTotal);
		return "checkFinances";
	}
	
	@RequestMapping("/accountAdministration")
	public String newLogin(ModelMap modelMap){
		return "accountAdministration";
	}
}
