package fviv.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import fviv.model.EmployeeRepository;

@Controller
class ManagerFunctionsController{
	@Autowired
	private final EmployeeRepository employeeRepository;
	
	@Autowired
	public ManagerFunctionsController(EmployeeRepository employeeRepository){
		this.employeeRepository = employeeRepository;
	}
	
	@RequestMapping("/")
	public String index(){
		return "manager";
	}
	
	@RequestMapping("/checkEmployees")
	public String checkEmployees(ModelMap modelMap){
		modelMap.addAttribute("employeelist", employeeRepository.findAll());
		return "checkEmployees";
	}
	
	@RequestMapping("/checkFinances")
	public String checkFinances(){
		return "checkFinances";
	}
	
	@RequestMapping("/newLogin")
	public String newLogin(){
		return "newLogin";
	}
}
