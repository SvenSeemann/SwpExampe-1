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
	public static enum MenuType {
		MEAL, DRINK;
	}
	
	private long festivalId;
	private Boolean orderable;
	private MenuType type;
	private Money purchasePrice;
	private Money sellingPrice;
	
	@Autowired
	public Menu(long festivalId, String name, Money purchasePrice, Money sellingPrice, MenuType type) {
		super(name, sellingPrice, Units.METRIC);
		this.festivalId = festivalId;
		this.type = type;
		this.purchasePrice = purchasePrice;
		this.sellingPrice = sellingPrice;
		
	}
	

	@Deprecated
	public Menu () {};
	
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


	public Boolean getOrderable() {
		return orderable;
	}


	public void setOrderable(Boolean orderable) {
		this.orderable = orderable;
	}

	public long getFestivalId() {
		return festivalId;
	}

	public void setFestivalId(long festivalId) {
		this.festivalId = festivalId;
	}
	
	public MenuType getMenuType() {
		return this.type;
	}
}