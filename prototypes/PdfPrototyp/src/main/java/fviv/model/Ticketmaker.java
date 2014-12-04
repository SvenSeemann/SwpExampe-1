package fviv.model;

import org.hibernate.validator.constraints.NotEmpty;

//import org.hibernate.validator.constraints.NotEmpty;

public class Ticketmaker {
	@NotEmpty(message = "{Registration.ticketart.NotEmpty}")
	private String ticketart;

	@NotEmpty(message = "{Registration.festivalname.NotEmpty}")
	private String festivalname;

	@NotEmpty(message = "{Registration.actors.NotEmpty}")
	private String actors;

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
}
