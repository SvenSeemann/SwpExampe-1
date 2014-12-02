package fviv.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class Ticket{
	private String ticketart, festivalname, actors;
		
	@Id
	@GeneratedValue
	private long id;
	
	@Deprecated
	protected Ticket(){}
	
	@Autowired
	public Ticket(String ticketart, String festivalname, String actors){
	this.setTicketart(ticketart);
	this.setFestivalname("Wunderland");
	this.setActors("AVICII & UND MEHR");
	}

	public String getTicketart() {
		return ticketart;
	}

	public void setTicketart(String ticketart) {
		this.ticketart = ticketart;
	}

	public String getFestivalname() {
		return festivalname;
	}

	public void setFestivalname(String festivalname) {
		this.festivalname = festivalname;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}
	
	public void setTicketid(long id){
		this.id = id;
	}
	
	public long getTicketid(){
		return id;
	}
	
}
