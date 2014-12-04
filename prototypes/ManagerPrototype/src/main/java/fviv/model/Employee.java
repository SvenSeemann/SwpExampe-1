package fviv.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class Employee{
	private String lastname, firstname, email, phone;
		
	@Id
	@GeneratedValue
	private long id;
	
	@Deprecated
	protected Employee(){}
	
	@Autowired
	public Employee(String lastname, String firstname, String email, String phone){
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
	
	public void setLastname(String lastname){
		this.lastname = lastname;
	}
	
	public void setFirstname(String firstname){
		this.firstname = firstname;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public void setPhone(String phone){
		this.phone = phone;
	}
	
	public void setId(long id){
		this.id = id;
	}
}
