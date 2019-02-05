package rs.ftn.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ftn.isa.model.ReservationTicket;
import rs.ftn.isa.model.Ticket;

@Repository
public interface ReservationTicketRepository extends JpaRepository<ReservationTicket, Long>{

	ReservationTicket findOneById(Long id);
	
	
	
	
}
