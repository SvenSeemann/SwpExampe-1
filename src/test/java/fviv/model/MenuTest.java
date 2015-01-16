package fviv.model;

import org.joda.money.Money;

import static org.joda.money.CurrencyUnit.EUR;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import fviv.AbstractIntegrationTests;
import fviv.catering.model.Menu;
import fviv.catering.model.Menu.MenuType;
import fviv.catering.model.MenusRepository;

/**
 * @author Hendric Eckelt
 */

public class MenuTest extends AbstractIntegrationTests {
	@Autowired
	MenusRepository menusRepository;

	@Test
	public void newMenuTest() {
		// Create new menus
		Menu testMenu1 = new Menu(2, "testMeal", Money.of(EUR, 1), Money.of(
				EUR, 3), MenuType.MEAL);
		Menu testMenu2 = new Menu(1, "testDrink", Money.of(EUR, 0.52),
				Money.of(EUR, 1.50), MenuType.DRINK);
		menusRepository.save(testMenu1);
		menusRepository.save(testMenu2);

		// Validate name
		assertThat(testMenu1.getName().equals("testMeal"), is(true));
		assertThat(testMenu2.getName().equals("testDrink"), is(true));

		// Validate menuType
		assertThat(testMenu1.getMenuType(), is(MenuType.MEAL));
		assertThat(testMenu2.getMenuType(), is(MenuType.DRINK));

		// Validate purchasePrice
		assertThat(testMenu1.getPurchasePrice().abs(), is(Money.of(EUR, 1)));
		assertThat(testMenu2.getPurchasePrice().abs(), is(Money.of(EUR, 0.52)
				.abs()));

		// Check if the menus got saved to the repository
		assertThat(menusRepository.findOne(testMenu1.getId()).getName().equals("testMeal"), is(true));
		assertThat(menusRepository.findOne(testMenu2.getId()).getName().equals("testDrink"), is(true));	
	}
}
