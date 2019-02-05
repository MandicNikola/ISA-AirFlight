package rs.ftn.isa.service;

import org.springframework.stereotype.Service;

import rs.ftn.isa.model.ReservationTicket;
import rs.ftn.isa.model.Ticket;

@Service
public interface ReservationTicketService {

	ReservationTicket findOneById(Long id);
	void saveReservation(ReservationTicket reservation);
	
}
