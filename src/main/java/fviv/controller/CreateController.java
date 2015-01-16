package fviv.controller;

import static org.joda.money.CurrencyUnit.EUR;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.LinkedList;

import org.joda.money.Money;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Units;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fviv.areaPlanner.AreaItem;
import fviv.areaPlanner.AreaItem.Type;
import fviv.areaPlanner.AreaItemsRepository;
import fviv.catering.model.Menu;
import fviv.catering.model.Menu.MenuType;
import fviv.catering.model.MenusRepository;
import fviv.festival.FestivalRepository;
import fviv.festival.Festival;
import fviv.location.Location;
import fviv.location.LocationRepository;
import fviv.model.Finance;
import fviv.model.Finance.FinanceType;
import fviv.model.Finance.Reference;
import fviv.model.FinanceRepository;

// by niko // festivalerstellungscontroller

@Controller
@PreAuthorize("hasRole('ROLE_BOSS')")
public class CreateController {
	private static final String IS_AJAX_HEADER = "X-Requested-With=XMLHttpRequest";
	private final FestivalRepository festivalRepository;
	private final LocationRepository locationRepository;
	private String mode = "festival";
	private Festival selected;
	private AreaItemsRepository areaItems;
	private FinanceRepository financeRepository;
	private MenusRepository menusRepository;
	private Inventory inventory;

	@Autowired
	public CreateController(FestivalRepository festivalRepository,
			LocationRepository locationrepository,
			FinanceRepository financeRepository, AreaItemsRepository areaItems,
			MenusRepository menusRepository, Inventory inventory) {
		this.festivalRepository = festivalRepository;
		this.locationRepository = locationrepository;
		this.financeRepository = financeRepository;
		this.menusRepository = menusRepository;
		this.areaItems = areaItems;
		this.inventory = inventory;
	}

	/**
	 * index method and Modelmapping of the festivallist and locationlist
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping({ "/festival" })
	public String index(ModelMap modelMap) {
		// mode = "festival";
		modelMap.addAttribute("festivallist", festivalRepository.findAll());
		modelMap.addAttribute("locationlist", locationRepository.findAll());
		return "festival";
	}

	@ModelAttribute("selected")
	public Festival festival() {
		return selected;
	}

	@RequestMapping(value = "/setQuantityOfEmployees")
	public String quantityEmployees(@RequestParam("festivalId") long festivalId) {
		mode = "setQuantity";
		selected = festivalRepository.findOne(festivalId);
		return "redirect:/festival";
	}

	@RequestMapping(value = "/setSalary")
	public String setSalary(@RequestParam("festivalId") long festivalId) {
		mode = "setSalary";
		selected = festivalRepository.findOne(festivalId);

		return "redirect:/festival";
	}

	@RequestMapping(value = "/festival/create", method = RequestMethod.POST)
	public String createFestival() {
		mode = "festival";
		return "redirect:/festival";
	}

	@RequestMapping(value = "/editFestivals", method = RequestMethod.POST)
	public String areaplan() {
		mode = "editFestivals";
		return "redirect:/festival";
	}

	@RequestMapping(value = "/setup/area", method = RequestMethod.POST)
	public String setUpArea(@RequestParam("festivalId") long festivalId) {
		// hier auf max planner zugreifen
		this.selected = festivalRepository.findOne(festivalId);
		festivalRepository.save(selected);
		return "redirect:/planning/" + selected.getId();
	}

	public String setUpAreaSave() {
		// hier auf max planner zugreifen
		// this.selected.setArea(areaItems);
		return "redirect:/festival";
	}

	/**
	 * Calculates finances (salary) for each employee after setting the amount
	 * of the salary per hour multiplied by 8 working hours.
	 * 
	 * @param salManagement
	 * @param salLeadership
	 * @param salCatering
	 * @param salSecurity
	 * @param salCleaning
	 * @return link
	 */
	@RequestMapping("/setNewSalary")
	public String setNewSalary(
			@RequestParam("salManagement") BigDecimal salManagement,
			@RequestParam("salLeadership") BigDecimal salLeadership,
			@RequestParam("salCatering") BigDecimal salCatering,
			@RequestParam("salSecurity") BigDecimal salSecurity,
			@RequestParam("salCleaning") BigDecimal salCleaning) {

		if (salManagement.floatValue() != 0)
			selected.setManagementSalaryPerHour(Money.of(EUR, salManagement));
		if (salLeadership.floatValue() != 0)
			selected.setLeadershipSalaryPerHour(Money.of(EUR, salManagement));
		if (salCatering.floatValue() != 0)
			selected.setCateringSalaryPerHour(Money.of(EUR, salCatering));
		if (salSecurity.floatValue() != 0)
			selected.setSecuritySalaryPerHour(Money.of(EUR, salSecurity));
		if (salCleaning.floatValue() != 0)
			selected.setCleaningSalaryPerHour(Money.of(EUR, salCleaning));
		festivalRepository.save(selected);

		financeRepository.delete(financeRepository
				.findByFinanceType(FinanceType.SALARY));

		Period dateHelper;
		dateHelper = selected.getStartDatum().until(selected.getEndDatum());
		int days = dateHelper.getDays();
		Money salaryTotalManagement = selected.getManagementSalaryPerHour()
				.multipliedBy(days).multipliedBy(selected.getQuantManagement())
				.multipliedBy(8);
		Money salaryTotalLeadership = selected.getLeadershipSalaryPerHour()
				.multipliedBy(days).multipliedBy(selected.getQuantLeadership())
				.multipliedBy(8);
		Money salaryTotalCatering = selected.getCateringSalaryPerHour()
				.multipliedBy(days).multipliedBy(selected.getQuantCatering())
				.multipliedBy(8);
		Money salaryTotalSecurity = selected.getSecuritySalaryPerHour()
				.multipliedBy(days).multipliedBy(selected.getQuantSecurity())
				.multipliedBy(8);
		Money salaryTotalCleaning = selected.getCleaningSalaryPerHour()
				.multipliedBy(days).multipliedBy(selected.getQuantCleaning())
				.multipliedBy(8);

		financeRepository.save(new Finance(selected.getId(), Reference.EXPENSE,
				salaryTotalManagement, FinanceType.SALARY));
		financeRepository.save(new Finance(selected.getId(), Reference.EXPENSE,
				salaryTotalLeadership, FinanceType.SALARY));
		financeRepository.save(new Finance(selected.getId(), Reference.EXPENSE,
				salaryTotalCatering, FinanceType.SALARY));
		financeRepository.save(new Finance(selected.getId(), Reference.EXPENSE,
				salaryTotalSecurity, FinanceType.SALARY));
		financeRepository.save(new Finance(selected.getId(), Reference.EXPENSE,
				salaryTotalCleaning, FinanceType.SALARY));
		return "redirect:/festival";
	}

