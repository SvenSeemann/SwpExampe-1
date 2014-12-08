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
import fviv.catering.model.StaffRepository;
import fviv.catering.model.Menu.Type;

@Component
public class CateringDataInitializer implements DataInitializer {

	private final Inventory<InventoryItem> inventory;
	private final MenusRepository menusRepository;
	private final UserAccountManager userAccountManager;
	private final StaffRepository staffRepository;
	
	
	@Autowired
	public CateringDataInitializer (MenusRepository menusRepository,
									Inventory<InventoryItem> inventory,
									UserAccountManager userAccountManager,
									StaffRepository staffRepository) {
		
		
		Assert.notNull(inventory, "Inventory must not be null!");
		Assert.notNull(menusRepository, "MenusRepository must not be null!");		
		
		this.menusRepository = menusRepository;
		this.inventory = inventory;
		this.userAccountManager = userAccountManager;
		this.staffRepository = staffRepository;
	}

	
	@Override
	public void initialize() {
		initializeMenus(menusRepository, inventory);
		initializeUsers(userAccountManager, staffRepository);
	}
	
	private void initializeMenus(MenusRepository menusRepository,
								 Inventory<InventoryItem> inventory) {
		
		Menu Meal1 = new Menu("Pommes Frites", Money.of(EUR, 2.50), Type.MEAL);
		Menu Meal2 = new Menu("Pommes Spezial", Money.of(EUR, 3.50), Type.MEAL);
		Menu Meal3 = new Menu("Bratwurst", Money.of(EUR, 2.00), Type.MEAL);
		Menu Meal4 = new Menu("Currywurst", Money.of(EUR, 3.00), Type.MEAL);
		Menu Meal5 = new Menu("Currywurst mit Pommes", Money.of(EUR, 4.50), Type.MEAL);
		Menu Meal6 = new Menu("Stück Pizza", Money.of(EUR, 2.50), Type.MEAL);
		Menu Meal7 = new Menu("Vanilleeis", Money.of(EUR, 1.00), Type.MEAL);
		Menu Meal8 = new Menu("Schokoeis", Money.of(EUR, 1.00), Type.MEAL);
		
		// --- Getränke --- \\
		
		Menu Drink1 = new Menu("Pils", Money.of(EUR, 2.50), Type.DRINK);
		Menu Drink2 = new Menu("Alt", Money.of(EUR, 3.00), Type.DRINK);
		Menu Drink3 = new Menu("Alkoholfrei", Money.of(EUR, 3.00), Type.DRINK);
		Menu Drink4 = new Menu("Softdrink", Money.of(EUR, 2.20), Type.DRINK);
		Menu Drink5 = new Menu("Wein", Money.of(EUR, 4.00), Type.DRINK);
		
		menusRepository.save(Arrays.asList(Meal1, Meal2, Meal3, Meal4, Meal5, Meal6,
				Meal7, Meal8, Drink1, Drink2, Drink3, Drink4, Drink5));
		
		for (Menu Menu : menusRepository.findAll()) {
			InventoryItem inventoryItem = new InventoryItem(Menu, Units.of(50));
			inventory.save(inventoryItem);
		}
		
		for (Menu Menu : menusRepository.findAll()) {
			InventoryItem inventoryItem = new InventoryItem(Menu, Units.of(50));
			inventory.save(inventoryItem);
		}
	}

	private void initializeUsers(UserAccountManager userAccountManager, StaffRepository staffRepository){
	
		final Role catererRole = new Role("ROLE_CATERER");
		
		UserAccount caterer_1 = userAccountManager.create("tussi", "pommes", catererRole);
		userAccountManager.save(caterer_1);
		
		staffRepository.save(caterer_1);
	}
}
