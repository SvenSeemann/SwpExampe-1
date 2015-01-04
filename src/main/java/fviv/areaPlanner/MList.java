package fviv.areaPlanner;

import fviv.areaPlanner.Coords.Type;

public class MList {

	private String name;
	private Type type;
	private float xpos;
	private float ypos;
	private float factor;
	private int width;
	private int height;
	
	public MList(Type type, int width, int height, float factor) {
		super();
		this.type = type;
		this.width = width;
		this.height = height;
		this.factor = factor;
	}
	
	public MList(Type type, String name, int width, int height, float xpos,
			float ypos) {
		super();
		this.name = name;
		this.type = type;
		this.xpos = xpos;
		this.ypos = ypos;
		this.width = width;
		this.height = height;
	}
}
