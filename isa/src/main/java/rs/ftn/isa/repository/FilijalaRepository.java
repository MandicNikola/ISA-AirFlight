package rs.ftn.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ftn.isa.model.Filijala;

@Repository
public interface FilijalaRepository extends JpaRepository<Filijala, Long> {
		
	Filijala findOneById(Long id);
}
