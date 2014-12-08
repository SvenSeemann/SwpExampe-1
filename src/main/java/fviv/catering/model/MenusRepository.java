package fviv.catering.model;

import org.salespointframework.catalog.ProductIdentifier;
import org.springframework.data.repository.CrudRepository;

import fviv.catering.model.Menu.Type;

public interface MenusRepository extends CrudRepository<Menu, ProductIdentifier> {

	Menu findByProductIdentifier(ProductIdentifier id);
	Iterable<Menu> findByType(Type type);
}