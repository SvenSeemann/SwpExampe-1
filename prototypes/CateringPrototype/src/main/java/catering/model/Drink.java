package catering.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.joda.money.Money;
import org.salespointframework.catalog.Product;
import org.salespointframework.quantity.Units;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("deprecation")
@Entity
public class Drink extends Product {
	
	@Autowired
	public Drink(String name, Money price) {
		
		super(name, price, Units.METRIC);
		
	}
	
	@Deprecated
	public Drink () {};
	
}