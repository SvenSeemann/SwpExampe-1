package fviv.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.salespointframework.useraccount.UserAccount;

@Entity
public class Employee extends Person{
	@Id
	@GeneratedValue
	private long id;

	public Employee(String lastname, String firstname, String email, String phone){
		super(lastname, firstname, email, phone);
	}
	
	public long getId(){
		return id;
	}
}
