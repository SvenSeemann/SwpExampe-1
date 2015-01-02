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
	
	/** Get the purchase price of the {@link Menu}.
	 * 
	 * @return Purchase price of {@link Menu}
	 */
	
	public Money getPurchasePrice() {
		return this.purchasePrice;
	}
	
	/** Get the selling price of the {@link Menu}.
	 * 
	 * @return Selling price of {@link Menu}, Price of {@link Product}
	 */
	
	public Money getSellingPrice() {
		return this.sellingPrice;
	}
	
	@Deprecated
	public Menu () {};

}