package catering;
//import static org.joda.money.CurrencyUnit.EUR;

import java.math.BigDecimal;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.joda.money.Money;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.quantity.Units;
import org.salespointframework.order.Cart;
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

@Controller
public class OrderController {
	
					//constructor is not a valid setter method or instance variable
	//private Order order;
	private String mode = "";
	private final Inventory<InventoryItem> inventory;
	private final DrinksRepository drinksRepository;
	private final MealsRepository mealsRepository;
	
	@Autowired
	public OrderController(MealsRepository MealsRepository, DrinksRepository DrinksRepository,
						   Inventory<InventoryItem> inventory, HttpSession session) {

		this.mealsRepository = MealsRepository;
		this.drinksRepository = DrinksRepository;
		this.inventory = inventory;
		
		Cart cart = getCart(session);
		
		//order = new Order(mealsRepository, drinksRepository);
	}
	
	// --- --- --- --- --- --- ModelAttributes --- --- --- --- --- --- \\
	
	@ModelAttribute("ordermode")
	public String ordermode() {
		return mode;
	}
	
	@ModelAttribute("cart")
	private Cart getCart(HttpSession session) {

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
		//modelMap.addAttribute("mealsRepository", order.getOrderedMeals().findAll());
		//modelMap.addAttribute("drinksRepository", order.getOrderedDrinks().findAll());
		modelMap.addAttribute("mealsRepository", this.mealsRepository.findAll());
		modelMap.addAttribute("drinksRepository", this.drinksRepository.findAll());
		return "/order";
	}
	
	/*@RequestMapping("/cancel")
	public String cancel() {
		order.cancel();
		return "redirect:/";
	}*/
	
	@RequestMapping("/drinks")
	public String drinks() {
		mode = "drinks";
		return "redirect:/";
	}
	
	@RequestMapping("/meals")
	public String meals() {
		mode = "meals";
		return "redirect:/";
	}
	
	@RequestMapping("/menu/{mid}")
	public String menu(@PathVariable("mid") int mid, Model model) {
		
		//Optional<InventoryItem> result = inventory.findByProductProductIdentifier(menu.getIdentifier());
		//Quantity quantity = item.map(InventoryItem::getQuantity).orElse(Units.ZERO);
		//inventory.count();
		/*if (result.isPresent()) {
			InventoryItem item = result.get();
			
			if (item.getQuantity().getAmount().floatValue() <= 0f) {
				return "error";
			} else {
				Quantity reduce = new Quantity(1, item.getQuantity().getMetric(), item.getQuantity().getRoundingStrategy());
				item.getQuantity().subtract(reduce);
				inventory.save(item);
			}
		} else {
			return "error";
		} */
		//model.addAttribute("menu", menu);
		//model.addAttribute("quantity", quantity);
		return "redirect:/";
	}
	
	/*@RequestMapping("/menu1")
	public String menu1() {
		Meal meal = new Meal("Pommes Frites", 2.50f);
		order.addMealToRepository(meal);
		return "redirect:/";
	}
	
	@RequestMapping("/menu2")
	public String menu2() {
		Meal meal = new Meal("Pommes Spezial", 3.50f);
		order.addMealToRepository(meal);
		return "redirect:/";
	}
	
	@RequestMapping("/menu3")
	public String menu3() {
		Meal meal = new Meal("Bratwurst", 2.00f);
		order.addMealToRepository(meal);
		return "redirect:/";
	}
	
	@RequestMapping("/menu4")
	public String menu4() {
		Meal meal = new Meal("Currywurst", 3.00f);
		order.addMealToRepository(meal);
		return "redirect:/";
	}
	
	@RequestMapping("/menu5")
	public String menu5() {
		Meal meal = new Meal("Currywurst Pommes", 4.50f);
		order.addMealToRepository(meal);
		return "redirect:/";
	}
	
	@RequestMapping("/menu6")
	public String menu6() {
		Meal meal = new Meal("Stück Pizza", 2.50f);
		order.addMealToRepository(meal);
		return "redirect:/";
	}
	
	@RequestMapping("/menu7")
	public String menu7() {
		Meal meal = new Meal("Vanilleeis", 1.00f);
		order.addMealToRepository(meal);
		return "redirect:/";
	}
	
	@RequestMapping("/menu8")
	public String menu8() {
		Meal meal = new Meal("Schokoeis", 1.00f);
		order.addMealToRepository(meal);
		return "redirect:/";
	}
	
	@RequestMapping("/menu9")
	public String menu9() {
		Drink drink = new Drink("Pils", 2.50f);
		order.addDrinkToRepository(drink);
		return "redirect:/";
	}
	
	@RequestMapping("/menu10")
	public String menu10() {
		Drink drink = new Drink("Alt", 3.00f);
		order.addDrinkToRepository(drink);
		return "redirect:/";
	}
	
	@RequestMapping("/menu11")
	public String menu11() {
		Drink drink = new Drink("Alkoholfrei", 3.00f);
		order.addDrinkToRepository(drink);
		return "redirect:/";
	}
	
	@RequestMapping("/menu12")
	public String menu12() {
		Drink drink = new Drink("Softdrink", 2.20f);
		order.addDrinkToRepository(drink);
		return "redirect:/";
	}
	
	@RequestMapping("/menu13")
	public String menu13() {
		Drink drink = new Drink("Wein", 4.70f);
		order.addDrinkToRepository(drink);
		return "redirect:/";
	}*/
	
}
