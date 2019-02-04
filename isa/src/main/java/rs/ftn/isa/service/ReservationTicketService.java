package rs.ftn.isa.service;

import org.springframework.stereotype.Service;

import rs.ftn.isa.model.ReservationTicket;

@Service
public interface ReservationTicketService {

	ReservationTicket findOneById(Long id);
	void saveReservation(ReservationTicket reservation);
}
