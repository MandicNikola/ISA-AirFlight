package rs.ftn.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ftn.isa.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

	
	Ticket findOneById(Long id);
	
	
}
