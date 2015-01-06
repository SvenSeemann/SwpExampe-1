package fviv.areaPlanner;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;

@Entity
public class Objekt {
	@javax.persistence.Id
	@GeneratedValue
	long id;
	
	String name;
	int width;
	int height;
	float rentCost;

	public Objekt(){
		
	}
	
	@Autowired
	public Objekt(String name, int width, int height, float rentCost) {
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

	public float getRentCost() {
		return rentCost;
	}

	public void setRentCost(float rentCost) {
		this.rentCost = rentCost;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
