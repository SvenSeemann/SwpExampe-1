package fviv.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *@author Hendric Eckelt
*/

@Entity
public class Expense {
	public static enum ExpenseType {
		SALARY, RENT, CATERING, DEPOSIT;
	}
	
	private Money amount;
	private ExpenseType expenseType;
	
	@Id
	@GeneratedValue
	private long id;
	
	@Deprecated
	protected Expense(){}
	
	@Autowired
	public Expense(ExpenseType expenseType, Money amount){
		this.expenseType = expenseType;
		this.amount = amount;
		/*if(expenseType != "salary" && expenseType != "rent" && expenseType != "catering" && expenseType != "deposit"){
			throw new IllegalArgumentException("only salary, rent or catering allowed");
		}*/
	}
	
	public ExpenseType getExpenseType(){
		return expenseType;
	}
	
	public Money getAmount(){
		return amount;
	}
}
