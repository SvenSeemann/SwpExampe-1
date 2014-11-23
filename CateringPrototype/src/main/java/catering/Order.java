package catering;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;


@Component
public class Order {
	
	private List<Menu> menus;
	private int[] ct = new int[8];
	private String[] output = new String[8];
	
	public Order() {
		menus = new ArrayList<>();
		for (int i = 0; i <= 7; i++) {
			output[i] = "";
		}
	}
	
	Menu addMenu(Menu m) {
		menus.add(m);
		count(m);
		return m;
	}
	
	public boolean hasNoMenus() {
		return menus.isEmpty();
	}
	
	public Menu getMenu() {
		Iterator<Menu> iterator = menus.iterator();
		if (iterator.hasNext()) {
			return iterator.next();
		} else {
			return null;
		}
	}
	
	public String getOutput(int i) {
		return output[i];
	}
	
	/*public String toString() {
		//Iterator<Menu> iterator = menus.iterator();
		
		if (menus.isEmpty()) {
			return "There were no menus ordered.";
		} else {
			
			output[0] = ct[0] + "x " + ;
			
			
			
			
			
			//return menus.get(0).toString();
			/*while (iterator.hasNext()) {
				//count(iterator.next());
				output = output + iterator.next().toString();
			}*/
			/*return output;
		}
	}*/
	
	private void count (Menu m) {
		for (int i = 0; i <= 7; i++) {
			if (m.getId() == i) {
				this.ct[i] += 1;
				output[i] = this.ct[i] + "x " + m.getDescription() + " " + m.getPrice();
			}
		}
	}
	
}