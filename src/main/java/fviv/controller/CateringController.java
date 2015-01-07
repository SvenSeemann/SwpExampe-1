package fviv.controller;

import fviv.catering.model.Menu;
import fviv.catering.model.Menu.Type;
import fviv.catering.model.MenusRepository;
import fviv.model.Finance.FinanceType;
import fviv.model.Finance.Reference;
import fviv.model.FinanceRepository;
import fviv.model.Finance;

import org.salespointframework.catalog.Product;
import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.order.Cart;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
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
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;

import java.util.Optional;

/**
 * @author Niklas Fallik
 */

@Controller
@PreAuthorize("hasRole('ROLE_CATERER')")
@SessionAttributes("cart")
public class CateringController {

	private String mode = "";
	private final MenusRepository menusRepository;
	private final OrderManager<Order> orderManager;
	private final UserAccountManager userAccountManager;
	private final Inventory<InventoryItem> inventory;
	private final FinanceRepository financeRepository;

	@Autowired
	public CateringController(MenusRepository menusRepository,
			OrderManager<Order> orderManager,
			UserAccountManager userAccountManager,
			Inventory<InventoryItem> inventory,
			FinanceRepository financeRepository) {

		this.menusRepository = menusRepository;
		this.orderManager = orderManager;
		this.userAccountManager = userAccountManager;
		this.inventory = inventory;
		this.financeRepository = financeRepository;

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
			} else {
				menusRepository.findByProductIdentifier(
						item.getProduct().getId()).setOrderable(true);
			}
		}

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
	@RequestMapping("catering-menu/{mid}")
	public String addMeal(ModelMap modelMap, @PathVariable("mid") Menu menu,
			@ModelAttribute Cart cart, HttpSession session,
			@LoggedIn UserAccount userAccount) {
		// Quantity quantity =
		// inventory.findByProduct(menu).get().getQuantity();

		// should be catched by thymeleaf
		// modelMap.addAttribute("orderable",
		// quantity.isGreaterThan(Units.ZERO));

		cart.addOrUpdateItem(menu, Units.of(1));
		//TODO quantity von product in cart darf nicht größer sein als quantity von product in inventory
		/*Product product = (cart.getItem(menu.getName())).get().getProduct();
		if (inventory.findByProduct(product).get().getQuantity().isLessThan(cart.getItem(menu.getName()).get().getQuantity())) {
			menu.setOrderable(false);
			menusRepository.save(menu);
		}*/

		return "redirect:/catering";
	}

	/**
	 * Method to cancel an order. Deletes products from cart.
	 * 
	 * @param session
	 * @param cart
	 * @return link
	 */
	@RequestMapping(value = "/catering-cancel", method = RequestMethod.POST)
	public String cancel(HttpSession session, @ModelAttribute Cart cart) {
		// Cart cart = getCart(session);
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
	@RequestMapping(value = "/catering-confirm", method = RequestMethod.POST)
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

					cart.clear();

					return "redirect:/catering";
				}).orElse("redirect:/catering");
	}
}
