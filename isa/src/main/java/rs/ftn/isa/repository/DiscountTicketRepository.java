package rs.ftn.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ftn.isa.model.DiscountTicket;

@Repository
public interface DiscountTicketRepository extends JpaRepository<DiscountTicket, Long>{

	DiscountTicket findOneById(Long id);
	
	
}
