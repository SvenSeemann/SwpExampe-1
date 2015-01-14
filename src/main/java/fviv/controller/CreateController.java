package fviv.controller;

import static org.joda.money.CurrencyUnit.EUR;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.LinkedList;

import org.joda.money.Money;
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
	private UserAccountManager userAccountManager;
	private LinkedList<String> managerAccounts = new LinkedList<String>();
	private AreaItemsRepository areaItems;
	private FinanceRepository financeRepository;

	@Autowired
	public CreateController(FestivalRepository festivalRepository,
			LocationRepository locationrepository,
			UserAccountManager userAccountManager,
			FinanceRepository financeRepository,
			AreaItemsRepository areaItems) {
		this.festivalRepository = festivalRepository;
		this.locationRepository = locationrepository;
		this.userAccountManager = userAccountManager;
		this.financeRepository = financeRepository;
		this.areaItems = areaItems;
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

		managerAccounts.clear();
		for (UserAccount userAccount : userAccountManager.findAll()) {
			if (userAccount.hasRole(new Role("ROLE_MANAGER")))
				managerAccounts.add(userAccount.getIdentifier().toString());
		}
		modelMap.addAttribute("managerAccounts", managerAccounts);
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
	public String setNewQuant(
			@RequestParam("quantManagement") int quantManagement,
			@RequestParam("quantCatering") int quantCatering,
			@RequestParam("quantSecurity") int quantSecurity,
			@RequestParam("quantCleaning") int quantCleaning) {

		if (quantManagement != 0)
			selected.setQuantManagement(quantManagement);
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
			@RequestParam("selectManager") String manager,
			@RequestParam("locationId") long locationId) throws ParseException {

		

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LL-dd");

		LocalDate dateStart = LocalDate.parse(startDate, formatter);
		LocalDate dateEnd = LocalDate.parse(endDate, formatter);

		Festival festival = new Festival(dateStart, dateEnd, festivalName,

		locationId, actors, (int) locationRepository.findById(locationId)
				.getMaxVisitors(), Money.of(EUR, Long.parseLong(preisTag)),
				manager);

		long festivalId = festivalRepository.save(festival).getId();
		
		this.areaItems.save(new AreaItem(Type.AREA, "Areal", locationRepository
				.findById(locationId).getWidth(), locationRepository
				.findById(locationId).getHeight(), 0, 0, festivalRepository.findById(festivalId)));

		return "redirect:/festival";
	}
}