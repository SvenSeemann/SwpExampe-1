package fviv.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;

import fviv.AbstractIntegrationTests;
import fviv.model.EmployeeRepository;

/**
 *@author Hendric Eckelt
*/

public class ManagerControllerTest extends AbstractIntegrationTests{
	
	@Autowired AuthenticationManager am;
	@Autowired ManagerController controller;
	@Autowired EmployeeRepository employeeRepository;
	
	protected void login(String userName, String password){
		Authentication auth = new UsernamePasswordAuthenticationToken(userName, password);
		SecurityContextHolder.getContext().setAuthentication(am.authenticate(auth));
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void managerControllerIntegrationTest(){
		login("manager", "123");
		
		ModelMap modelMap = new ModelMap();
		String returnedView = controller.index(modelMap);
		
		assertThat(returnedView, is("manager"));
		assertThat((Iterable<Object>) modelMap.get("employeelist"), is(iterableWithSize((int) employeeRepository.count())));
	}
}
