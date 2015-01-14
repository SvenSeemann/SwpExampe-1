package fviv.controller;

import static org.joda.money.CurrencyUnit.EUR;

import java.text.ParseException;
import java.util.LinkedList;

import org.joda.money.Money;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import java.time.LocalDate;
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

	@Autowired
	public CreateController(FestivalRepository festivalRepository, LocationRepository locationrepository,UserAccountManager userAccountManager) {
		this.festivalRepository = festivalRepository;
		this.locationRepository = locationrepository;
		this.userAccountManager = userAccountManager;


	}
/**
 * index method and Modelmapping of the festivallist and locationlist
 * @param modelMap
 * @return
 */
	@RequestMapping({ "/festival" })
	public String index(ModelMap modelMap) {
		//mode = "festival";
		modelMap.addAttribute("festivallist", festivalRepository.findAll());

		managerAccounts.clear();
		for(UserAccount userAccount : userAccountManager.findAll()){
			if(userAccount.hasRole(new Role("ROLE_MANAGER")))managerAccounts.add(userAccount.getIdentifier().toString());
		}
		modelMap.addAttribute("managerAccounts", managerAccounts);
		modelMap.addAttribute("locationlist", locationRepository.findAll());
		return "festival";
	}

	@RequestMapping({ "/showFestival" })
	public String showFestival() {
		mode = "showFestivals";

		return "redirect:/festival";
	}

	@ModelAttribute("selected")
	public Festival festival() {
		return selected;
	}

	@RequestMapping("/setQuantityOfEmployees")
	public String quantityEmployees(@RequestParam("festivalId") long festivalId) {
		mode = "setQuantity";
		selected = festivalRepository.findById(festivalId);
		return "redirect:/festival";
	}
	
	@RequestMapping("/setSalary")
	public String setSalary(@RequestParam("festivalId") long festivalId) {
		mode = "setSalary";
		System.out.println(festivalRepository.findById(festivalId)
				.getFestivalName());
		selected = festivalRepository.findById(festivalId);
		return "redirect:/festival";
	}

	@RequestMapping(value = "/setup-employees", method = RequestMethod.POST)
	public String setUpEmployees() {
		mode = "setup-employees";
		return "redirect:/festival";
	}
	
	@RequestMapping(value = "/festival/create", method = RequestMethod.POST)
	public String createFestival() {
		mode = "festival";
		return "redirect:/festival";
	}
	
	@RequestMapping(value = "/festival/areaplan", method = RequestMethod.POST)
	public String areaplan() {
		mode = "areaplan";
		return "redirect:/festival";
	}
	
	@RequestMapping(value = "/setup/area", method = RequestMethod.POST)
	public String setUpArea(@RequestParam("festivalId") long festivalId) {
		//hier auf max planner zugreifen
		this.selected = festivalRepository.findById(festivalId);
		
		
		
		// TODO this.selected.setArea(afds);
		festivalRepository.save(selected);
		return "redirect:/planning/" + selected.getId();
	}
	
	public String setUpAreaSave() {
		//hier auf max planner zugreifen
		//this.selected.setArea(areaItems);
		return "redirect:/festival";
	}

	@RequestMapping("/setNewSalary")
	public String setNewSalary(
			@RequestParam("salManagement") long salManagement,
			@RequestParam("salCatering") long salCatering,
			@RequestParam("salSecurity") long salSecurity,
			@RequestParam("salCleaning") long salCleaning) {
		selected.setManagementSalaryPerDay(Money.of(EUR, salManagement));
		selected.setCateringSalaryPerDay(Money.of(EUR, salCatering));
		selected.setSecuritySalaryPerDay(Money.of(EUR, salSecurity));
		selected.setCleaningSalaryPerDay(Money.of(EUR, salCleaning));
		festivalRepository.save(selected);
		return "redirect:/festival";
	}
	
	@RequestMapping("/setNewQuant")
	public String setNewQuant(
			@RequestParam("quantManagement") int quantManagement,
			@RequestParam("quantCatering") int quantCatering,
			@RequestParam("quantSecurity") int quantSecurity,
			@RequestParam("quantCleaning") int quantCleaning) {
		selected.setQuantManagement(quantManagement);
		selected.setQuantCatering(quantCatering);
		selected.setQuantSecurity(quantSecurity);
		selected.setQuantCleaning(quantCleaning);
		festivalRepository.save(selected);
		return "redirect:/festival";
	}

	@ModelAttribute("festivalmode")
	public String festivalmode() {
		return mode;
	}

	/**
	 * Creating Festival from the inputs of the site
	 * @param festivalName
	 * @param startDate
	 * @param endDate
	 * @param actors
	 * @param maxVisitors
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
			@RequestParam("preisTag") long preisTag,
			@RequestParam("selectManager") String manager,
			@RequestParam("locationId") long locationId) throws ParseException {


		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LL-dd");
		
		LocalDate dateStart = LocalDate.parse(startDate, formatter);
		LocalDate dateEnd = LocalDate.parse(endDate, formatter);


		Festival festival = new Festival(dateStart, dateEnd, festivalName,
				locationId, actors, (int) locationRepository.findById(locationId).getMaxVisitors(), (long) preisTag, manager);
	
		festivalRepository.save(festival);
				
		return "redirect:/festival";

	}

}