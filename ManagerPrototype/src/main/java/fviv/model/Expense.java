package fviv.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class Expense {
	private float amount;
	private String expenseType;
	
	@Id
	@GeneratedValue
	private long id;
	
	@Deprecated
	protected Expense(){}
	
	@Autowired
	public Expense(String expenseType, float amount){
		this.expenseType = expenseType;
		this.amount = amount;
		if(expenseType != "salary" && expenseType != "rent" && expenseType != "catering"){
			throw new IllegalArgumentException("only salary, rent or catering allowed");
		}
	}
	
	public String getExpenseType(){
		return expenseType;
	}
	
	public float getAmount(){
		return amount;
	}
}
