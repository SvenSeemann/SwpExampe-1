package fviv.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.salespointframework.useraccount.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;

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
	public Employee(UserAccount userAccount, String phone){
		this.userAccount = userAccount;
		this.phone = phone;
	}
	
	public UserAccount getUserAccount(){
		return userAccount;
	}
	
	public String getLastname(){
		return userAccount.getLastname();
	}
	
	public String getFirstname(){
		return userAccount.getFirstname();
	}
	
	public String getEmail(){
		return userAccount.getEmail();
	}
	
	public String getPhone(){
		return phone;
	}
	
	public void setLastname(String lastname){
		userAccount.setLastname(lastname);
	}
	
	public void setFirstname(String firstname){
		userAccount.setFirstname(firstname);
	}
	
	public void setEmail(String email){
		userAccount.setEmail(email);
	}
	
	public void setPhone(String phone){
		this.phone = phone;
	}
}
