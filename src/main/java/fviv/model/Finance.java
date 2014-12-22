package fviv.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Hendric Eckelt
 */

@Entity
public class Finance {
	public static enum Reference {
		SALARY, RENT, CATERING;
	}

	public static enum Calc {
		EXPENSE, DEPOSIT;
	}

	private Money amount;
	private Reference reference;
	private Calc calc;

	@Id
	@GeneratedValue
	private long id;

	@Deprecated
	protected Finance() {
	}

	@Autowired
	public Finance(Reference reference, Calc calc, Money amount) {
		this.reference = reference;
		this.calc = calc;
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

	public Calc getCalc() {
		return this.calc;
	}

	public Money getAmount() {
		return this.amount;
	}
}
