package catering;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestParam;

@Component
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
	
	@Bean
	public String getDescription() {
		return this.description;
	}
	
	@Bean
	public double getPrice() {
		return this.price;
	}
	
}
