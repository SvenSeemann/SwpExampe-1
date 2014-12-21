package fviv.catering.model;

import javax.persistence.Entity;

import org.joda.money.Money;
import org.salespointframework.catalog.Product;
import org.salespointframework.quantity.Units;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *@author Niklas Fallik
*/

@SuppressWarnings("serial")
@Entity
public class Menu extends Product {
	public static enum Type {
		MEAL, DRINK;
	}
	
	@SuppressWarnings("unused")
	private Type type;
	private Money purchasePrice;
	private Money sellingPrice;
	
	@Autowired
	public Menu(String name, Money purchasePrice, Money sellingPrice, Type type) {
		
		super(name, sellingPrice, Units.METRIC);
		this.type = type;
		this.purchasePrice = purchasePrice;
		this.sellingPrice = sellingPrice;
		
	}
	
	public Money getPurchasePrice() {
		return this.purchasePrice;
	}
	
	public Money getSellingPrice() {
		return this.sellingPrice;
	}
	
	@Deprecated
	public Menu () {};

}