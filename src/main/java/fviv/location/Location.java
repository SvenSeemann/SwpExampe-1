package fviv.location;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;



@Entity
public class Location {
	@Id
	@GeneratedValue
	private long id;
	
	private int width;
	private int height;
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
	private String adresse;
	private int maxVisitors;
	private Money costPerDay;
	
	@Deprecated
	protected Location(){}
	@Autowired
	public Location(String name,int width, int height, int maxVisitors, String adresse, Money costPerDay){
		
		this.width= width;
		this.height = height;
		this.name = name;
		this.maxVisitors= maxVisitors;;
		this.adresse = adresse;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LL-dd");
		LocalDate date1 = LocalDate.parse("1001-12-30", formatter);
		LocalDate date2 = LocalDate.parse("1002-01-03", formatter);
		this.startDate = date1;
		this.endDate = date2;
		this.setCostPerDay(costPerDay);
		
	}
	
	
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public int getHeight() {
		return height;
	}
	public void setHeigth(int height) {
		this.height = height;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public long getId(){
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMaxVisitors() {
		return maxVisitors;
	}
	public void setMaxVisitors(int maxVisitors) {
		this.maxVisitors = maxVisitors;
	}

	public Money getCostPerDay() {
		return costPerDay;
	}
	
	public void setCostPerDay(Money costPerDay) {
		this.costPerDay = costPerDay;
	}
	
}
