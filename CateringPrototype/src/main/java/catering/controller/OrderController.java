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
import org.salespointframework.order.Basket;
import org.salespointframework.order.Cart;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderLine;
import org.salespointframework.order.OrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import catering.model.DrinksRepository;
import catering.model.MealsRepository;

//changed SalesPoint class "cart" to SalesPoint class "basket"


@Controller
public class OrderController {
	
					//constructor is not a valid setter method or instance variable
	//private Order order;
	private String mode = "";
	private final Inventory<InventoryItem> inventory;
	private final DrinksRepository drinksRepository;
	private final MealsRepository mealsRepository;
	private final OrderManager<Order> orderManager;
	private UserAccount account;
	
	@Autowired
	public OrderController(MealsRepository MealsRepository, DrinksRepository DrinksRepository,
						   Inventory<InventoryItem> inventory, OrderManager<Order> orderManager) {

		this.mealsRepository = MealsRepository;
		this.drinksRepository = DrinksRepository;
		this.inventory = inventory;
		this.orderManager = orderManager;
	}
	
	// --- --- --- --- --- --- ModelAttributes --- --- --- --- --- --- \\
	
	@ModelAttribute("ordermode")
	public String ordermode() {
		return mode;
	}
	
	@ModelAttribute("basket")
	private Basket getBasket(HttpSession session) {
		Basket basket = (Basket) session.getAttribute("basket");
		if (basket == null) {
			basket = new Basket();
			session.setAttribute("cart", basket);
		}
		return basket;
	}
	
	// --- --- --- --- --- --- RequestMapping --- --- --- --- --- --- \\
	
	@RequestMapping({"/", "/index", "/order"})				
	public String index(ModelMap modelMap) {
		modelMap.addAttribute("mealsRepository", this.mealsRepository.findAll());
		modelMap.addAttribute("drinksRepository", this.drinksRepository.findAll());
		return "/order";
	}
	
	@RequestMapping("/drinks")
	public String drinks() {
		mode = "drinks";
		return "redirect:/";
	}
	
	@RequestMapping("/cancel")
	public String cancel(HttpSession session) {
		Basket basket = getBasket(session);
		basket.clear();
		return "redirect:/";
	}
	
	@RequestMapping("/meals")
	public String meals() {
		mode = "meals";
		return "redirect:/";
	}
	
	@RequestMapping("/menu/{mid}")
	public String menu(@PathVariable("mid") ProductIdentifier mealId, Model model, HttpSession session) {
		
		Order order = new Order(account, Cash.CASH);
		Quantity quantity = Units.of(1);
		OrderLine orderLine = new OrderLine(mealsRepository.findByProductIdentifier(mealId), quantity);

		Basket basket = getBasket(session);
		basket.add(orderLine);
		
		System.out.println(orderLine.getProductName());
		return "redirect:/";
	}
	
	@RequestMapping("confirm")
	public String confirm(Model model, HttpSession session) {

				Order order = new Order(account, Cash.CASH);
				Basket basket = getBasket(session);
				basket.commit(order);

				orderManager.payOrder(order);
				orderManager.completeOrder(order);
				orderManager.add(order);

				basket.clear();

				return "redirect:/";
	}
}
