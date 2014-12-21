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
	private Money purchasePrice;
	
	@Autowired
	public Menu(String name, Money purchasePrice, Money sellingPrice, Type type) {
		
		super(name, sellingPrice, Units.METRIC);
		this.type = type;
		this.purchasePrice = purchasePrice;
		
	}
	
	public Money getPurchasePrice() {
		return this.purchasePrice;
	}
	
	@Deprecated
	public Menu () {};

}