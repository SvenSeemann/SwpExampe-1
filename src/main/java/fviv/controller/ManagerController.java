package fviv.controller;

import static org.joda.money.CurrencyUnit.EUR;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import org.joda.money.Money;
import org.joda.time.DateTime;
import org.joda.time.Days;
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
import fviv.ticket.Ticket;
import fviv.ticket.TicketRepository;

/**
 * @author Hendric Eckelt
 * @author Niklas Fallik
 */

@PreAuthorize("hasRole('ROLE_MANAGER')")
@Controller
public class ManagerController {
	private String mode = "startConfiguration";
	private String editSingleAccount = "startConfiguration";
	private String showErrors = "no";
	private final EmployeeRepository employeeRepository;
	private final MenusRepository menusRepository;
	private final UserAccountManager userAccountManager;
	private final Inventory<InventoryItem> inventory;
	private final FinanceRepository financeRepository;
	private final FestivalRepository festivalRepository;
	private long id;
	private TicketRepository ticketRepository;

	@Autowired
	public ManagerController(EmployeeRepository employeeRepository,
			MenusRepository menusRepository,
			UserAccountManager userAccountManager,
			Inventory<InventoryItem> inventory,
			FinanceRepository financeRepository,
			FestivalRepository festivalRepository,
			TicketRepository ticketRepository) {

		this.employeeRepository = employeeRepository;
		this.menusRepository = menusRepository;
		this.userAccountManager = userAccountManager;
		this.inventory = inventory;
		this.financeRepository = financeRepository;
		this.festivalRepository = festivalRepository;
		this.ticketRepository = ticketRepository;
	}

	// ------------------------ ATTRIBUTEMAPPING ------------------------ \\

	/**
	 * String managermode for th:switch to decide which div to display
	 * 
	 * @return link
	 */
	@ModelAttribute("managermode")
	public String managermode() {
		return mode;
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

	// -------------------- REQUESTMAPPING from niko------------------ \\
	/**
	 * Model mapped the number of guest onto the website
	 * 
	 * @param modelMap
	 * @param tagesdate
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getBesucher")
	public String getBesucher(ModelMap modelMap,
			@RequestParam("hilfsDate") String tagesdate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LL-dd");
		LocalDate datum = LocalDate.parse(tagesdate, formatter);
		Festival festival = festivalRepository.findById(id);
		List<Ticket> festivalnamelist = ticketRepository
				.findByFestivalName(festival.getFestivalName());
		List<Ticket> checkedlist = ticketRepository.findByChecked(true);
		List<Ticket> datumlist = ticketRepository.findByTagesticketdate(datum);
		List<Ticket> datum2list = ticketRepository.findByTagesticketdate(null);

		Collection<Ticket> listone = new ArrayList(festivalnamelist);
		Collection<Ticket> listtwo = new ArrayList(checkedlist);
		Collection<Ticket> listthree = new ArrayList(datumlist);
		Collection<Ticket> listfourth = new ArrayList(datum2list);

		listone.retainAll(listtwo);

		listthree.addAll(listfourth);

		listone.retainAll(listthree);

		int anzahl = listone.size();

		modelMap.addAttribute("besucherzahl", anzahl);
		modelMap.addAttribute("festivallist", festivalRepository.findAll());

		return "manager";
	}

	@RequestMapping("/loadtickets")
	public String getFestivals(ModelMap modelMap,
			@RequestParam("festivalId") long festivalId) {
		Festival loadingfestival = festivalRepository.findById(festivalId);
		id = festivalId;
		LocalDate startDate = loadingfestival.getStartDatum();
		LocalDate endDate = loadingfestival.getEndDatum();
		DateTime startDatum = DateTime.parse(startDate.toString()); // hadtobedone
		DateTime endDatum = DateTime.parse(endDate.toString()); // hadtobedone
		String[] dateArray;
		int days = Days.daysBetween(startDatum, endDatum).getDays();
		dateArray = new String[days];
		LocalDate hilfsDate = startDate;

		for (int i = 0; i < days; i++) {
			dateArray[i] = hilfsDate.toString();
			hilfsDate = hilfsDate.plusDays(1);
		}
		modelMap.addAttribute("ticketdates", dateArray);

		return "manager";
	}

	// ------------------------ REQUESTMAPPING ------------------------ \\

	/**
	 * Main mapping for the manager functions
	 * 
	 * @param modelMap
	 * @return link
	 */
	@RequestMapping("/manager")
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
			if (finance.getFinanceType().equals(FinanceType.SALARY)
					&& finance.getReference() == Reference.EXPENSE)
				salaryExpense.add(finance);
			if (finance.getFinanceType().equals(FinanceType.SALARY)
					&& finance.getReference() == Reference.DEPOSIT)
				salaryDeposit.add(finance);
			if (finance.getFinanceType().equals(FinanceType.CATERING)
					&& finance.getReference() == Reference.EXPENSE)
				cateringExpense.add(finance);
			if (finance.getFinanceType().equals(FinanceType.CATERING)
					&& finance.getReference() == Reference.DEPOSIT)
				cateringDeposit.add(finance);
			if (finance.getFinanceType().equals(FinanceType.RENT)
					&& finance.getReference() == Reference.EXPENSE)
				rentExpense.add(finance);
			if (finance.getFinanceType().equals(FinanceType.RENT)
					&& finance.getReference() == Reference.DEPOSIT)
				rentDeposit.add(finance);
		}

