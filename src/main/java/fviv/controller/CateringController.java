package fviv.controller;

//import static org.joda.money.CurrencyUnit.EUR;

import fviv.catering.model.Menu;
import fviv.catering.model.Menu.Type;
import fviv.catering.model.MenusRepository;

import org.salespointframework.order.Cart;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Units;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;

import java.util.Optional;

@Controller
@PreAuthorize("hasRole('ROLE_CATERER')")
@SessionAttributes("cart")
public class CateringController {

	// constructor is not a valid setter method or instance variable
	// private Order order;
	private String mode = "";
	private final MenusRepository menusRepository;
	private final OrderManager<Order> orderManager;
	private final UserAccountManager userAccountManager;

	@Autowired
	public CateringController(MenusRepository menusRepository,
			OrderManager<Order> orderManager,
			UserAccountManager userAccountManager) {

		this.menusRepository = menusRepository;
		this.orderManager = orderManager;
		this.userAccountManager = userAccountManager;

	}

	// --- --- --- --- --- --- ModelAttributes --- --- --- --- --- --- \\

	@ModelAttribute("ordermode")
	public String ordermode() {
		return mode;
	}

	@ModelAttribute("cart")
	public Cart initializeCart(HttpSession session) {
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		return cart;
	}

	// --- --- --- --- --- --- RequestMapping --- --- --- --- --- --- \\
	@RequestMapping("/catering")
	public String catering(ModelMap modelMap) {
		modelMap.addAttribute("meals",
				this.menusRepository.findByType(Type.MEAL));
		modelMap.addAttribute("drinks",
				this.menusRepository.findByType(Type.DRINK));
		return "/catering";
	}

	@RequestMapping(value = "/catering-drinks", method = RequestMethod.POST)
	public String drinks() {
		mode = "drinks";
		return "redirect:/catering";
	}

	@RequestMapping(value = "/catering-meals", method = RequestMethod.POST)
	public String meals() {
		mode = "meals";
		return "redirect:/catering";
	}

	@RequestMapping("catering-menu/{mid}")
	public String addMeal(@PathVariable("mid") Menu menu,
			@ModelAttribute Cart cart, HttpSession session,
			@LoggedIn UserAccount userAccount) {

		cart.addOrUpdateItem(menu, Units.of(1));
		return "redirect:/catering";
	}

	@RequestMapping("/catering-cancel")
	public String cancel(HttpSession session, @ModelAttribute Cart cart) {
		//Cart cart = getCart(session);
		cart.clear();
		return "redirect:/catering";
	}
	
	@RequestMapping("/catering-confirm")
	public String confirm(@ModelAttribute Cart cart,
			@LoggedIn Optional<UserAccount> userAccount) {

		return userAccount.map(account -> {

			Order order = new Order(account, Cash.CASH);

			cart.addItemsTo(order);

			orderManager.payOrder(order);
			orderManager.completeOrder(order);
			orderManager.save(order);

			cart.clear();

			return "redirect:/";
		}).orElse("redirect:/catering");
	}
}
