package rs.ftn.isa.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.util.DatePatternToRegexUtil;
import rs.ftn.isa.dto.AirplaneDTO;
import rs.ftn.isa.dto.FlightDTO;
import rs.ftn.isa.model.Destination;
import rs.ftn.isa.model.Flight;
import rs.ftn.isa.model.Ticket;
import rs.ftn.isa.service.DestinationServiceImp;
import rs.ftn.isa.service.FlightService;

@RestController
@RequestMapping(value="api/letovi")
public class FightController {

	@Autowired
	FlightService servis;
	
	@Autowired
	DestinationServiceImp destinationService;
	
	
	
	@RequestMapping(value="/findFlights", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FlightDTO>>  findFlight(@RequestBody FlightDTO flight) throws ParseException{		
	//
		
		String startDestination = flight.getLokPoletanja();
		String endDestination = flight.getLokSletanja();
		int brojOsoba =flight.getBrojLjudi();
		
		
		
		if(startDestination.equals("nema") || startDestination.isEmpty() || startDestination == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		if(endDestination.equals("nema") || endDestination.isEmpty() || endDestination == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		
		if(flight.getDatumPoletanja().equals("nema") || flight.getDatumPoletanja().isEmpty()||flight.getDatumPoletanja() == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		String datumPovratka;
		if(flight.getDatumPovratka().equals("nema") || flight.getDatumPovratka().isEmpty()||flight.getDatumPovratka()== null)
		{
			if(flight.getTip().equals("round-trip"))
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			else
				datumPovratka = "nema";
		}
		else
		{
			datumPovratka = flight.getDatumPovratka();
		}
		
		
		Destination stDestination = destinationService.findDestinationByName(startDestination);
		Destination enDestination = destinationService.findDestinationByName(endDestination);
		
		if(stDestination == null || enDestination == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		String datumPoletanja = flight.getDatumPoletanja();
		
		List<Flight> retFlights = new ArrayList<Flight>();
		
		String type = flight.getTip();
		System.out.println(type);
		if(type.equals("round-trip"))
		{
			Date datePoletanja = new SimpleDateFormat("yyyy-MM-dd").parse(datumPoletanja);
			Date datePovratka = new SimpleDateFormat("yyyy-MM-dd").parse(datumPovratka);
			if(datePoletanja.after(datePovratka))
				return new ResponseEntity<List<FlightDTO>>(new ArrayList<FlightDTO>(), HttpStatus.OK);
			
			retFlights = servis.findFlightsBetweenDates(datePoletanja, datePovratka);
			
		}
		else
		{
			Date datePoletanja = new SimpleDateFormat("yyyy-MM-dd").parse(datumPoletanja);
			retFlights = servis.findActiveFlights(datePoletanja);
		}
		
		//ukoliko nije pronasao nijedan let
		System.out.println("usao ovde");
		if(retFlights == null || retFlights.size() == 0)
			return new ResponseEntity<List<FlightDTO>>(new ArrayList<FlightDTO>(), HttpStatus.OK);
		//obrisemo tipove koji mi ne odgovaraju
		if(!type.equals("all"))
		{
			Iterator<Flight> it = retFlights.iterator();
			while(it.hasNext())
			{
				Flight let = it.next();
				if(!let.getTip().equals(type))
					it.remove();
			}	
		}
		
		//ukoliko je broj osoba veci onda vrsim pretragu koja mi treba
		
		String klasaPuta = "all";
		
		
		if(klasaPuta.equals("mixed"))
		{
			if(brojOsoba > 0)
			{
				Iterator<Flight> it = retFlights.iterator();
				while(it.hasNext())
				{
					Flight let = it.next();
					if(!checkTickets(let, brojOsoba,klasaPuta))
						it.remove();
				}	
			}
		}
		else
		{
			if(brojOsoba > 0)
			{
				Iterator<Flight> it = retFlights.iterator();
				while(it.hasNext())
				{
					Flight let = it.next();
					if(!checkTickets(let, brojOsoba,klasaPuta))
						it.remove();
				}	
			}
			
			
		}
		
	//formiranje DTO jos zavrsiti
		ArrayList<FlightDTO> retFlightDto = new ArrayList<FlightDTO>();
		for(Flight let : retFlights)
		{
			FlightDTO dto = new FlightDTO();
			dto.setIdLeta(let.getId());
			dto.setIdKompanije(let.getAvioKomp().getId());
			dto.setCena(let.getCena());
			dto.setNazivKompanije(let.getAvioKomp().getNaziv());
			
			
			SimpleDateFormat formatVreme = new SimpleDateFormat("HH:mm");
			String vremePoletanja = formatVreme.format(let.getVremePoletanja());
			String vremeSletanja = formatVreme.format(let.getVremeSletanja());
			
			int brojPresedanja = let.getPresedanja().size();
			dto.setVremePoletanja(vremePoletanja);
			dto.setVremeSletanja(vremeSletanja);
			dto.setBrojPresedanja(brojPresedanja);
			
			
			retFlightDto.add(dto);
		}
		
		
		
		return new ResponseEntity<List<FlightDTO>>(retFlightDto, HttpStatus.OK);
	}
	
	public boolean checkTickets(Flight flight, int brOsoba,String type)
	{
		Set<Ticket> tickets = flight.getKarte();
		int brojSlobodnih = 0;
		
		if(type.equals("mixed"))
		{
			for(Ticket ticket : tickets)
			{
				if(!ticket.isRezervisano())
				{
					brojSlobodnih++;
					if(brojSlobodnih == brOsoba)
						return true;
				}
			}
		}
		else
		{
			for(Ticket ticket : tickets)
			{
				if(!ticket.isRezervisano() && ticket.getKlasa().equals(type))
				{
					brojSlobodnih++;
					if(brojSlobodnih == brOsoba)
						return true;
				}
			}
			
			
		}
		
		return false;
	}
	
}
