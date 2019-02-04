package rs.ftn.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ftn.isa.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

	
	Ticket findOneById(Long id);
	
	
}
