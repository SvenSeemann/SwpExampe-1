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

	@Id
	@GeneratedValue
	private long id;

	@Deprecated
	protected Finance() {
	}

	@Autowired
	public Finance(Reference reference, Money amount) {
		this.reference = reference;
		this.amount = amount;
		/*
		 * if(expenseType != "salary" && expenseType != "rent" && expenseType !=
		 * "catering" && expenseType != "deposit"){ throw new
		 * IllegalArgumentException("only salary, rent or catering allowed"); }
		 */
	}

	public Reference getReference() {
		return this.reference;
	}

	public Money getAmount() {
		return this.amount;
	}
}
