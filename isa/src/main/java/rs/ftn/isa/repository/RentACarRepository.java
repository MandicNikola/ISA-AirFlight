package rs.ftn.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ftn.isa.model.RentACar;

@Repository
public interface RentACarRepository extends JpaRepository<RentACar,Long>{
	
	 RentACar findOneByNaziv(String name);
	 RentACar findOneById(Long id);
	 
	 
	 

}
