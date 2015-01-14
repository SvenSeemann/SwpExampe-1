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
	public static enum Departement {
		MANAGEMENT, CATERING, SECURITY, CLEANING, NULL;
	}
	
	private String phone;
	private Departement departement;
	
	@Id
	@GeneratedValue
	private long id;
	
	@OneToOne
	private UserAccount userAccount;
	
	@Deprecated
	protected Employee(){}
	
	@Autowired
	public Employee(UserAccount userAccount, String lastname, String firstname, String email, String phone, Departement departement){
		this.userAccount = userAccount;
		userAccount.setLastname(lastname);
		userAccount.setFirstname(firstname);
		userAccount.setEmail(email);
		this.phone = phone;
		this.departement = departement;
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
	
	public void setDepartement(Departement d) {
		this.departement = d;
	}
	
	public Departement getDepartement() {
		return departement;
	}
	
	public void setId(long id) {
		this.id = id;
	}
}
