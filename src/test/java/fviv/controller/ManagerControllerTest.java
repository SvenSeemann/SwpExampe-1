package fviv.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import fviv.AbstractIntegrationTests;
import fviv.model.EmployeeRepository;

/**
 *@author Hendric Eckelt
*/

public class ManagerControllerTest extends AbstractIntegrationTests{
	
	@Autowired ManagerController controller;
	@Autowired EmployeeRepository employeeRepository;
	
	@Test
	@SuppressWarnings("unchecked")
	public void managerControllerIntegrationTest(){
		ModelMap modelMap = new ModelMap();
		String returnedView = controller.index(modelMap);
		
		assertThat(returnedView, is("manager"));
		assertThat((Iterable<Object>) modelMap.get("employeelist"), is(iterableWithSize((int) employeeRepository.count())));
	}
}
