package rs.ftn.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ftn.isa.model.Pozivnica;


@Repository
public interface PozivnicaRepository extends JpaRepository<Pozivnica, Long>{

	Pozivnica findOneById(Long id);
}
