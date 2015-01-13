package fviv.festival;

import static org.joda.money.CurrencyUnit.EUR;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.joda.money.Money;
import java.time.LocalDate;

@Entity
public class Festival {
	
	@Id
	@GeneratedValue
	private long id;
	private LocalDate startDatum;
	private LocalDate endDatum;
	private String festivalName;
	private String location;
	private String actors;
	private int maxVisitors;
	private long preisTag;
	private Money managementSalaryPerDay;
	private Money cateringSalaryPerDay;
	private Money securitySalaryPerDay;
	private Money cleaningSalaryPerDay;
	private int quantManagement;
	private int quantCatering;
	private int quantSecurity;
	private int quantCleaning;
	private String managerUserName;

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

	@Deprecated
	protected Festival() {
	}

	public Festival(LocalDate startDatum, LocalDate endDatum,
			String festivalName, String location, String actors,
			int maxVisitors, long preisTag, String managerUserName) {

		this.startDatum = startDatum;
		this.endDatum = endDatum;
		this.festivalName = festivalName;
		this.location = location;
		this.actors = actors;
		this.maxVisitors = maxVisitors;
		this.preisTag = preisTag;
		this.managementSalaryPerDay = Money.of(EUR, 0.00);
		this.cateringSalaryPerDay = Money.of(EUR, 0.00);
		this.securitySalaryPerDay = Money.of(EUR, 0.00);
		this.cleaningSalaryPerDay = Money.of(EUR, 0.00);
		this.quantCleaning = 0;
		this.quantManagement = 0;
		this.quantSecurity = 0;
		this.quantCatering = 0;
		this.managerUserName = managerUserName;
		//this.area = null;

	}

	public long getId() {
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

	public int getMaxVisitors() {
		return maxVisitors;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public String getManager() {
		return managerUserName;
	}
	
	public void setManager(String managerUserName){
		this.managerUserName = managerUserName;	
	}
}
