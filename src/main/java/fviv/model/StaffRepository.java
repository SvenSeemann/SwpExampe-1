package fviv.model;

import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountIdentifier;
import org.springframework.data.repository.CrudRepository;

public interface StaffRepository extends CrudRepository<UserAccount, UserAccountIdentifier> {

	UserAccount findByUserAccountIdentifier(UserAccountIdentifier id);
}