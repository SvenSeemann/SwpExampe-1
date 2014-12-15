package fviv.ticket;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
@Entity
public class Ticket {
	private boolean ticketArt;
	private boolean checked;
	
	
	@Id
	@GeneratedValue
	private long id;
	 
	 @Deprecated
		protected Ticket(){
		 
	 }
	 
	 @Autowired
	public Ticket(boolean ticketArt, boolean checked ){
		this.ticketArt = ticketArt;
		this.checked = checked;
	}
	public long getId(){
		return id;
	}
	public void setTicketArt(boolean ticketArt){
		this.ticketArt = ticketArt;
	}
	public boolean getticketArt(){
		return ticketArt; // Eins (true) ist gleich Tagesticket // Null (false) ist gleich 3Tagesticket
	}
	public boolean getChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	

}
