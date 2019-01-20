package rs.ftn.isa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ftn.isa.model.AirplaneCompany;

@Repository
public interface AirCompRepository extends JpaRepository<AirplaneCompany, Long>{

	AirplaneCompany findOneByNaziv(String naziv);
	AirplaneCompany findOneById(Long id);
	
	
}
