package fviv.festival;

import static org.joda.money.CurrencyUnit.EUR;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.salespointframework.useraccount.UserAccount;
import org.springframework.data.repository.CrudRepository;

import fviv.areaPlanner.AreaItem;
import fviv.areaPlanner.AreaItemsRepository;
import fviv.location.Location;
import fviv.location.LocationRepository;

import java.time.LocalDate;

@Entity
public class Festival {
	
	@Id
	@GeneratedValue
	private long id;
	private LocalDate startDatum;
	private LocalDate endDatum;
	private String festivalName;
	private long locationId;
	private String actors;
	private long preisTag;
	private Money managementSalaryPerDay;
	private Money cateringSalaryPerDay;
	private Money securitySalaryPerDay;
	private Money cleaningSalaryPerDay;
	private int quantManagement;
	private int quantCatering;
	private int quantSecurity;
	private int quantCleaning;
	@OneToOne
	private UserAccount userAccount;
	

	@Deprecated
	protected Festival() {
	}

	@Autowired
	public Festival(LocalDate startDatum, LocalDate endDatum, String festivalName, long locationId,
			String actors, long preisTag) {
		this.startDatum = startDatum;
		this.endDatum = endDatum;
		this.festivalName = festivalName;
		this.locationId = locationId;
		this.actors = actors;
		this.preisTag = preisTag;
		this.managementSalaryPerDay = Money.of(EUR, 0.00);
		this.cateringSalaryPerDay = Money.of(EUR, 0.00);
		this.securitySalaryPerDay = Money.of(EUR, 0.00);
		this.cleaningSalaryPerDay = Money.of(EUR, 0.00);
		this.quantCleaning = 0;
		this.quantManagement = 0;
		this.quantSecurity = 0;
		this.quantCatering = 0;
		//this.area = null;

	}
	public long getId(){
		return id;
	}

	public LocalDate getStartDatum() {
		return startDatum;
	}

	public void setStartDatum(LocalDate startDatum) {
		
		this.startDatum = startDatum;
	}

	public LocalDate getEndDatum() {
		return endDatum;
	}

	public void setEndDatum(LocalDate endDatum) {
		this.endDatum = endDatum;
	}

	public String getFestivalName() {
		return festivalName;
	}

	public void setFestivalName(String festivalName) {
		this.festivalName = festivalName;
	}

	

	public void setMaxVisitors(int maxVisitors) {
		this.maxVisitors = maxVisitors;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public long getPreisTag() {
		return preisTag;
	}

	public void setPreisTag(long preisTag) {
		this.preisTag = preisTag;
	}

	public long getLocationId() {
		return locationId;
	}

	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}

	public Money getManagementSalaryPerDay() {
		return managementSalaryPerDay;
	}

	public void setManagementSalaryPerDay(Money managementSalaryPerDay) {
		this.managementSalaryPerDay = managementSalaryPerDay;
	}

	public Money getCateringSalaryPerDay() {
		return cateringSalaryPerDay;
	}

	public void setCateringSalaryPerDay(Money cateringSalaryPerDay) {
		this.cateringSalaryPerDay = cateringSalaryPerDay;
	}

	public Money getSecuritySalaryPerDay() {
		return securitySalaryPerDay;
	}

	public void setSecuritySalaryPerDay(Money securitySalaryPerDay) {
		this.securitySalaryPerDay = securitySalaryPerDay;
	}

	public Money getCleaningSalaryPerDay() {
		return cleaningSalaryPerDay;
	}

	public void setCleaningSalaryPerDay(Money cleaningSalaryPerDay) {
		this.cleaningSalaryPerDay = cleaningSalaryPerDay;
	}

	public int getQuantManagement() {
		return quantManagement;
	}

	public void setQuantManagement(int quantManagement) {
		this.quantManagement = quantManagement;
	}

	public int getQuantCatering() {
		return quantCatering;
	}

	public void setQuantCatering(int quantSalary) {
		this.quantCatering = quantSalary;
	}

	public int getQuantSecurity() {
		return quantSecurity;
	}
	
	public int getRecommendedQuantSecurity() {
		return (maxVisitors / 100);
	}

	public void setQuantSecurity(int quantSecurity) {
		this.quantSecurity = quantSecurity;
	}

	public int getQuantCleaning() {
		return quantCleaning;
	}

	public void setQuantCleaning(int quantCleaning) {
		this.quantCleaning = quantCleaning;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
	
	
}
