package fviv.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import fviv.model.EmployeeRepository;
import fviv.model.Expense;
import fviv.model.ExpenseRepository;

@Controller
class ManagerFunctionsController{
	@Autowired
	private final EmployeeRepository employeeRepository;
	private final ExpenseRepository expenseRepository;
	
	@Autowired
	public ManagerFunctionsController(EmployeeRepository employeeRepository, ExpenseRepository expenseRepository){
		this.employeeRepository = employeeRepository;
		this.expenseRepository = expenseRepository;
	}
	
	@RequestMapping({"/","/manager"})
	public String index(){
		return "manager";
	}
	
	@RequestMapping("/checkEmployees")
	public String checkEmployees(ModelMap modelMap){
		modelMap.addAttribute("employeelist", employeeRepository.findAll());
		return "checkEmployees";
	}
	
	@RequestMapping("/checkFinances")
	public String checkFinances(ModelMap modelMap){
		float salaryTotal = 0, cateringTotal = 0, rentTotal = 0;
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
		
		modelMap.addAttribute("salaryTotal", salaryTotal);
		modelMap.addAttribute("cateringTotal", cateringTotal);
		modelMap.addAttribute("rentTotal", rentTotal);
		return "checkFinances";
	}
	
	@RequestMapping("/newLogin")
	public String newLogin(){
		return "newLogin";
	}
}
