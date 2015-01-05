package fviv.model;

import org.hibernate.validator.constraints.NotEmpty;

/**
 *@author Hendric Eckelt
*/

public class Registration {
	@NotEmpty(message = "{Registration.lastname.NotEmpty}")
	private String lastname;
	
	@NotEmpty(message = "{Registration.firstname.NotEmpty}")
	private String firstname;
	
	@NotEmpty(message = "{Registration.email.NotEmpty}")
	private String email;
	
	@NotEmpty(message = "{Registration.phone.NotEmpty}")
	private String phone;
	
	@NotEmpty(message = "{Registration.password.NotEmpty}")
	private String password;
	
	public String getLastname(){
		return lastname;
	}
	
	public void setLastname(String lastname){
		this.lastname = lastname;
	}
	
	public String getFirstname(){
		return firstname;
	}
	
	public void setFirstname(String firstname){
		this.firstname = firstname;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getPhone(){
		return phone;
	}
	
	public void setPhone(String phone){
		this.phone = phone;
	}
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
}
