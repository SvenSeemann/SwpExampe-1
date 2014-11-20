package fviv;

public class Manager {
	private String name;
	private int id;
	
	public Manager(String name, int id){
		this.id = id;
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public int getId(){
		return id;
	}
}
