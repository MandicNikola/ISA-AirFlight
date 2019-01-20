package rs.ftn.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ftn.isa.model.AirplaneCompany;
import rs.ftn.isa.model.Hotel;

@Repository
public interface AirplaneCompanyRepository extends JpaRepository<AirplaneCompany,Long>{
	 AirplaneCompany findOneByNaziv(String name);
	
		
}
