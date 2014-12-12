package fviv.controller;

import org.springframework.stereotype.Controller;

import fviv.ticket.TicketRepository;
import fviv.ticket.Ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class TicketController {
	private final TicketRepository ticketRepository;

	@Autowired
	public TicketController(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}

	@RequestMapping({ "/ticket" })
	public String index() {
		return "ticket";
	}

	@RequestMapping(value = "/newTicket", method = RequestMethod.POST)
	public String newTicket(@RequestParam("ticketart") boolean ticketart, @RequestParam("checked") boolean checked){
		// Create Ticket
		Ticket ticket = new Ticket(ticketart, checked); 	// Eins ist gleich Tagesticket // Null ist gleich 3Tagesticket
		ticketRepository.save(ticket);

		return "redirect:/";
	}
}
