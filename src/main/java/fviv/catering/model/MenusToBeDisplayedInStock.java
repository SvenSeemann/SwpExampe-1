package fviv.catering.model;

import org.salespointframework.catalog.ProductIdentifier;
import org.springframework.data.repository.CrudRepository;

public interface MenusToBeDisplayedInStock extends CrudRepository<Menu, ProductIdentifier> {

	
}
