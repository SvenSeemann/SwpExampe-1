package catering;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestParam;

@Entity
public abstract class Menu {

	@Id
	@GeneratedValue
	private long id;
	protected String description;
	protected float price;
	
	@Autowired
	public Menu(String description, float price) {
		this.description = description;
		this.price = price;
	}
	
	@Deprecated
	public Menu () {};
	
	public long getId() {
		return this.id;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public double getPrice() {
		return this.price;
	}
	
}
