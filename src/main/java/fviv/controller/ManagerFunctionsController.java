package fviv.controller;

import javax.validation.Valid;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import fviv.model.StaffRepository;
import fviv.model.Employee;
import fviv.model.Expense;
import fviv.model.ExpenseRepository;
import fviv.model.Registration;

@PreAuthorize("hasRole('ROLE_MANAGER')")
@Controller
class ManagerFunctionsController{
	private final StaffRepository staffRepository;
	private final ExpenseRepository expenseRepository;
	private final UserAccountManager userAccountManager;
	
	@Autowired
	public ManagerFunctionsController(StaffRepository staffRepository, ExpenseRepository expenseRepository, UserAccountManager userAccountManager){
		this.staffRepository = staffRepository;
		this.expenseRepository = expenseRepository;
		this.userAccountManager = userAccountManager;
	}
	
	@RequestMapping({"/manager"})
	public String index(){
		return "manager";
	}
	
	@RequestMapping("/checkEmployees")
	public String checkEmployees(ModelMap modelMap){
		
		modelMap.addAttribute("employeelist", staffRepository.findAll());
		modelMap.addAttribute("registration", new Registration());
		modelMap.addAttribute("deleteEmployee", new Employee(null, ""));
		return "checkEmployees";
	}	
	
	@RequestMapping("/newEmployee")
	public String newEmployee(@ModelAttribute(value="registration") @Valid Registration registration, BindingResult results){
		//Return to checkEmployees if new employee data are invalid or incomplete
		if(results.hasErrors())return "redirect:/checkEmployees";
		
		//Create new employee
		final Role employeeRole = new Role("ROLE_EMPLOYEE");
		
		UserAccount employeeAccount = userAccountManager.create(registration.getLastname(), "123", employeeRole);
		Employee employee = new Employee(employeeAccount, registration.getPhone());
		userAccountManager.save(employeeAccount);
		staffRepository.save(employee.getUserAccount());
		
		return "redirect:/checkEmployees";
	}
	
	@RequestMapping("/deleteEmployee")
	public String deleteEmployee(@ModelAttribute(value="deleteEmployee") @Valid Employee employee, BindingResult results){
		if(results.hasErrors() || staffRepository.findByUserAccountIdentifier(employee.getUserAccount().getId()) == null){
			return "redirect:/checkEmployees";
		}
		
		UserAccount deleteThisEmployee = staffRepository.findByUserAccountIdentifier(employee.getUserAccount().getId());
		staffRepository.delete(deleteThisEmployee);
		
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
