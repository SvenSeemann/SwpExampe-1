package catering;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("deprecation")
@Entity
public class Meal extends Menu {

	
	@Autowired
	public Meal(String description, Money price) {

	}
	
	@Deprecated
	public Meal () {};

}