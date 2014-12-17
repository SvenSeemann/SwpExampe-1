package fviv.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@PreAuthorize("hasAnyRole('ROLE_BOSS','ROLE_CATERER','ROLE_MANAGER')")
@Controller
public class InternalController {
	
	public InternalController () {
		
	}
	
	@RequestMapping("/internal")
	public String internal() {
		return "internal";
	}
	
	@RequestMapping({"/messaging", "/chat"})
	public String chat() {
		return "messaging";
	}
}
