package rs.ftn.isa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ftn.isa.model.Filijala;

@Repository
public interface FilijalaRepository extends JpaRepository<Filijala, Long> {
		
	Filijala findOneById(Long id);
	Filijala findOneByGrad(String grad);
	Filijala findOneByUlica(String ulica);
	List<Filijala> findAllByUlica(String ulica);
	List<Filijala> findAllByGrad(String grad);
	
}
