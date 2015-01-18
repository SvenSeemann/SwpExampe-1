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
	private Money preisTag;
	private int maxVisitors;
	private Money managementSalaryPerHour;
	private Money leadershipSalaryPerHour;
	private Money cateringSalaryPerHour;
	private Money securitySalaryPerHour;
	private Money cleaningSalaryPerHour;
	private int quantManagement;
	private int quantLeadership;
	private int quantCatering;
	private int quantSecurity;
	private int quantCleaning;

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
	
	public int getQuantCleaning(){
		return quantCleaning;
	}

	public int getRecommendedQuantSecurity() {
		return (maxVisitors / 100);
	}

	public void setQuantSecurity(int quantSecurity) {
		this.quantSecurity = quantSecurity;
	}

	public void setQuantCleaning(int quantCleaning) {
		this.quantCleaning = quantCleaning;
	}


	@Deprecated
	protected Festival() {
	}

	@Autowired
	public Festival(LocalDate startDatum, LocalDate endDatum, String festivalName, long locationId,
			String actors, int maxVisitors, Money preisTag) {
		this.startDatum = startDatum;
		this.endDatum = endDatum;
		this.festivalName = festivalName;
		this.locationId = locationId;
		this.actors = actors;
		this.maxVisitors=maxVisitors;
		this.preisTag = preisTag;
		this.managementSalaryPerHour = Money.of(EUR, 0.00);
		this.leadershipSalaryPerHour = Money.of(EUR, 0.00);
		this.cateringSalaryPerHour = Money.of(EUR, 0.00);
		this.securitySalaryPerHour = Money.of(EUR, 0.00);
		this.cleaningSalaryPerHour = Money.of(EUR, 0.00);
		this.quantCleaning = 0;
		this.quantManagement = 1;
		this.quantLeadership = 1;
		this.quantSecurity = 0;
		this.quantCatering = 0;
		//this.area = null;

	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
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

	public int getMaxVisitors() {
		return this.maxVisitors;
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

	public Money getPreisTag() {
		return preisTag;
	}

	public void setPreisTag(Money preisTag) {
		this.preisTag = preisTag;
	}

	public long getLocationId() {
		return locationId;
	}

	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}

	public Money getManagementSalaryPerHour() {
		return managementSalaryPerHour;
	}

	public void setManagementSalaryPerHour(Money managementSalaryPerHour) {
		this.managementSalaryPerHour = managementSalaryPerHour;
	}

	public Money getCateringSalaryPerHour() {
		return cateringSalaryPerHour;
	}

	public void setCateringSalaryPerHour(Money cateringSalaryPerHour) {
		this.cateringSalaryPerHour = cateringSalaryPerHour;
	}

	public Money getSecuritySalaryPerHour() {
		return securitySalaryPerHour;
	}

	public void setSecuritySalaryPerHour(Money securitySalaryPerHour) {
		this.securitySalaryPerHour = securitySalaryPerHour;
	}

	public Money getCleaningSalaryPerHour() {
		return cleaningSalaryPerHour;
	}

	public void setCleaningSalaryPerHour(Money cleaningSalaryPerHour) {
		this.cleaningSalaryPerHour = cleaningSalaryPerHour;
	}

	public int getQuantLeadership() {
		return quantLeadership;
	}

	public void setQuantLeadership(int quantLeadership) {
		this.quantLeadership = quantLeadership;
	}

	public Money getLeadershipSalaryPerHour() {
		return leadershipSalaryPerHour;
	}

	public void setLeadershipSalaryPerHour(Money leadershipSalaryPerHour) {
		this.leadershipSalaryPerHour = leadershipSalaryPerHour;
	}
}
