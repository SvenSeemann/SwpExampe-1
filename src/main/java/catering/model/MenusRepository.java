package catering.model;

import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.data.repository.CrudRepository;

import catering.model.Menu.Type;

public interface MenusRepository extends CrudRepository<Menu, ProductIdentifier> {

	Menu findByProductIdentifier(ProductIdentifier id);
	Iterable<Menu> findByType(Type type);
}