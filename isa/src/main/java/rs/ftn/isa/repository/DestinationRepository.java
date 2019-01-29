package rs.ftn.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ftn.isa.model.Destination;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long>{

	Destination findOneByNaziv(String naziv);
	Destination findOneById(Long id);
	
	
}