	/**
	 * Calculates finances (salary) for each employee after setting the quantity
	 * of employees multiplied by 8 working hours.
	 * 
	 * @param quantManagement
	 * @param quantCatering
	 * @param quantSecurity
	 * @param quantCleaning
	 * @return link
	 */
	@RequestMapping("/setNewQuant")
	public String setNewQuant(@RequestParam("quantCatering") int quantCatering,
			@RequestParam("quantSecurity") int quantSecurity,
			@RequestParam("quantCleaning") int quantCleaning) {

		if (quantCatering != 0)
			selected.setQuantCatering(quantCatering);
		if (quantSecurity != 0)
			selected.setQuantSecurity(quantSecurity);
		if (quantCleaning != 0)
			selected.setQuantCleaning(quantCleaning);
		festivalRepository.save(selected);

		financeRepository.delete(financeRepository
				.findByFinanceType(FinanceType.SALARY));

		Period dateHelper;
		dateHelper = selected.getStartDatum().until(selected.getEndDatum());
		int days = dateHelper.getDays();
		Money salaryTotalManagement = selected.getManagementSalaryPerHour()
				.multipliedBy(days).multipliedBy(selected.getQuantManagement())
				.multipliedBy(8);
		Money salaryTotalLeadership = selected.getLeadershipSalaryPerHour()
				.multipliedBy(days).multipliedBy(selected.getQuantLeadership())
				.multipliedBy(8);
		Money salaryTotalCatering = selected.getCateringSalaryPerHour()
				.multipliedBy(days).multipliedBy(selected.getQuantCatering())
				.multipliedBy(8);
		Money salaryTotalSecurity = selected.getSecuritySalaryPerHour()
				.multipliedBy(days).multipliedBy(selected.getQuantSecurity())
				.multipliedBy(8);
		Money salaryTotalCleaning = selected.getCleaningSalaryPerHour()
				.multipliedBy(days).multipliedBy(selected.getQuantCleaning())
				.multipliedBy(8);

		financeRepository.save(new Finance(selected.getId(), Reference.EXPENSE,
				salaryTotalManagement, FinanceType.SALARY));
		financeRepository.save(new Finance(selected.getId(), Reference.EXPENSE,
				salaryTotalLeadership, FinanceType.SALARY));
		financeRepository.save(new Finance(selected.getId(), Reference.EXPENSE,
				salaryTotalCatering, FinanceType.SALARY));
		financeRepository.save(new Finance(selected.getId(), Reference.EXPENSE,
				salaryTotalSecurity, FinanceType.SALARY));
		financeRepository.save(new Finance(selected.getId(), Reference.EXPENSE,
				salaryTotalCleaning, FinanceType.SALARY));

		return "redirect:/festival";
	}

