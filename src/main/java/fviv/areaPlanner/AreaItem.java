package fviv.areaPlanner;

import fviv.festival.Festival;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;

/**
 * 
 * @author Maximilian Schwarze
 *
 * Item of the area plan of the current festival
 *
 */

@Entity
public class AreaItem {
	
	public enum Type {
		TOILET, AREA, STAGE,BLOCKED, CATERING, CAMPING, PARKING
	}
	
	
	@javax.persistence.Id
	@GeneratedValue
	private long id;
	@ManyToOne
	private Festival festival;
	private int width;
	private int height;
	private float yPos;
	private float xPos;
	private Type type;
	private String name;
	private float factor;

	@Deprecated
	public AreaItem(){
		
	}
	@Autowired 
	public AreaItem(Type type, String name, int width, int height, float xPos, float yPos, Festival festival) {
		this.width = width;
		this.height = height;
		this.xPos = xPos;
		this.yPos = yPos;
		this.type = type;
		this.name = name;
		this.festival = festival;
	}
	
	@Autowired
	public AreaItem(Type type, String name, int width, int height, float xPos, float yPos, float factor, Festival festival) {
		this.width = width;
		this.height = height;
		this.xPos = xPos;
		this.yPos = yPos;
		this.type = type;
		this.name = name;
		this.festival = festival;
		this.setFactor(factor);
	}

	public Festival getFestival() {
		return festival;
	}

	public void setFestival(Festival festival) {
		this.festival = festival;
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

	public float getyPos() {
		return yPos;
	}

	public void setyPos(float yPos) {
		this.yPos = yPos;
	}

	public float getxPos() {
		return xPos;
	}

	public void setxPos(float xPos) {
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
	@Override
	public String toString() {
		return "[width=" + width + ", height=" + height + ", yPos="
				+ yPos + ", xPos=" + xPos + ", type=" + type + ", name=" + name
				+ "]";
	}
	
}
