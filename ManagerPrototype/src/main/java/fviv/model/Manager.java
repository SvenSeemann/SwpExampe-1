package fviv.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Manager extends Person{
	@Id
	@GeneratedValue
	private long id;
	
	public Manager(String lastname, String firstname, String email, String phone){
		super(lastname, firstname, email, phone);
	}
	
	public long getId(){
		return id;
	}
}
