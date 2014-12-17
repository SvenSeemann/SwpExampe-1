package fviv.areaPlanner;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;


@Entity
public class Coords {
	
	public enum Type {
		TOILET, AREA, STAGE, CATERING, CAMPING
	}
	
	
	@javax.persistence.Id
	@GeneratedValue
	private long id;
	private int width;
	private int height;
	private int yPos;
	private int xPos;
	private Type type;
	private String name;
	private float factor;
	public Coords(){
		
	}
	@Autowired 
	public Coords(String name, int width, int height, int xPos, int yPos, Type type) {
		this.width = width;
		this.height = height;
		this.xPos = xPos;
		this.yPos = yPos;
		this.type = type;
		this.name = name;
	}
	
	@Autowired
	public Coords(String name, int width, int height, int xPos, int yPos, Type type, float factor) {
		this.width = width;
		this.height = height;
		this.xPos = xPos;
		this.yPos = yPos;
		this.type = type;
		this.name = name;
		this.setFactor(factor);
	}
	public long getId(){
		return id;
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

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}
	
	public Type getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	public float getFactor() {
		return factor;
	}
	public void setFactor(float factor) {
		this.factor = factor;
	}
}
