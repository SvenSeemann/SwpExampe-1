package catering;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.joda.money.Money;
import org.salespointframework.catalog.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestParam;

@Entity
public abstract class Menu extends Product {

	
	protected String description;
	
	@SuppressWarnings("deprecation")
	public Menu(String description, Money price) {
		this.description = description;
	}
	
	@Deprecated
	public Menu () {};
	
	public String getDescription() {
		return this.description;
	}
	
}
