package fviv.festival;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
public class Festival {

	@Id
	@GeneratedValue
	private long id;
	private Date startDatum;
	private Date endDatum;
	private String festivalName;
	private String actors;
	private int maxVisitors;
	private int preisTag;
	private int preisTage;

	@Deprecated
	protected Festival() {
	}

	public Festival(Date startDatum,Date endDatum, String festivalName, String actors, int maxVisitors, int preisTag, int preisTage){
		this.actors = actors;
		this.endDatum = endDatum;
		this.startDatum = startDatum;
		this.festivalName = festivalName;
		this.maxVisitors = maxVisitors;
		this.preisTag = preisTag;
		this.preisTage = preisTage;
		
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

	public int getPreisTag() {
		return preisTag;
	}

	public void setPreisTag(int preisTag) {
		this.preisTag = preisTag;
	}

	public int getPreisTage() {
		return preisTage;
	}

	public void setPreisTage(int preisTage) {
		this.preisTage = preisTage;
	}

	
}
