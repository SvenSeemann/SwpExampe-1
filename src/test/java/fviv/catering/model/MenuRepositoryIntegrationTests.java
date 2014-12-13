package fviv.catering.model;

import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;

import fviv.AbstractIntegrationTests;

import fviv.catering.model.Menu.Type;

public class MenuRepositoryIntegrationTests extends AbstractIntegrationTests {
	
	@Autowired MenusRepository menus;
	
	@Test
	public void findsAllDrinks() {
		
		Iterable<Menu> result = menus.findByType(Type.DRINK);
		assertThat(result, is(iterableWithSize(5)));
	}
	
}
