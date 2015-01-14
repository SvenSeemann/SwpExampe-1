package fviv.controller;

import static org.joda.money.CurrencyUnit.EUR;

import java.util.Collection;
import java.util.LinkedList;

import javax.validation.Valid;

import org.joda.money.Money;
import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Units;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import fviv.catering.model.Menu;
import fviv.catering.model.MenusRepository;
import fviv.festival.Festival;
import fviv.festival.FestivalRepository;
import fviv.model.Employee.Departement;
import fviv.model.EmployeeRepository;
import fviv.model.Employee;
import fviv.model.Finance;
import fviv.model.Finance.FinanceType;
import fviv.model.Finance.Reference;
import fviv.model.FinanceRepository;
import fviv.model.Registration;

/**
 * @author Hendric Eckelt
 * @author Niklas Fallik
 */

@PreAuthorize("hasRole('ROLE_LEADER')")
@Controller
public class FestivalLeaderController {
	private String lmode = "startConfiguration"; //leadermode
	private String editSingleAccount = "startConfiguration";
	private String showErrors = "no";
	private final EmployeeRepository employeeRepository;
	private final MenusRepository menusRepository;
	private final UserAccountManager userAccountManager;
	private final Inventory<InventoryItem> inventory;
	private final FinanceRepository financeRepository;
	private final FestivalRepository festivalRepository;
	private Festival selected;
	private long selectedId;

	@Autowired
	public FestivalLeaderController(EmployeeRepository employeeRepository,
			MenusRepository menusRepository,
			UserAccountManager userAccountManager,
			Inventory<InventoryItem> inventory,
			FinanceRepository financeRepository, FestivalRepository festivalRepository) {

		this.employeeRepository = employeeRepository;
		this.menusRepository = menusRepository;
		this.userAccountManager = userAccountManager;
		this.inventory = inventory;
		this.financeRepository = financeRepository;
		this.festivalRepository = festivalRepository;
	}

	// ------------------------ ATTRIBUTEMAPPING ------------------------ \\
	
	/**
	 * 
	 * @return the name of the festival for the UI
	 */
	@ModelAttribute("selectedFestival")
	public String selectedFestival() {
		if (selected == null) {
			return "(noch kein Festival ausgewählt)";
		} else {
			selectedId = selected.getId();
			return selected.getFestivalName();
		}
	}
	
	/**
	 * String managermode for th:switch to decide which div to display
	 * 
	 * @return link
	 */
	@ModelAttribute("leadermode")
	public String managermode() {
		return lmode;
	}

	/**
	 * Switch to show errors in form validation
	 * 
	 * @return link
	 */
	@ModelAttribute("showErrors")
	public String showErrors() {
		return showErrors;
	}

	// ------------------------ REQUESTMAPPING ------------------------ \\

	/**
	 * Main mapping for the manager functions
	 * 
	 * @param modelMap
	 * @return link
	 */
	@RequestMapping("/leadership")
	public String index(ModelMap modelMap) {
		// Money used as sum for each type of expense
		Money catExpTot = Money.of(EUR, 0.00);
		Money catDepTot = Money.of(EUR, 0.00);

		// ------------------------ FINANCES ------------------------ \\

		// Lists that contain Finances sorted by Type
		Collection<Finance> cateringExpense = new LinkedList<Finance>();
		Collection<Finance> cateringDeposit = new LinkedList<Finance>();

		// Fill the Finance lists
		for (Finance finance : financeRepository.findAll()) {
			if (finance.getFinanceType().equals(FinanceType.CATERING)
					&& finance.getReference() == Reference.EXPENSE)
				cateringExpense.add(finance);
			if (finance.getFinanceType().equals(FinanceType.CATERING)
					&& finance.getReference() == Reference.DEPOSIT)
				cateringDeposit.add(finance);
			
		}
		
		// Schnittmenge:
		cateringExpense.retainAll(this.financeRepository.findByFestivalId(selectedId));
		cateringDeposit.retainAll(this.financeRepository.findByFestivalId(selectedId));
		
		Iterable<Finance> cateringExpenseAsAttribute = cateringExpense;
		Iterable<Finance> cateringDepositAsAttribute = cateringDeposit;
		
		// Calculate total amounts of each expense type
		

		for (Finance catDep : cateringDeposit) {
			catDepTot = catDepTot.plus(catDep.getAmount());
		}

		for (Finance catExp : cateringExpense) {
			catExpTot = catExpTot.plus(catExp.getAmount());
		}

		// Add deposit and total amounts to modelMap
		modelMap.addAttribute("catExpTot", catExpTot);
		modelMap.addAttribute("catDepTot", catDepTot);

		// Add finances by type to the modelMap
		modelMap.addAttribute("cateringExpense", cateringExpenseAsAttribute);
		modelMap.addAttribute("cateringDeposit", cateringDepositAsAttribute);

		// ------------------------ STOCK ------------------------ \\

		modelMap.addAttribute("inventory", this.inventory.findAll());
		modelMap.addAttribute("festivals", this.festivalRepository.findAll());
		return "festivalleader";
	}

	// ------------------------ ORDER MORE ------------------------ \\

	/**
	 * Check stock and order more food if necessary
	 * 
	 * @param item
	 * @param units
	 * @return link
	 */
	@RequestMapping("orderMore")
	public String orderMore(@RequestParam("itemid") InventoryItem item,
			@RequestParam("units") Long units) {
		ProductIdentifier mid = item.getProduct().getIdentifier();
		financeRepository.save(new Finance(selectedId, Reference.EXPENSE, (menusRepository
				.findByProductIdentifier(mid).getPurchasePrice()
				.multipliedBy(units)), FinanceType.CATERING));
		item.increaseQuantity(Units.of(units));
		inventory.save(item);
		
		// Menu is orderable again, because its quantity is >0
		Menu menu = menusRepository.findByProductIdentifier(item.getProduct().getId());
		menu.setOrderable(true);
		menusRepository.save(menu);

		return "redirect:/leadership";
	}

	// ------------------------ MODEMAPPING ------------------------ \\

	@RequestMapping("/leadership/festival")
	public String festival(@RequestParam("festival") long festivalId) {
		selected = festivalRepository.findById(festivalId);
		return "redirect:/leadership";
	}
	
	@RequestMapping("/Finances")
	public String finances() {
		lmode = "finances";
		showErrors = "no";
		return "redirect:/leadership";
	}

	@RequestMapping("/Terminal")
	public String terminal() {
		lmode = "terminal";
		showErrors = "no";
		return "redirect:/leadership";
	}

	@RequestMapping("/Stock")
	public String stock() {
		lmode = "checkStock";
		showErrors = "no";
		return "redirect:/leadership";
	}
}
