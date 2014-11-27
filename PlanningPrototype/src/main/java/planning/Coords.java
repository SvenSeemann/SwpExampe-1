package planning;

import javax.persistence.GeneratedValue;

import org.springframework.data.annotation.Id;

// Klasse schreiben PlanningDataInitializer, Adaequat zu VideoShopdataInitializer
public class Coords {
	@Id
	@GeneratedValue
	private long id;
	private int width;
	private int height;
	private int yPos;
	private int xPos;

	public Coords(int width, int height, int xPos, int yPos) {
		this.width = width;
		this.height = height;
		this.xPos = xPos;
		this.yPos = yPos;
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
}
