package fviv.areaPlanner;


import static org.joda.money.CurrencyUnit.*;

import java.util.Arrays;

import org.joda.money.Money;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Units;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountIdentifier;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
@Component
public class PlanningDataInitializer implements DataInitializer {
	private final PlanningRepository planningRepository;
	
	@Autowired 
	public PlanningDataInitializer(PlanningRepository planningRepository){
	this.planningRepository = planningRepository;
}	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
	private void initializeCoords(PlanningRepository planningRepository){
		//planningRepository.save(new Coords(width, height, xPos, yPos));
	}
}
