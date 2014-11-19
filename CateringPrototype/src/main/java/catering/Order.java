package catering;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;


@Component
public class Order {
	
	List<Menu> menus;
	
	public Order() {
		menus = new ArrayList<>();
	}
	
	Menu addMenu(Menu m) {
		menus.add(m);
		return m;
	}
	
	public boolean hasNoMenus() {
		return menus.isEmpty();
	}
}