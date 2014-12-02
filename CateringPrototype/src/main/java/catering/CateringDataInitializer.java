package catering;

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

import catering.model.Drink;
import catering.model.DrinksRepository;
import catering.model.Meal;
import catering.model.MealsRepository;
import catering.model.StaffRepository;

@Component
public class CateringDataInitializer implements DataInitializer {

	private final Inventory<InventoryItem> inventory;
	private final MealsRepository mealsRepository;
	private final DrinksRepository drinksRepository;
	private final UserAccountManager userAccountManager;
	private final StaffRepository staffRepository;
	
	
	@Autowired
	public CateringDataInitializer (MealsRepository mealsRepository,
									DrinksRepository drinksRepository,
									Inventory<InventoryItem> inventory,
									UserAccountManager userAccountManager,
									StaffRepository staffRepository) {
		
		
		Assert.notNull(inventory, "Inventory must not be null!");
		Assert.notNull(mealsRepository, "MealsRepository must not be null!");
		Assert.notNull(drinksRepository, "DrinksRepository must not be null!");
		
		
		this.mealsRepository = mealsRepository;
		this.drinksRepository = drinksRepository;
		this.inventory = inventory;
		this.userAccountManager = userAccountManager;
		this.staffRepository = staffRepository;
		//initialize();
	}

	
	@Override
	public void initialize() {
		initializeMenus(mealsRepository, drinksRepository, inventory);
		initializeUsers(userAccountManager, staffRepository);
	}
	
	private void initializeMenus(MealsRepository mealsRepository,
								 DrinksRepository drinksRepository,
								 Inventory<InventoryItem> inventory) {
		
		Meal meal1 = new Meal("Pommes Frites", Money.of(EUR, 2.50));
		Meal meal2 = new Meal("Pommes Spezial", Money.of(EUR, 3.50));
		Meal meal3 = new Meal("Bratwurst", Money.of(EUR, 2.00));
		Meal meal4 = new Meal("Currywurst", Money.of(EUR, 3.00));
		Meal meal5 = new Meal("Currywurst Pommes", Money.of(EUR, 4.50));
		Meal meal6 = new Meal("Stück Pizza", Money.of(EUR, 2.50));
		Meal meal7 = new Meal("Vanilleeis", Money.of(EUR, 1.00));
		Meal meal8 = new Meal("Schokoeis", Money.of(EUR, 1.00));
		Meal meal9 = new Meal("A", Money.of(EUR, 0.00));
		
		// --- Getränke --- \\
		
		Drink drink1 = new Drink("Pils", Money.of(EUR, 2.50));
		Drink drink2 = new Drink("Alt", Money.of(EUR, 3.00));
		Drink drink3 = new Drink("Alkoholfrei", Money.of(EUR, 3.00));
		Drink drink4 = new Drink("Softdrink", Money.of(EUR, 2.20));
		Drink drink5 = new Drink("Wein", Money.of(EUR, 4.00));
		
		mealsRepository.save(Arrays.asList(meal1, meal2, meal3, meal4, meal5, meal6,
				meal7, meal8, meal9));
		
		//mealsRepository.save(meal1);
		
		drinksRepository.save(Arrays.asList(drink1, drink2, drink3, drink4, drink5));
		
		System.out.println(mealsRepository.findAll());
		
		for (Meal meal : mealsRepository.findAll()) {
			InventoryItem inventoryItem = new InventoryItem(meal, Units.of(50));			//löst NullPointerException aus!
			inventory.save(inventoryItem);
		}
		
		for (Drink drink : drinksRepository.findAll()) {
			InventoryItem inventoryItem = new InventoryItem(drink, Units.of(50));
			inventory.save(inventoryItem);
		}
	}

	private void initializeUsers(UserAccountManager userAccountManager, StaffRepository staffRepository){
	
		final Role catererRole = new Role("ROLE_CATERER");
		
		UserAccount caterer_1 = userAccountManager.create("tussi", "pommes", catererRole);
		userAccountManager.save(caterer_1);
		
		staffRepository.save(caterer_1); //vllt ist das mal gut!
	}
}
