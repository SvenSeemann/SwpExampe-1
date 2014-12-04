package fviv.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Manager{
	private String lastname, firstname, email, phone;
		
	@Id
	@GeneratedValue
	private long id;
	
	@Deprecated
	protected Manager(){}
	
	public Manager(String lastname, String firstname, String email, String phone){
		this.lastname = lastname;
		this.firstname = firstname;
		this.email = email;
		this.phone = phone;
	}
	
	public long getId(){
		return id;
	}
	
	public String getLastname(){
		return lastname;
	}
	
	public String getFirstname(){
		return firstname;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getPhone(){
		return phone;
	}
}
