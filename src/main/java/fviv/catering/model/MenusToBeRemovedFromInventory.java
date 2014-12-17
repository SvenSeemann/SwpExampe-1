package fviv.catering.model;

import java.util.Optional;

import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.inventory.InventoryItem;
import org.springframework.data.repository.CrudRepository;

public interface MenusToBeRemovedFromInventory extends CrudRepository<Menu, ProductIdentifier> {
	
	Menu findByProductIdentifier(ProductIdentifier id);

}