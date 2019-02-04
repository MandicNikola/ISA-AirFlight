package rs.ftn.isa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ftn.isa.model.ReservationTicket;
import rs.ftn.isa.repository.ReservationTicketRepository;

@Service
public class ReservationTicketServiceImp implements ReservationTicketService{

	@Autowired
	ReservationTicketRepository repozitorijum;

	@Override
	public ReservationTicket findOneById(Long id) {
		// TODO Auto-generated method stub
		return repozitorijum.findOneById(id);
	}

	@Override
	public void saveReservation(ReservationTicket reservation) {
		// TODO Auto-generated method stub
		repozitorijum.save(reservation);
	}
	
	
	
}
