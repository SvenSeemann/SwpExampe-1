package fviv.controller;

import fviv.catering.model.Menu;
import fviv.catering.model.Menu.Type;
import fviv.catering.model.MenusRepository;
import fviv.festival.Festival;
import fviv.festival.FestivalRepository;
import fviv.model.Finance.FinanceType;
import fviv.model.Finance.Reference;
import fviv.model.FinanceRepository;
import fviv.model.Finance;

import org.salespointframework.catalog.Product;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.Cart;
import org.salespointframework.order.CartItem;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Units;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

/**
 * @author Niklas Fallik
 */

@Controller
@PreAuthorize("hasRole('ROLE_CATERER')")
@SessionAttributes("cart")
public class CateringController {

	private String mode = "";
	private LinkedList<CartItem> currentCartItems = new LinkedList<CartItem>();
	private final MenusRepository menusRepository;
	private final OrderManager<Order> orderManager;
	private final Inventory<InventoryItem> inventory;
	private final FinanceRepository financeRepository;
	private final FestivalRepository festivalRepository;
	private long selected;

	@Autowired
	public CateringController(MenusRepository menusRepository,
			OrderManager<Order> orderManager,
			Inventory<InventoryItem> inventory,
			FinanceRepository financeRepository,
			FestivalRepository festivalRepository) {

		this.menusRepository = menusRepository;
		this.orderManager = orderManager;
		this.inventory = inventory;
		this.financeRepository = financeRepository;
		this.festivalRepository = festivalRepository;

	}

	// --- --- --- --- --- --- ModelAttributes --- --- --- --- --- --- \\

	/**
	 * Sets the order mode to MEALS or DRINKS in the UI
	 * 
	 * @return mode
	 */
	@ModelAttribute("ordermode")
	public String ordermode() {
		return mode;
	}

	/**
	 * Gets the cart attribute from current session
	 * 
	 * @param session
	 * @return cart
	 */
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

	/**
	 * Main mapping for catering functions. Adds the attributes from the menus
	 * repository.
	 * 
	 * @param modelMap
	 * @return link
	 */
	@RequestMapping("/catering")
	public String catering(ModelMap modelMap) {
		for (InventoryItem item : inventory.findAll()) {
			if (item.getQuantity().equals(Units.ZERO)) {
				menusRepository.findByProductIdentifier(
						item.getProduct().getId()).setOrderable(false);
			}
		}
		modelMap.addAttribute("festivals", festivalRepository.findAll());
		Collection<Menu> meals = this.menusRepository.findByType(Type.MEAL);
		Collection<Menu> drinks = this.menusRepository.findByType(Type.DRINK);

		// Schnittmenge:
		meals.retainAll(this.menusRepository.findByFestivalId(selected));
		drinks.retainAll(this.menusRepository.findByFestivalId(selected));

		Iterable<Menu> mealsAsAttribute = meals;
		Iterable<Menu> drinksAsAttribute = drinks;

		modelMap.addAttribute("meals", mealsAsAttribute);
		modelMap.addAttribute("drinks", drinksAsAttribute);

		return "/catering";
	}

	@RequestMapping(value = "/catering/drinks", method = RequestMethod.POST)
	public String drinks() {
		mode = "drinks";
		return "redirect:/catering";
	}

	@RequestMapping(value = "/catering/meals", method = RequestMethod.POST)
	public String meals() {
		mode = "meals";
		return "redirect:/catering";
	}

	@RequestMapping(value = "/catering/choosefestival", method = RequestMethod.POST)
	public String choosefestival() {
		mode = "festival";
		return "redirect:/catering";
	}

	@RequestMapping(value = "/catering/festival", method = RequestMethod.POST)
	public String festival(@RequestParam("festival") long festivalId) {
		selected = festivalId;
		return "redirect:/catering";
	}

	/**
	 * Get the ordered meal and update the current order
	 * 
	 * @param modelMap
	 * @param menu
	 * @param cart
	 * @param session
	 * @param userAccount
	 * @return link
	 */
	@RequestMapping("catering/menu/{mid}")
	public String addMeal(ModelMap modelMap, @PathVariable("mid") Menu menu,
			@ModelAttribute Cart cart, HttpSession session,
			@LoggedIn UserAccount userAccount) {
		// Quantity quantity =
		// inventory.findByProduct(menu).get().getQuantity();

		// should be catched by thymeleaf
		// modelMap.addAttribute("orderable",
		// quantity.isGreaterThan(Units.ZERO));

		CartItem cartItem = cart.addOrUpdateItem(menu, Units.of(1));
		// List to keep track of the items in the cart
		// Because there is no option to get all items in the cart?
		if (!(currentCartItems.contains(cartItem)))
			currentCartItems.add(cartItem);

		// quantity von product in cart darf nicht größer sein als quantity von
		// product in inventory

		Product product = cartItem.getProduct();
		if (cartItem
				.getQuantity()
				.getAmount()
				.equals(inventory.findByProduct(product).get().getQuantity()
						.getAmount())) {
			menu.setOrderable(false);
			menusRepository.save(menu);
		}
		return "redirect:/catering";
	}

	/**
	 * Method to cancel an order. Deletes products from cart.
	 * 
	 * @param session
	 * @param cart
	 * @return link
	 */
	@RequestMapping(value = "/catering/cancel", method = RequestMethod.POST)
	public String cancel(HttpSession session, @ModelAttribute Cart cart) {
		// Cart cart = getCart(session);

		// Revert menus to orderable in case of cancel
		for (CartItem cartItem : currentCartItems) {
			Menu menu = menusRepository.findByProductIdentifier(cartItem
					.getProduct().getId());
			menu.setOrderable(true);
			menusRepository.save(menu);
		}

		currentCartItems.clear();
		cart.clear();
		return "redirect:/catering";
	}

	/**
	 * Method to confirm the currend order. Updates the FinanceRepository.
	 * 
	 * @param cart
	 * @param userAccount
	 * @return link
	 */
	@RequestMapping(value = "/catering/confirm", method = RequestMethod.POST)
	public String confirm(@ModelAttribute Cart cart,
			@LoggedIn Optional<UserAccount> userAccount) {

		return userAccount.map(
				account -> {
					Order order = new Order(account, Cash.CASH);

					cart.addItemsTo(order);
					orderManager.payOrder(order);
					orderManager.completeOrder(order);
					orderManager.save(order);
					financeRepository.save(new Finance(Reference.DEPOSIT, order
							.getTotalPrice(), FinanceType.CATERING));

					currentCartItems.clear();
					cart.clear();

					return "redirect:/catering";
				}).orElse("redirect:/catering");
	}
}