		// Calculate total amounts of each expense type
		for (Finance salDep : salaryDeposit) {
			salDepTot = salDepTot.plus(salDep.getAmount());
		}

		for (Finance catDep : cateringDeposit) {
			catDepTot = catDepTot.plus(catDep.getAmount());
		}

		for (Finance catExp : cateringExpense) {
			catExpTot = catExpTot.plus(catExp.getAmount());
		}

		for (Finance rentDep : rentDeposit) {
			rentDepTot = rentDepTot.plus(rentDep.getAmount());
		}

		for (Finance salExp : salaryExpense) {
			salExpTot = salExpTot.plus(salExp.getAmount());
		}

		for (Finance rentExp : rentExpense) {
			rentExpTot = rentExpTot.plus(rentExp.getAmount());
		}

		// Add deposit and total amounts to modelMap
		modelMap.addAttribute("salExpTot", salExpTot);
		modelMap.addAttribute("catExpTot", catExpTot);
		modelMap.addAttribute("rentExpTot", rentExpTot);
		modelMap.addAttribute("salDepTot", salDepTot);
		modelMap.addAttribute("catDepTot", catDepTot);
		modelMap.addAttribute("rentDepTot", rentDepTot);

		// Add finances by type to the modelMap
		modelMap.addAttribute("salaryExpense", salaryExpense);
		modelMap.addAttribute("salaryDeposit", salaryDeposit);
		modelMap.addAttribute("cateringExpense", cateringExpense);
		modelMap.addAttribute("cateringDeposit", cateringDeposit);
		modelMap.addAttribute("rentExpense", rentExpense);
		modelMap.addAttribute("rentDeposit", rentDeposit);

		modelMap.addAttribute("festivallist", festivalRepository.findAll());

		// ------------------------ ROLES ------------------------ \\

		// List of available roles for the account management
		LinkedList<Role> allRoles = new LinkedList<Role>();
		allRoles.add(new Role("ROLE_BOSS"));
		allRoles.add(new Role("ROLE_MANAGER"));
		allRoles.add(new Role("ROLE_CATERER"));
		allRoles.add(new Role("ROLE_EMPLOYEE"));
		allRoles.add(new Role("MESSAGE_SENDER"));
		allRoles.add(new Role("MESSAGE_RECEIVER"));

		// Add accounts and roles to modelMap
		modelMap.addAttribute("roles", allRoles);
		modelMap.addAttribute("userAccounts", userAccountManager.findAll());
		if (userAccountManager.findByUsername(editSingleAccount).isPresent()) {
			modelMap.addAttribute("editSingleAccount", userAccountManager
					.findByUsername(editSingleAccount).get());
		}

