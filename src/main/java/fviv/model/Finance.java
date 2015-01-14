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
	
	public static enum FinanceType {
		SALARY, RENT, CATERING;
	}

	private long festivalId;
	private Money amount;
	private Reference reference;
	private FinanceType financeType;

	@Id
	@GeneratedValue
	private long id;

	@Deprecated
	protected Finance() {
	}

	@Autowired
	public Finance(long festivalId, Reference reference, Money amount, FinanceType financeType) {
		//this.festivalId = festivalId;
		this.reference = reference;
		this.amount = amount;		
		this.financeType = financeType;
		this.setFestivalId(festivalId);
	}

	/** Returns whether the finance is an expense or an deposit.
	 * 
	 * @return Reference
	 */
	
	public Reference getReference() {
		return this.reference;
	}
	
	/** Get the amount of the expense or deposit.
	 * 
	 * @return Amount of Finance
	 */

	public Money getAmount() {
		return this.amount;
	}
	
	/** Get the purpose of the deposit or expense.
	 * 
	 * @return FinanceType
	 */
	
	public FinanceType getFinanceType(){
		return financeType;
	}

	public long getFestivalId() {
		return festivalId;
	}

	public void setFestivalId(long festivalId) {
		this.festivalId = festivalId;
	}
}
