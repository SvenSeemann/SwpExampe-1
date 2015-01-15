package fviv.areaPlanner;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author Maximilian Schwarze
 * 
 * provided in the FVIV Area planner to set up a new festival area
 *
 */

@Entity
public class PlanningItem {
	@javax.persistence.Id
	@GeneratedValue
	long id;
	
	String name;
	int width;
	int height;
	Money rentCost;

	public PlanningItem(){
		
	}
	
	@Autowired
	public PlanningItem(String name, int width, int height, Money rentCost) {
		//super();
		this.name = name;
		this.width = width;
		this.height = height;
		this.rentCost = rentCost;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Money getRentCost() {
		return rentCost;
	}

	public void setRentCost(Money rentCost) {
		this.rentCost = rentCost;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