		// Add list of all employees and an empty registration to the modelMap
		modelMap.addAttribute("employeelist", employeeRepository.findAll());
		modelMap.addAttribute("registration", new Registration());

		// ------------------------ STOCK ------------------------ \\

		modelMap.addAttribute("inventory", this.inventory.findAll());
		return "manager";
	}

	// ------------------------ NEW EMPLOYEE ------------------------ \\

	/**
	 * Mapping to add a new employee to the repository
	 * 
	 * @param registration
	 * @param results
	 * @return link
	 */
	@RequestMapping("/newEmployee")
	public String newEmployee(
			@ModelAttribute(value = "registration") @Valid Registration registration,
			BindingResult results,
			@RequestParam("departement") String departementAsString) {
		// Assumption that given input is valid
		showErrors = "no";

		// Redirect if entered registration information are not valid
		if (results.hasErrors()) {
			showErrors = "newEmployee";
			return "redirect:/manager";
		}

		Departement departement = Departement.NULL;
		if (departementAsString.equalsIgnoreCase("management")) {
			departement = Departement.MANAGEMENT;
		}
		if (departementAsString.equalsIgnoreCase("catering")) {
			departement = Departement.CATERING;
		}
		if (departementAsString.equalsIgnoreCase("security")) {
			departement = Departement.SECURITY;
		}
		if (departementAsString.equalsIgnoreCase("cleaning")) {
			departement = Departement.CLEANING;
		}

		// Create useraccount
		Role employeeRole;
		if (departement == Departement.MANAGEMENT) {
			employeeRole = new Role("ROLE_MANAGER");
		}
		if (departement == Departement.CATERING) {
			employeeRole = new Role("ROLE_CATERER");
		} else {
			employeeRole = new Role("ROLE_EMPLOYEE");
		}

		UserAccount employeeAccount = userAccountManager.create(
				registration.getFirstname() + "." + registration.getLastname(),
				registration.getPassword(), employeeRole);

		// Create employee
		Employee employee = new Employee(employeeAccount,
				registration.getLastname(), registration.getFirstname(),
				registration.getEmail(), registration.getPhone(), departement);

		// Add employee and useraccount to the specific repositories
		userAccountManager.save(employeeAccount);
		employeeRepository.save(employee);

		return "redirect:/manager";
	}

	// ------------------------ DELETE EMPLOYEE ------------------------ \\

	/**
	 * Mapping to delete an employee from the repository
	 * 
	 * @param employeeId
	 * @return link
	 */
	@RequestMapping("/deleteEmployee")
	public String deleteEmployee(@RequestParam("employeeId") long employeeId) {
		// Assumption that the given input was valid
		showErrors = "no";

		// Redirect if employee doesn't exists
		if (employeeRepository.findById(employeeId) == null) {
			showErrors = "deleteEmployee";
			return "redirect:/manager";
		}

		// Get employee, disable his account and delete him from the repository
		Employee deleteThisEmployee = employeeRepository.findById(employeeId);
		userAccountManager.disable(deleteThisEmployee.getUserAccount()
				.getIdentifier());
		userAccountManager.save(deleteThisEmployee.getUserAccount());
		employeeRepository.delete(deleteThisEmployee);

		return "redirect:/manager";
	}

	// ------------------------ ADD ROLE ------------------------ \\

	/**
	 * Mapping to add a certain role to a useraccount
	 * 
	 * @param role
	 * @return link
	 */
	@RequestMapping(params = "save", value = "/editAccount", method = RequestMethod.POST)
	public String addRole(@RequestParam("roles") String role) {
		// Assumption that given input is valid
		showErrors = "no";

		UserAccount userAccount = userAccountManager.findByUsername(
				editSingleAccount).get();

		// Define a role by the given string "role"
		final Role addRole = new Role(role);

		// Redirect if the useraccount doesn't exist or already have the role
		// attached
		if (!(userAccountManager.findByUsername(editSingleAccount).isPresent())
				|| userAccountManager.findByUsername(editSingleAccount).get()
						.hasRole(addRole)) {
			return "redirect:/manager";
		}

		// Add role to the useraccount and save it
		userAccount.add(addRole);
		userAccountManager.save(userAccount);
		return "redirect:/manager";
	}

	// ------------------------ DELETE ROLE ------------------------ \\

	/**
	 * Mapping to delete a certain role from a useraccount
	 * 
	 * @param role
	 * @return link
	 */
	@RequestMapping(params = "delete", value = "/editAccount", method = RequestMethod.POST)
	public String deleteRole(@RequestParam("roles") String role) {
		// Assumption that given input is valid
		showErrors = "no";

		// Ensure that manager, boss and caterer role cannot be deleted entirely
		if (editSingleAccount.equals("manager") && role.equals("ROLE_MANAGER")) {
			showErrors = "deleteRoleManager";
			return "redirect:/manager";
		}
		if (editSingleAccount.equals("boss") && role.equals("ROLE_BOSS")) {
			showErrors = "deleteRoleBoss";
			return "redirect:/manager";
		}
		if (editSingleAccount.equals("caterer") && role.equals("ROLE_CATERER")) {
			showErrors = "deleteRoleCaterer";
			return "redirect:/manager";
		}

		// Define a role by the given string "role"
		final Role deleteRole = new Role(role);
		UserAccount userAccount = userAccountManager.findByUsername(
				editSingleAccount).get();

		// Redirect if the useraccount doesn't exist or doesn't have the role
		// attached
		if (!(userAccountManager.findByUsername(editSingleAccount).isPresent())
				|| !(userAccountManager.findByUsername(editSingleAccount).get()
						.hasRole(deleteRole))) {
			return "redirect:/manager";
		}

		// Delete role from the useraccount and save it
		userAccount.remove(deleteRole);
		userAccountManager.save(userAccount);

		return "redirect:/manager";
	}

	// ------------------------ ACTIVATE ACCOUNT ------------------------ \\

	/**
	 * Mapping to activate a certain account
	 * 
	 * @return link
	 */
	@RequestMapping(params = "enable", value = "/editAccount", method = RequestMethod.POST)
	public String enableAccount() {
		// Assumption that given input is valid
		showErrors = "no";

		// Redirect if the useraccount doesn't exist or is already enabled
		if (!(userAccountManager.findByUsername(editSingleAccount).isPresent())
				|| userAccountManager.findByUsername(editSingleAccount).get()
						.isEnabled()) {
			return "redirect:/manager";
		}

		// Enable useraccount and save it
		userAccountManager.enable(userAccountManager
				.findByUsername(editSingleAccount).get().getIdentifier());
		userAccountManager.save(userAccountManager.findByUsername(
				editSingleAccount).get());

		return "redirect:/manager";
	}

	// ------------------------ DEACTIVATE ACCOUNT ------------------------ \\

	/**
	 * Mapping to deactivate a certain account
	 * 
	 * @return link
	 */
	@RequestMapping(params = "disable", value = "/editAccount", method = RequestMethod.POST)
	public String disableAccount() {
		// Assumption that given input is valid
		showErrors = "no";

		// Redirect if the useraccount doesn't exist or is already disabled
		if (!(userAccountManager.findByUsername(editSingleAccount).isPresent())
				|| !(userAccountManager.findByUsername(editSingleAccount).get()
						.isEnabled())) {
			return "redirect:/manager";
		}

		// Prevent to disable boss, manager and caterer
		if (editSingleAccount.equals("manager")
				|| editSingleAccount.equals("boss")
				|| editSingleAccount.equals("caterer")) {
			showErrors = "disableImportantAccount";
			return "redirect:/manager";
		}

		// Disable useraccount and save it
		userAccountManager.disable(userAccountManager
				.findByUsername(editSingleAccount).get().getIdentifier());
		userAccountManager.save(userAccountManager.findByUsername(
				editSingleAccount).get());

		return "redirect:/manager";
	}

	// ------------------------ EDIT ACCOUNT SWITCH ------------------------ \\

	/**
	 * Mapping to get the editAccount view
	 * 
	 * @param userName
	 * @param modelMap
	 * @return link
	 */
	@RequestMapping("/switchToEditAccount")
	public String editAccount(@RequestParam("userNameEdit") String userName,
			ModelMap modelMap) {
		// Assumption the given input is valid
		showErrors = "no";

		// Redirect if useraccount doesn't exist
		if (userName == ""
				|| !(userAccountManager.findByUsername(userName).isPresent())) {
			showErrors = "switchToEditAccount";
			return "redirect:/manager";
		}

		// change mode for th:switch and change string to selected account
		mode = "editAccount";
		editSingleAccount = userName;

		return "redirect:/manager";
	}

	// ------------------------ EMPLOYEE DETAILS ------------------------ \\

	/**
	 * Mapping to edit lastname, firstname or email of a useraccount
	 * 
	 * @param lastname
	 * @param firstname
	 * @param email
	 * @return link
	 */
	@RequestMapping("/editEmployeeDetails")
	public String editEmployeeDetails(
			@RequestParam("editLastname") String lastname,
			@RequestParam("editFirstname") String firstname,
			@RequestParam("editEmail") String email) {

		// Assumption that given input is valid
		showErrors = "no";

		// Update user information
		UserAccount userAccount = userAccountManager.findByUsername(
				editSingleAccount).get();
		if (lastname != "")
			userAccount.setLastname(lastname);
		if (firstname != "")
			userAccount.setFirstname(firstname);
		if (email != "")
			userAccount.setEmail(email);

		// Save account
		userAccountManager.save(userAccount);

		return "redirect:/manager";
	}

	// ------------------------ CHANGE PASSWORD ------------------------ \\

	/**
	 * Mapping to change the password of a single account
	 * 
	 * @param password1
	 * @param password2
	 * @return link
	 */
	@RequestMapping("/changePassword")
	public String changePassword(
			@RequestParam("changePassword1") String password1,
			@RequestParam("changePassword2") String password2) {
		// Assumption that given input is valid
		showErrors = "changePasswordSuccessful";

		// Password must not be empty and both passwords must match
		if (password1.equals("") || password2.equals("")) {
			showErrors = "changePasswordEmpty";
			return "redirect:/manager";
		}
		if (!(password1.equals(password2))) {
			showErrors = "changePasswordDifferentInput";
			return "redirect:/manager";
		}

		// Change the password and save the account
		userAccountManager.changePassword(
				userAccountManager.findByUsername(editSingleAccount).get(),
				password1);
		userAccountManager.save(userAccountManager.findByUsername(
				editSingleAccount).get());

		return "redirect:/manager";
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
		Menu menu = menusRepository.findByProductIdentifier(item.getProduct()
				.getId());
		menu.setOrderable(true);
		menusRepository.save(menu);

		return "redirect:/manager";
	}

	// ------------------------ MODEMAPPING ------------------------ \\

	@RequestMapping("/Employees")
	public String employees() {
		mode = "employees";
		showErrors = "no";
		return "redirect:/manager";
	}

	@RequestMapping("/Finances")
	public String finances() {
		mode = "finances";
		showErrors = "no";
		return "redirect:/manager";
	}

	@RequestMapping("/Accountmanagement")
	public String newLogin() {
		mode = "accounts";
		showErrors = "no";
		return "redirect:/manager";
	}

	@RequestMapping("/Terminal")
	public String terminal() {
		mode = "terminal";
		showErrors = "no";
		return "redirect:/manager";
	}

	@RequestMapping("/Stock")
	public String stock() {
		mode = "checkStock";
		showErrors = "no";
		return "redirect:/manager";
	}

	@RequestMapping("/Besucher")
	public String besucher() {
		mode = "checkBesucher";
		return "redirect:/manager";
	}
}
