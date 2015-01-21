package fviv.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.junit.Assert.assertThat;
import static org.joda.money.CurrencyUnit.EUR;

import java.util.Optional;

import org.joda.money.Money;
import org.junit.Test;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.Cart;
import org.salespointframework.quantity.Units;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;

import fviv.AbstractIntegrationTests;
import fviv.catering.model.Menu;
import fviv.catering.model.MenusRepository;
import fviv.catering.model.Menu.MenuType;
import fviv.festival.FestivalRepository;
import fviv.model.Finance.FinanceType;
import fviv.model.FinanceRepository;

/**
 * @author Hendric Eckelt
 */

public class CateringControllerTest extends AbstractIntegrationTests {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	CateringController controller;
	@Autowired
	FestivalRepository festivalRepository;
	@Autowired
	MenusRepository menusRepository;
	@Autowired
	UserAccountManager userAccountManager;
	@Autowired
	Inventory<InventoryItem> inventory;
	@Autowired
	FinanceRepository financeRepository;

	protected void login(String userName, String password) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				userName, password);
		SecurityContextHolder.getContext().setAuthentication(
				authenticationManager.authenticate(authentication));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void CateringControllerIndexTest() {
		login("caterer", "123");

		ModelMap modelMap = new ModelMap();
		String returnedView = controller.catering(modelMap);

		// returnedView has to be catering
		assertThat(returnedView, is("/catering"));

		// ModelMap "festival" must contain all festivals from the
		// repository
		assertThat((Iterable<Object>) modelMap.get("festivals"),
				is(iterableWithSize((int) festivalRepository.count())));

		// ModelMap "meals" and "drinks" must be empty, because no festival is
		// selected
		assertThat((Iterable<Object>) modelMap.get("meals"),
				is(iterableWithSize(0)));
		assertThat((Iterable<Object>) modelMap.get("drinks"),
				is(iterableWithSize(0)));
	}

	@Test
	public void CateringControllerAddMealTest() {
		login("caterer", "123");

		// Create the needed attributes
		ModelMap modelMap = new ModelMap();

		Menu menu = new Menu(1, "testMeal", Money.of(EUR, 2), Money.of(EUR, 5),
				MenuType.MEAL);
		InventoryItem inventoryItem = new InventoryItem(menu, Units.of(1));
		inventory.save(inventoryItem);

		Cart cart = new Cart();
		UserAccount userAccount = userAccountManager.findByUsername("caterer")
				.get();

		String returnedView = controller.addMeal(modelMap, menu, cart, null,
				userAccount);

		// returnedView has to be "redirect:/catering"
		assertThat(returnedView, is("redirect:/catering"));

		// menu must not be orderable anymore (last unit was ordered)
		assertThat(menusRepository.findByName("testMeal").getOrderable(),
				is(false));
	}

	@Test
	public void CateringControllerCancelTest() {
		login("caterer", "123");

		Cart cart = new Cart();
		Menu menu = new Menu(1, "testMeal", Money.of(EUR, 2), Money.of(EUR, 5),
				MenuType.MEAL);
		cart.addOrUpdateItem(menu, Units.of(1));

		String returnedView = controller.cancel(null, cart);

		// returnedView has to be "redirect:/catering"
		assertThat(returnedView, is("redirect:/catering"));

		// cart has to be empty after cancel
		assertThat(cart.isEmpty(), is(true));
	}

	@Test
	public void CateringControllerConfirmTest() {
		login("caterer", "123");

		long amountOfFinancesInRepository = financeRepository
				.findByFinanceType(FinanceType.CATERING).size();

		// Create a cart with one unit of "testMeal"
		Cart cart = new Cart();

		Optional<UserAccount> userAccount = userAccountManager
				.findByUsername("caterer");

		String returnedView = controller.confirm(cart, userAccount);

		// returnedView has to be "redirect:/catering"
		assertThat(returnedView, is("redirect:/catering"));

		// Cart has to be clear after confirm
		assertThat(cart.isEmpty(), is(true));

		// There must be one finance in the repository after confirm
		amountOfFinancesInRepository += 1;
		assertThat(financeRepository.findByFinanceType(FinanceType.CATERING),
				is(iterableWithSize((int) amountOfFinancesInRepository)));
	}
}
