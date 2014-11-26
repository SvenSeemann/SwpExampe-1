package catering;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("deprecation")
@Entity
public class Drink extends Menu {
	
	//protected String description;
	
	@Autowired
	public Drink(String description, Money price) {
		//this.description = description;
	}
	
	@Deprecated
	public Drink () {};
	
}