package catering;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.joda.money.Money;
import org.salespointframework.catalog.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestParam;

@Entity
public class Menu extends Product { //abstract
	
	@SuppressWarnings("deprecation")
	public Menu(String name, Money price) {
		
	}
	
	@Deprecated
	public Menu () {};
	
	
	
}
