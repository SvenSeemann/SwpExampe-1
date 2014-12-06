package catering.controller;
//import static org.joda.money.CurrencyUnit.EUR;

import java.math.BigDecimal;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.joda.money.Money;
import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.quantity.Units;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.salespointframework.useraccount.web.LoggedIn;
import org.salespointframework.order.Cart;
//import org.salespointframework.order.Basket;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderLine;
import org.salespointframework.order.OrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import catering.model.Drink;
import catering.model.DrinksRepository;
import catering.model.Menu;
import catering.model.Menu.Type;
import catering.model.MenusRepository;




@Controller
@PreAuthorize("hasRole('ROLE_CATERER')")
@SessionAttributes("cart")
public class OrderController {
	
					//constructor is not a valid setter method or instance variable
	//private Order order;
	private String mode = "";
	private final Inventory<InventoryItem> inventory;
	private final MenusRepository menusRepository;
	private final OrderManager<Order> orderManager;
	private final UserAccountManager userAccountManager;
	private Order order;
	
	@Autowired
	public OrderController(MenusRepository menusRepository, Inventory<InventoryItem> inventory,
																OrderManager<Order> orderManager,
																UserAccountManager userAccountManager) {

		this.menusRepository = menusRepository;
		this.inventory = inventory;
		this.orderManager = orderManager;
		this.userAccountManager = userAccountManager;
		
	}
	
	// --- --- --- --- --- --- ModelAttributes --- --- --- --- --- --- \\
	
	@ModelAttribute("ordermode")
	public String ordermode() {
		return mode;
	}
	
	@ModelAttribute("cart")
	public Cart getCart(HttpSession session) {
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		return cart;
	}
	
	
	// --- --- --- --- --- --- RequestMapping --- --- --- --- --- --- \\
	
	@RequestMapping({"/", "/index", "/order"})				
	public String index(ModelMap modelMap) {
		modelMap.addAttribute("meals", this.menusRepository.findByType(Type.MEAL)); //TODO replace "...Repository" in html
		modelMap.addAttribute("drinks", this.menusRepository.findByType(Type.DRINK));
		return "/order";
	}
	
	@RequestMapping("/drinks")
	public String drinks() {
		mode = "drinks";
		return "redirect:/";
	}
	
	@RequestMapping("/cancel")
	public String cancel(HttpSession session) {
		Cart cart = getCart(session);
		cart.clear();
		return "redirect:/";
	}
	
	@RequestMapping("/meals")
	public String meals() {
		mode = "meals";
		return "redirect:/";
	}
	
	@RequestMapping("/menu/{mid}")
	public String addMeal(@PathVariable("mid") Menu menu, @ModelAttribute Cart cart, HttpSession session, @LoggedIn UserAccount userAccount) {
		
		cart.addOrUpdateItem(menu, Units.of(1));
		
		return "redirect:/";
	}
	
	/*@RequestMapping("/drink/{did}")
	public String addDrink(@PathVariable("did") Drink drink, @ModelAttribute Cart cart, HttpSession session, @LoggedIn UserAccount userAccount) {
		
		cart.addOrUpdateItem(drink, Units.of(1));
		
		return "redirect:/";
	}*/
	
	@RequestMapping("confirm")
	public String confirm(@ModelAttribute Cart cart, @LoggedIn Optional<UserAccount> userAccount) {

				return userAccount.map(account -> {
					
						Order order = new Order(account, Cash.CASH);

						cart.addItemsTo(order);

						orderManager.payOrder(order);
						orderManager.completeOrder(order);
						orderManager.save(order);

						cart.clear();

						return "redirect:/";
					}).orElse("redirect:/order");
	}
}
