package rs.ftn.isa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ftn.isa.model.Ticket;
import rs.ftn.isa.repository.TicketRepository;

@Service
public class TicketServiceImp  implements TicketService{

	@Autowired
	TicketRepository repozitorijum;

	@Override
	public Ticket findOneById(Long id) {
		// TODO Auto-generated method stub
		return repozitorijum.findOneById(id);
	}
	
	
	
}
