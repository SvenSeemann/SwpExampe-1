package catering;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;


@Component
public class Order {
	
	private float total;
	private final MealsRepository mealsRepository;
	private final DrinksRepository drinksRepository;
	
	@Autowired
	public Order(MealsRepository mealsRepository, DrinksRepository drinksRepository) {
		this.mealsRepository = mealsRepository;
		this.drinksRepository = drinksRepository;
	}
	
	public void addMealToRepository(Meal meal) {
		mealsRepository.save(meal);
	}
	
	public void addDrinkToRepository(Drink drink) {
		drinksRepository.save(drink);
	}
	
	public MealsRepository getOrderedMeals() {
		return this.mealsRepository;
	}
	
	public DrinksRepository getOrderedDrinks() {
		return this.drinksRepository;
	}
	
	public void cancel() {
		this.mealsRepository.deleteAll();
		this.drinksRepository.deleteAll();
		//TODO
	}
	
	public float getTotal() {
		return this.total;
	}
	
	private void count (Menu m) {
		//TODO
	}
	
}