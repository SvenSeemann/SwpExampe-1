package catering;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class OrderController {
	
	@Autowired				//constructor is not a valid setter method or instance variable
	private Order order;
	private Menu menu1, menu2, menu3, menu4, menu5, menu6, menu7, menu8;
	
	//OrderController() {}
	
	public OrderController() {
		order = new Order();
		Assert.notNull(order, "Order must not be null!");
		//this.order = order;
		
		menu1 = new Menu(0, "Pommes Frites", 2.50);
		menu2 = new Menu(1, "Pommes Spezial", 3.50);
		menu3 = new Menu(2, "Bratwurst", 2.00);
		menu4 = new Menu(3, "Currywurst", 3.00);
		menu5 = new Menu(4, "Currywurst Pommes", 4.50);
		menu6 = new Menu(5, "Stück Pizza", 2.50);
		menu7 = new Menu(6, "Vanilleeis", 1.00);
		menu8 = new Menu(7, "Schokoeis", 1.00);
	}
	
	// --- --- --- --- --- --- ModelAttributes --- --- --- --- --- --- \\
	
	@ModelAttribute("output0")
	public String output0() {
		if (order.hasNoMenus()) return "There were no menus ordered."; else
		return order.getOutput(0);
	}
	
	@ModelAttribute("output1")
	public String output1() {
		return order.getOutput(1);
	}
	
	@ModelAttribute("output2")
	public String output2() {
		return order.getOutput(2);
	}
	
	@ModelAttribute("output3")
	public String output3() {
		return order.getOutput(3);
	}
	
	@ModelAttribute("output4")
	public String output4() {
		return order.getOutput(4);
	}
	
	@ModelAttribute("output5")
	public String output5() {
		return order.getOutput(5);
	}
	
	@ModelAttribute("output6")
	public String output6() {
		return order.getOutput(6);
	}
	
	@ModelAttribute("output7")
	public String output7() {
		return order.getOutput(7);
	}
	
	// --- --- --- --- --- --- RequestMapping --- --- --- --- --- --- \\
	
	@RequestMapping("/")				
	public String index() {
		return "/order";
	}
	
	@RequestMapping("/menu1")
	public String menu1(Model model) {			//@RequestParam("menu") Menu menu
		order.addMenu(menu1);
		return "redirect:/";
	}
	
	@RequestMapping("/menu2")
	public String menu2(Model model) {
		order.addMenu(menu2);
		return "redirect:/";
	}
	@RequestMapping("/menu3")
	public String menu3(Model model) {
		order.addMenu(menu3);
		return "redirect:/";
	}
	@RequestMapping("/menu4")
	public String menu4(Model model) {
		order.addMenu(menu4);
		return "redirect:/";
	}
	@RequestMapping("/menu5")
	public String menu5(Model model) {
		order.addMenu(menu5);
		return "redirect:/";
	}
	@RequestMapping("/menu6")
	public String menu6(Model model) {
		order.addMenu(menu6);
		return "redirect:/";
	}
	@RequestMapping("/menu7")
	public String menu7(Model model) {
		order.addMenu(menu7);
		return "redirect:/";
	}
	@RequestMapping("/menu8")
	public String menu8(Model model) {
		order.addMenu(menu8);
		return "redirect:/";
	}
	
}
