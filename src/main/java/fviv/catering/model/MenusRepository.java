package fviv.catering.model;

import java.util.LinkedList;

import org.salespointframework.catalog.ProductIdentifier;
import org.springframework.data.repository.CrudRepository;

import fviv.catering.model.Menu.Type;

/**
 *@author Niklas Fallik
*/

public interface MenusRepository extends CrudRepository<Menu, ProductIdentifier> {

	Menu findByProductIdentifier(ProductIdentifier id);
	Menu findByName(String name);
	Iterable<Menu> findByType(Type type);
}