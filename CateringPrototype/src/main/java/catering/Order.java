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
	@Autowired
	private final OrderedMealsRepository orderedMealsRepository;
	private final OrderedDrinksRepository orderedDrinksRepository;
	
	public Order(OrderedMealsRepository orderedMealsRepository, OrderedDrinksRepository orderedDrinksRepository) {
		this.orderedMealsRepository = orderedMealsRepository;
		this.orderedDrinksRepository = orderedDrinksRepository;
	}
	
	public void addMealToRepository(Meal meal) {
		orderedMealsRepository.save(meal);
	}
	
	public void addDrinkToRepository(Drink drink) {
		orderedDrinksRepository.save(drink);
	}
	
	public OrderedMealsRepository getOrderedMeals() {
		return this.orderedMealsRepository;
	}
	
	public OrderedDrinksRepository getOrderedDrinks() {
		return this.orderedDrinksRepository;
	}
	
	public void cancel() {
		//TODO
	}
	
	public float getTotal() {
		return this.total;
	}
	
	private void count (Menu m) {
		//TODO
	}
	
}