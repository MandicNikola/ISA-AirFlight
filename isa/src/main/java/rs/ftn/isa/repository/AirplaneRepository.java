package rs.ftn.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ftn.isa.model.AirplaneCompany;

@Repository
public interface AirplaneRepository extends JpaRepository<AirplaneCompany,Long>{
	
}
