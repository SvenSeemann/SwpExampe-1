package catering;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestParam;

public class Menu {

	private int id;
	private String description;
	private double price;
	
	public Menu(Integer id, String description, double price) {
		
		Assert.notNull(id, "Order must not be null!");
		this.id = id;
		this.description = description;
		this.price = price;
		
	}
	
	Menu () {};
	
	public Integer getId() {
		return this.id;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public String toString() {
		return " | " + this.description + " | " + this.price;
	}
	
}
