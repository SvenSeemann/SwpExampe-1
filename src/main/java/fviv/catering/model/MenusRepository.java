package fviv.catering.model;

import java.util.Collection;

import org.salespointframework.catalog.ProductIdentifier;
import org.springframework.data.repository.CrudRepository;

import fviv.catering.model.Menu.MenuType;

/**
 *@author Niklas Fallik
*/

public interface MenusRepository extends CrudRepository<Menu, ProductIdentifier> {

	Menu findByProductIdentifier(ProductIdentifier id);
	Menu findByName(String name);
	Collection<Menu> findByType(MenuType type);
	Collection<Menu> findByFestivalId(Long festivalId);
}