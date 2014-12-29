package fviv.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Hendric Eckelt
 * @author Niklas Fallik
 */

@Entity
public class Finance {

	public static enum Reference {
		EXPENSE, DEPOSIT;
	}

	private Money amount;
	private Reference reference;
	private String financeType;

	@Id
	@GeneratedValue
	private long id;

	@Deprecated
	protected Finance() {
	}

	@Autowired
	public Finance(Reference reference, Money amount, String financeType) {
		this.reference = reference;
		this.amount = amount;
		
		  if(financeType != "salary" && financeType != "rent" && financeType !=
		  "catering"){ throw new
		  IllegalArgumentException("only salary, rent or catering allowed"); }
		
		this.financeType = financeType;
	}

	public Reference getReference() {
		return this.reference;
	}

	public Money getAmount() {
		return this.amount;
	}
	
	public String getFinanceType(){
		return financeType;
	}
}
