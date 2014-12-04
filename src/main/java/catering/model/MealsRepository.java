package catering.model;

import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.data.repository.CrudRepository;

public interface MealsRepository extends CrudRepository<Meal, ProductIdentifier> {

	Meal findByProductIdentifier(ProductIdentifier id);
}