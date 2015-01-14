package fviv.catering;

import static org.joda.money.CurrencyUnit.EUR;

import java.util.Arrays;

import org.joda.money.Money;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Units;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import fviv.catering.model.Menu;
import fviv.catering.model.MenusRepository;
import fviv.catering.model.Menu.Type;
import fviv.model.StaffRepository;

/**
 * @author Niklas Fallik
 */

@Component
public class CateringDataInitializer implements DataInitializer {

	private final Inventory<InventoryItem> inventory;
	private final MenusRepository menusRepository;

	@Autowired
	public CateringDataInitializer(MenusRepository menusRepository,
			Inventory<InventoryItem> inventory) {

		Assert.notNull(inventory, "Inventory must not be null!");
		Assert.notNull(menusRepository, "MenusRepository must not be null!");

		this.menusRepository = menusRepository;
		this.inventory = inventory;
	}

	@Override
	public void initialize() {
		initializeMenus(menusRepository, inventory);
	}

	private void initializeMenus(MenusRepository menusRepository,
			Inventory<InventoryItem> inventory) {

		// with long festivalId

		// --- Menus --- \\

		Menu Meal1 = new Menu(1, "Pommes Frites", Money.of(EUR, 0.50),
				Money.of(EUR, 2.50), Type.MEAL);
		Menu Meal2 = new Menu(1, "Pommes Spezial", Money.of(EUR, 0.70),
				Money.of(EUR, 3.50), Type.MEAL);
		Menu Meal3 = new Menu(1, "Bratwurst", Money.of(EUR, 0.20), Money.of(
				EUR, 2.00), Type.MEAL);
		Menu Meal4 = new Menu(1, "Currywurst", Money.of(EUR, 0.50), Money.of(
				EUR, 3.00), Type.MEAL);
		Menu Meal5 = new Menu(1, "Currywurst mit Pommes", Money.of(EUR, 1.00),
				Money.of(EUR, 4.50), Type.MEAL);
		Menu Meal6 = new Menu(1, "Stück Pizza", Money.of(EUR, 0.40), Money.of(
				EUR, 2.50), Type.MEAL);
		Menu Meal7 = new Menu(1, "Vanilleeis", Money.of(EUR, 0.20), Money.of(
				EUR, 1.00), Type.MEAL);
		Menu Meal8 = new Menu(1, "Schokoeis", Money.of(EUR, 0.20), Money.of(
				EUR, 1.00), Type.MEAL);

		// --- Drinks --- \\

		Menu Drink1 = new Menu(1, "Pils", Money.of(EUR, 0.50), Money.of(EUR,
				2.50), Type.DRINK);
		Menu Drink2 = new Menu(1, "Alt", Money.of(EUR, 0.60), Money.of(EUR,
				3.00), Type.DRINK);
		Menu Drink3 = new Menu(1, "Alkoholfrei", Money.of(EUR, 0.50), Money.of(
				EUR, 3.00), Type.DRINK);
		Menu Drink4 = new Menu(1, "Softdrink", Money.of(EUR, 0.50), Money.of(
				EUR, 2.20), Type.DRINK);
		Menu Drink5 = new Menu(1, "Wein", Money.of(EUR, 0.70), Money.of(EUR,
				4.00), Type.DRINK);

		// --- Menus --- \\

		Menu Meal9 = new Menu(2, "Pommes Frites", Money.of(EUR, 0.50),
				Money.of(EUR, 2.50), Type.MEAL);
		Menu Meal10 = new Menu(2, "Pommes Spezial", Money.of(EUR, 0.70),
				Money.of(EUR, 3.50), Type.MEAL);
		Menu Meal11 = new Menu(2, "Bratwurst", Money.of(EUR, 0.20), Money.of(
				EUR, 2.00), Type.MEAL);
		Menu Meal12 = new Menu(2, "Currywurst", Money.of(EUR, 0.50), Money.of(
				EUR, 3.00), Type.MEAL);
		Menu Meal13 = new Menu(2, "Currywurst mit Pommes", Money.of(EUR, 1.00),
				Money.of(EUR, 4.50), Type.MEAL);
		Menu Meal14 = new Menu(2, "Stück Pizza", Money.of(EUR, 0.40), Money.of(
				EUR, 2.50), Type.MEAL);
		Menu Meal15 = new Menu(2, "Vanilleeis", Money.of(EUR, 0.20), Money.of(
				EUR, 1.00), Type.MEAL);
		Menu Meal16 = new Menu(2, "Schokoeis", Money.of(EUR, 0.20), Money.of(
				EUR, 1.00), Type.MEAL);

		// --- Drinks --- \\

		Menu Drink6 = new Menu(2, "Pils", Money.of(EUR, 0.50), Money.of(EUR,
				2.50), Type.DRINK);
		Menu Drink7 = new Menu(2, "Alt", Money.of(EUR, 0.60), Money.of(EUR,
				3.00), Type.DRINK);
		Menu Drink8 = new Menu(2, "Alkoholfrei", Money.of(EUR, 0.50), Money.of(
				EUR, 3.00), Type.DRINK);
		Menu Drink9 = new Menu(2, "Softdrink", Money.of(EUR, 0.50), Money.of(
				EUR, 2.20), Type.DRINK);
		Menu Drink10 = new Menu(1, "Wein", Money.of(EUR, 0.70), Money.of(EUR,
				4.00), Type.DRINK);

		menusRepository
				.save(Arrays.asList(Meal1, Meal2, Meal3, Meal4, Meal5, Meal6,
						Meal7, Meal8, Meal9, Meal10, Meal11, Meal12, Meal13,
						Meal14, Meal15, Meal16, Drink1, Drink2, Drink3, Drink4,
						Drink5));

		for (Menu menu : menusRepository.findAll()) {
			InventoryItem inventoryItem = new InventoryItem(menu, Units.of(5));
			inventory.save(inventoryItem);
			for (InventoryItem item : inventory.findAll()) {
				if (item.getQuantity().isGreaterThan(Units.ZERO))
					menusRepository.findByProductIdentifier(
							item.getProduct().getId()).setOrderable(true);
			}
		}
	}
}
