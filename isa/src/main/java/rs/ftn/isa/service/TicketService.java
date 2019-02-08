package rs.ftn.isa.service;

import org.springframework.stereotype.Service;

import rs.ftn.isa.model.Ticket;

@Service
public interface TicketService {

	Ticket findOneById(Long id);
	void saveTicket(Ticket ticket);
}
