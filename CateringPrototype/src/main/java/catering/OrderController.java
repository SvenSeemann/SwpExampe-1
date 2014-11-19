package catering;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class OrderController {
	
	@Autowired				//constructor is not a valid setter method or instance variable
	private Order order;
	private Menu menu1;
	
	//OrderController() {}
	
	public OrderController() {
		order = new Order();
		Assert.notNull(order, "Order must not be null!");
		//this.order = order;
		
		menu1 = new Menu(100, "Pommes", 2.50);
	}
	
	//Menu menu1 = new Menu(100, "Pommes", 2.50);
	
	
	@RequestMapping("/")				
	public String index() {
		return "/order";
	}
	
	@RequestMapping("/menu1")
	public String menu1(Model model) {			//@RequestParam("menu") Menu menu
		//model.addAttribute("order", order.addMenu(menu1));
		order.addMenu(menu1);
		return "redirect:/";
	}
	
}