	@ModelAttribute("festivalmode")
	public String festivalmode() {
		return mode;
	}

	/**
	 * Creating Festival from the inputs of the site
	 * 
	 * @param festivalName
	 * @param startDate
	 * @param endDate
	 * @param actors
	 * @param location
	 * @param preisTag
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/newFestival", method = RequestMethod.POST)
	public String newFestival(
			@RequestParam("festivalName") String festivalName,
			@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate,
			@RequestParam("actors") String actors,
			@RequestParam("preisTag") String preisTag,
			@RequestParam("locationId") long locationId) throws ParseException {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LL-dd");

		LocalDate dateStart = LocalDate.parse(startDate, formatter);
		LocalDate dateEnd = LocalDate.parse(endDate, formatter);

		Festival festival = new Festival(dateStart, dateEnd, festivalName,
				locationId, actors, (int) locationRepository.findById(
						locationId).getMaxVisitors(), Money.of(EUR,
						Long.parseLong(preisTag)));

		
		
		long festivalId = festivalRepository.save(festival).getId();
		float factor = (835/locationRepository.findById(locationId).getWidth());
		
		areaItems.save(new AreaItem(Type.AREA, "Areal", locationRepository
				.findById(locationId).getWidth(), locationRepository.findById(
				locationId).getHeight(), 0, 0, factor, festivalRepository
				.findById(festivalId)));
		
		Period dateHelper;
		dateHelper = festival.getStartDatum().until(festival.getEndDatum());
		int days = dateHelper.getDays() + 1;
		Money costTot = locationRepository.findById(locationId).getCostPerDay().multipliedBy(days);
		Finance locationCost = new Finance(festivalId, Reference.EXPENSE, costTot, FinanceType.RENT);
		financeRepository.save(locationCost);
		
		// --- Initialize Menus for new festival --- \\
		
			// --- Meals --- \\

				Menu Meal1 = new Menu(festivalId, "Pommes Frites", Money.of(EUR, 0.50),
						Money.of(EUR, 2.50), MenuType.MEAL);
				Menu Meal2 = new Menu(festivalId, "Pommes Spezial", Money.of(EUR, 0.70),
						Money.of(EUR, 3.50), MenuType.MEAL);
				Menu Meal3 = new Menu(festivalId, "Bratwurst", Money.of(EUR, 0.20), Money.of(
						EUR, 2.00), MenuType.MEAL);
				Menu Meal4 = new Menu(festivalId, "Currywurst", Money.of(EUR, 0.50), Money.of(
						EUR, 3.00), MenuType.MEAL);
				Menu Meal5 = new Menu(festivalId, "Currywurst mit Pommes", Money.of(EUR, 1.00),
						Money.of(EUR, 4.50), MenuType.MEAL);
				Menu Meal6 = new Menu(festivalId, "Stück Pizza", Money.of(EUR, 0.40), Money.of(
						EUR, 2.50), MenuType.MEAL);
				Menu Meal7 = new Menu(festivalId, "Vanilleeis", Money.of(EUR, 0.20), Money.of(
						EUR, 1.00), MenuType.MEAL);
				Menu Meal8 = new Menu(festivalId, "Schokoeis", Money.of(EUR, 0.20), Money.of(
						EUR, 1.00), MenuType.MEAL);

				// --- Drinks --- \\

				Menu Drink1 = new Menu(festivalId, "Pils", Money.of(EUR, 0.50), Money.of(EUR,
						2.50), MenuType.DRINK);
				Menu Drink2 = new Menu(festivalId, "Alt", Money.of(EUR, 0.60), Money.of(EUR,
						3.00), MenuType.DRINK);
				Menu Drink3 = new Menu(festivalId, "Alkoholfrei", Money.of(EUR, 0.50), Money.of(
						EUR, 3.00), MenuType.DRINK);
				Menu Drink4 = new Menu(festivalId, "Softdrink", Money.of(EUR, 0.50), Money.of(
						EUR, 2.20), MenuType.DRINK);
				Menu Drink5 = new Menu(festivalId, "Wein", Money.of(EUR, 0.70), Money.of(EUR,
						4.00), MenuType.DRINK);
				
				menusRepository
				.save(Arrays.asList(Meal1, Meal2, Meal3, Meal4, Meal5, Meal6,
						Meal7, Meal8, Drink1, Drink2, Drink3, Drink4,
						Drink5));

		for (Menu menu : menusRepository.findAll()) {
			menu.setOrderable(true);
			InventoryItem inventoryItem = new InventoryItem(menu, Units.of(50));
			inventory.save(inventoryItem);
		}
		
		
		return "redirect:/festival";
	}
	
	// Setter for jUnit testing
	public void setSelected(Festival festival){
		this.selected = festival;
	}
}