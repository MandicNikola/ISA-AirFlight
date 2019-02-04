package rs.ftn.isa.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ftn.isa.model.Flight;
import rs.ftn.isa.repository.FlightRepository;

@Service
public class FlightService {

	@Autowired
	FlightRepository repozitorujum;
	
	
	public void deleteFlightById(Long id)
	{
		repozitorujum.deleteById(id);
	}
	
	/*
	 metoda koja pronalazi aktivne letove kompanije ukoliko su mi potrebni
	 */
	public List<Flight> findActiveFlights(Date date)
	{
		return repozitorujum.findByVremePoletanja(date);
	}
	
	public Flight findOneFlightById(Long id)
	{
		return repozitorujum.findOneById(id);
	}
	
	public List<Flight> findFlightsBetweenDates(Date dateDepart, Date dateReturn)
	{
		return repozitorujum.findFlightsBetweenDates(dateDepart, dateReturn);
	}
	
	
	public void saveFlight(Flight f)
	{
		repozitorujum.save(f);
	}
	
	
}
