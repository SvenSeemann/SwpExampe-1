package fviv.catering.model;

import javax.persistence.Entity;

import org.joda.money.Money;
import org.salespointframework.catalog.Product;
import org.salespointframework.quantity.Units;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("serial")
@Entity
public class Menu extends Product {
	public static enum Type {
		MEAL, DRINK;
	}
	
	@SuppressWarnings("unused")
	private Type type;
	
	@Autowired
	public Menu(String name, Money price, Type type) {
		
		super(name, price, Units.METRIC);
		this.type = type;
		
	}
	
	@Deprecated
	public Menu () {};

}