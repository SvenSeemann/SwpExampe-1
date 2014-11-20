package fviv.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
class ManagerFunctionsController{
	
	@RequestMapping("/")
	public String index(){
		return "manager";
	}
	
	@RequestMapping("/checkEmployees")
	public String checkEmployees(){
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
