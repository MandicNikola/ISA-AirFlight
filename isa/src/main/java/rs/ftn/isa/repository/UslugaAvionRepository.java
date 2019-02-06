package rs.ftn.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ftn.isa.model.UslugaAvion;

@Repository
public interface UslugaAvionRepository extends JpaRepository<UslugaAvion, Long>{

	
	UslugaAvion findOneById(Long id);
}
