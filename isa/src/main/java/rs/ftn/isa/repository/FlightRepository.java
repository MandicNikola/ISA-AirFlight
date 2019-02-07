package rs.ftn.isa.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ftn.isa.model.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>{

	@Query("select u " + 
			"from Flight u  " + 
			"where u.datumPoletanja = :date")
	List<Flight> findByVremePoletanja(@Param("date")Date date);
	
	
	@Query("select u " + 
			"from Flight u  " + 
			"where DATE(u.vremePoletanja) = DATE(:date) AND DATE(u.vremeSletanja) = DATE(:date1)")
	List<Flight> findFlightsBetweenDates(@Param("date") Date date, @Param("date1") Date date1);
	
	

	
	
	Flight findOneById(Long id);
	
}
