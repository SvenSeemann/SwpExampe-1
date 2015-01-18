package fviv.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long>{
	Employee findById(long id);
	List<Employee> findByPhone(String phone);
}
