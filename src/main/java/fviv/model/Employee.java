package fviv.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.salespointframework.useraccount.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *@author Hendric Eckelt
*/

@Entity
public class Employee{
	private String phone;
	
	@Id
	@GeneratedValue
	private long id;
	
	@OneToOne
	private UserAccount userAccount;
	
	@Deprecated
	protected Employee(){}
	
	@Autowired
	public Employee(UserAccount userAccount, String lastname, String firstname, String email, String phone){
		this.userAccount = userAccount;
		userAccount.setLastname(lastname);
		userAccount.setFirstname(firstname);
		userAccount.setEmail(email);
		this.phone = phone;
	}
	
	public UserAccount getUserAccount(){
		return userAccount;
	}
		
	public String getPhone(){
		return phone;
	}
		
	public void setPhone(String phone){
		this.phone = phone;
	}
	
	public long getId(){
		return id;
	}
}
