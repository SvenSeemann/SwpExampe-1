package fviv.model;

public class Person {
	private String lastname, firstname, email, phone;
	
	public Person(String lastname, String firstname, String email, String phone){
		this.lastname = lastname;
		this.firstname = firstname;
		this.email = email;
		this.phone = phone;
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
