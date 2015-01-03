package fviv.festival;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Festival {

	@Id
	@GeneratedValue
	private long id;
	private Date startDatum;
	private Date endDatum;
	private String festivalName;
	private String location;
	private String actors;
	private int maxVisitors;
	private long preisTag;

	@Deprecated
	protected Festival() {
	}

	public Festival(Date startDatum, Date endDatum, String festivalName, String location,
			String actors, int maxVisitors, long preisTag) {
		this.startDatum = startDatum;
		this.endDatum = endDatum;
		this.festivalName = festivalName;
		this.location = location;
		this.actors = actors;
		this.maxVisitors = maxVisitors;
		this.preisTag = preisTag;

	}
	public long getId(){
		return id;
	}

	public Date getStartDatum() {
		return startDatum;
	}

	public void setStartDatum(Date startDatum) {
		
		this.startDatum = startDatum;
	}

	public Date getEndDatum() {
		return endDatum;
	}

	public void setEndDatum(Date endDatum) {
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


}
