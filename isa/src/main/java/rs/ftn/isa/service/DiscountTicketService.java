package rs.ftn.isa.service;

import org.springframework.stereotype.Service;

import rs.ftn.isa.model.DiscountTicket;

@Service
public interface DiscountTicketService {

	DiscountTicket findOneById(Long id);
	void removeDiscount(DiscountTicket discount);
}
