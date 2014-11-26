package catering;

import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.data.repository.CrudRepository;

public interface DrinksRepository extends CrudRepository<Drink, ProductIdentifier> {

	Drink findByProductIdentifier(ProductIdentifier id);
}