package fviv.controller;

import static org.joda.money.CurrencyUnit.EUR;

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

	@Autowired
	public FestivalLeaderController(EmployeeRepository employeeRepository,
			MenusRepository menusRepository,
			UserAccountManager userAccountManager,
			Inventory<InventoryItem> inventory,
			FinanceRepository financeRepository) {

		this.employeeRepository = employeeRepository;
		this.menusRepository = menusRepository;
		this.userAccountManager = userAccountManager;
		this.inventory = inventory;
		this.financeRepository = financeRepository;
	}

	// ------------------------ ATTRIBUTEMAPPING ------------------------ \\

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
		Money salExpTot = Money.of(EUR, 0.00), catExpTot = Money.of(EUR, 0.00), rentExpTot = Money
				.of(EUR, 0.00);
		Money salDepTot = Money.of(EUR, 0.00), catDepTot = Money.of(EUR, 0.00), rentDepTot = Money
				.of(EUR, 0.00);

		// ------------------------ FINANCES ------------------------ \\

		// Lists that contain Finances sorted by Type
		LinkedList<Finance> salaryExpense = new LinkedList<Finance>();
		LinkedList<Finance> salaryDeposit = new LinkedList<Finance>();
		LinkedList<Finance> cateringExpense = new LinkedList<Finance>();
		LinkedList<Finance> cateringDeposit = new LinkedList<Finance>();
		LinkedList<Finance> rentExpense = new LinkedList<Finance>();
		LinkedList<Finance> rentDeposit = new LinkedList<Finance>();

		// Fill the Finance lists
		for (Finance finance : financeRepository.findAll()) {
			if (finance.getFinanceType().equals(FinanceType.CATERING)
					&& finance.getReference() == Reference.EXPENSE)
				cateringExpense.add(finance);
			if (finance.getFinanceType().equals(FinanceType.CATERING)
					&& finance.getReference() == Reference.DEPOSIT)
				cateringDeposit.add(finance);
		}
		
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
		modelMap.addAttribute("cateringExpense", cateringExpense);
		modelMap.addAttribute("cateringDeposit", cateringDeposit);

		// ------------------------ STOCK ------------------------ \\

		modelMap.addAttribute("inventory", this.inventory.findAll());
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
		financeRepository.save(new Finance(Reference.EXPENSE, (menusRepository
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
