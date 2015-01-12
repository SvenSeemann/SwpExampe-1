package fviv.controller;

import java.util.LinkedList;
import java.util.List;

import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fviv.areaPlanner.AreaItem;
import fviv.areaPlanner.AreaItemsRepository;
import fviv.festival.FestivalRepository;

@PreAuthorize("hasRole('ROLE_GUEST')")
@Controller
public class TerminalController {
	private AreaItemsRepository areaItems;
	private FestivalRepository festivalRepository;
	
	@Autowired
	public TerminalController(AreaItemsRepository areaItems, FestivalRepository festivalRepository) {
		this.areaItems = areaItems;
		this.festivalRepository = festivalRepository;
	}

	@RequestMapping("/terminal")
	public String giveMeATermial(Model modelMap, @LoggedIn UserAccount userAccount) {
		long festivalId = festivalRepository.findByUserAccount(userAccount).getId();
		modelMap.addAttribute("irgendwas", areaItems.findByFestivalId(festivalId));
		return "terminal";
	}
}
