package rs.ftn.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ftn.isa.model.RezervacijaHotel;
import rs.ftn.isa.model.Room;

@Repository
public interface RezervacijaHotelRepository extends JpaRepository<RezervacijaHotel, Long >{
	RezervacijaHotel findOneById(Long id);
	
	
	
	@Query("select u " + 
			"from RezervacijaHotel u  " + 
			"where u.rezavion = :id")
	RezervacijaHotel findPoAvionu(@Param("id")Long id);
	
	
	
	
	
}
