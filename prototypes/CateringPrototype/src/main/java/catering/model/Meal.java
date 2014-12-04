package catering.model;

import javax.persistence.Entity;

import org.joda.money.Money;
import org.salespointframework.catalog.Product;
import org.salespointframework.quantity.Units;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("deprecation")
@Entity
public class Meal extends Product {
	
	@Autowired
	public Meal(String name, Money price) {
		
		super(name, price, Units.METRIC);
		
	}
	
	@Deprecated
	public Meal () {};

}