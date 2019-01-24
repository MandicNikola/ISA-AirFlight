package rs.ftn.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ftn.isa.model.Pozivnica;



public interface PozivnicaRepository extends JpaRepository<Pozivnica, Long>{

	Pozivnica findOneById(Long id);
	
}
