package fviv;

import java.util.Arrays;

import org.salespointframework.core.DataInitializer;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import fviv.model.StaffRepository;

@Component
public class FvivDataInitializer implements DataInitializer {
	
	private final StaffRepository staffRepository;
	private final UserAccountManager userAccountManager;
	
	@Autowired
	public FvivDataInitializer (StaffRepository staffRepository, UserAccountManager userAccountManager) {
		
		Assert.notNull(staffRepository, "StaffRepository must not be null!");
		
		this.staffRepository = staffRepository;
		this.userAccountManager = userAccountManager;
		
	}
	
	@Override
	public void initialize() {
		initializeUsers(userAccountManager, staffRepository);
	}
	
	private void initializeUsers(UserAccountManager userAccountManager, StaffRepository staffRepository){
		
		final Role bossRole = new Role("ROLE_BOSS");
		final Role managerRole = new Role("ROLE_MANAGER");
		final Role catererRole = new Role("ROLE_CATERER");
		
		
		UserAccount boss = userAccountManager.create("boss", "123", bossRole);
		UserAccount manager = userAccountManager.create("manager", "123", managerRole);
		UserAccount caterer = userAccountManager.create("caterer", "123", catererRole);
		userAccountManager.save(boss);
		userAccountManager.save(manager);
		userAccountManager.save(caterer);
	}
	
}
