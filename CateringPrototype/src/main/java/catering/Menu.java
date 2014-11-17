package catering;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestParam;

public class Menu {

	private Integer id;
	private String description;
	private double price;
	
	public Menu(Integer id, String description, double price) {
		
		Assert.notNull(id, "Order must not be null!");
		this.id = id;
		this.description = description;
		this.price = price;
		
	}
	
	Menu () {};
	
	public String getDescription() {
		return this.description;
	}
	
	public double getPrice() {
		return this.price;
	}
	
}
