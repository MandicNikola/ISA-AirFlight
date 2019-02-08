package rs.ftn.isa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import rs.ftn.isa.model.DiscountTicket;
import rs.ftn.isa.repository.DiscountTicketRepository;

@Service
public class DiscountTicketServiceImp implements DiscountTicketService{

	@Autowired
	DiscountTicketRepository repozitorijum;
	
	@Override
	public DiscountTicket findOneById(Long id) {
		// TODO Auto-generated method stub
		return repozitorijum.findOneById(id);
	}

	@Override
	public void removeDiscount(DiscountTicket discount) {
		// TODO Auto-generated method stub
		repozitorijum.delete(discount);
	}

}
