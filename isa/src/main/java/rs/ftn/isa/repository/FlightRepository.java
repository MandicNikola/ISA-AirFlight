package rs.ftn.isa.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ftn.isa.model.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>{

	@Query("select u " + 
			"from Flight u  " + 
			"where CAST(u.vremePoletanja as date) = CAST(:date as date)")
	List<Flight> findFlights(@Param("date") Date date);
	
	
	@Query("select u " + 
			"from Flight u  " + 
			"where u.vremePoletanja > :date AND u.vremeSletanja < :date1")
	List<Flight> findFlightsBetweenDates(@Param("date") Date date, @Param("date1") Date date1);
	
	
	Flight findOneById(Long id);
	
}
