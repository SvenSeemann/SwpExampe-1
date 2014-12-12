package fviv.ticket;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
@Entity
public class Ticket {
	
	@Id
	@GeneratedValue
	private long id;
	
	private boolean ticketArt;
	 private boolean checked;
	 
	 @Deprecated
		protected Ticket(){
		 
	 }
	 
	 @Autowired
	public Ticket(boolean ticketArt, boolean checked ){
		this.ticketArt = ticketArt;
		checked = false;
	}
	public long getId(){
		return id;
	}
	public boolean getticketArt(){
		return ticketArt; // Eins ist gleich Tagesticket // Null ist gleich 3Tagesticket
	}
	public boolean getChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	

}
