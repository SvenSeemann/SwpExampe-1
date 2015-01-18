package fviv.ticket;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class Ticket {
	private boolean ticketArt;
	private boolean checked;
	private String festivalName;
	private LocalDate tagesticketdate;

	@Id
	@GeneratedValue
	private long id;

	@Deprecated
	protected Ticket() {

	}
	@Autowired
	public Ticket(boolean ticketArt, boolean checked, String festivalName,
			LocalDate tagesticketdate) {
		this.ticketArt = ticketArt;
		this.checked = checked;
		this.festivalName = festivalName;
		this.tagesticketdate = tagesticketdate;
	}

	public long getId() {
		return id;
	}

	public void setTicketArt(boolean ticketArt) {
		this.ticketArt = ticketArt;
	}
/**
 * eins gleich tagesticket false gleich 3tagesticket
 * @return
 */
	public boolean getticketArt() {
		return ticketArt; // Eins (true) ist gleich Tagesticket // Null (false)
							// ist gleich 3Tagesticket
	}

	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getFestivalName() {
		return festivalName;
	}

	public void setFestivalName(String festivalName) {
		this.festivalName = festivalName;
	}

	public LocalDate getTagesticketDate() {
		return tagesticketdate;
	}

	public void setTagesticketDate(LocalDate tagesticketdate) {
		this.tagesticketdate = tagesticketdate;
	}

}
