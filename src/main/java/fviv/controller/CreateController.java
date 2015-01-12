package fviv.controller;

import static org.joda.money.CurrencyUnit.EUR;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

import org.joda.money.Money;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fviv.festival.FestivalRepository;
import fviv.festival.Festival;

// by niko // festivalerstellungscontroller

@Controller
@PreAuthorize("hasRole('ROLE_BOSS')")
public class CreateController {
	private final FestivalRepository festivalRepository;
	private String mode = "festival";
	private final UserAccountManager userAccountManager;
	private Festival selected;
	private LinkedList<String> managerAccounts = new LinkedList<String>();
	
	@Autowired
	public CreateController(FestivalRepository festivalRepository,
			UserAccountManager userAccountManager) {
		this.festivalRepository = festivalRepository;
		this.userAccountManager = userAccountManager;

	}

	@RequestMapping({ "/festival" })
	public String index(ModelMap modelMap) {
		mode = "festival";
		modelMap.addAttribute("festivallist", festivalRepository.findAll());
		
		managerAccounts.clear();
		for(UserAccount userAccount : userAccountManager.findAll()){
			if(userAccount.hasRole(new Role("ROLE_MANAGER")))managerAccounts.add(userAccount.getIdentifier().toString());
		}
		modelMap.addAttribute("managerAccounts", managerAccounts);

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

	@RequestMapping(value = "/newFestival", method = RequestMethod.POST)
	public String newFestival(
			@RequestParam("festivalName") String festivalName,
			@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate,
			@RequestParam("actors") String actors,
			@RequestParam("maxVisitors") int maxVisitors,
			@RequestParam("location") String location,
			@RequestParam("preisTag") long preisTag,
			@RequestParam("selectManager") String manager) throws ParseException {
		
		DateFormat format = new SimpleDateFormat("d MMMM, yyyy", Locale.GERMAN);
		Date dateStart = format.parse(startDate);
		Date dateEnd = format.parse(endDate);

		Festival festival = new Festival(dateStart, dateEnd, festivalName,
				location, actors, (int) maxVisitors, (long) preisTag, userAccountManager.findByUsername("manager").get());

		festivalRepository.save(festival);
		return "redirect:/festival";

	}

}