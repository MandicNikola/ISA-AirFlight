package rs.ftn.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ftn.isa.model.ReservationTicket;

@Repository
public interface ReservationTicketRepository extends JpaRepository<ReservationTicket, Long>{

	ReservationTicket findOneById(Long id);
	
	
}
