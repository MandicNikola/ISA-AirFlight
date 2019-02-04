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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.util.DatePatternToRegexUtil;
import rs.ftn.isa.dto.AirplaneDTO;
import rs.ftn.isa.dto.FlightDTO;
import rs.ftn.isa.dto.PassengerDTO;
import rs.ftn.isa.dto.SeatDTO;
import rs.ftn.isa.model.Destination;
import rs.ftn.isa.model.Flight;
import rs.ftn.isa.model.Seat;
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
	
	
	/*
	 * metoda ne vraca podatke koji su mi potrebni sutra to jos istestirati da proverim
	 */
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
		
		//provera dobijenih letova sa destinacijom
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
			System.out.println("usao u pretragu2");
			retFlights = servis.findFlightsBetweenDates(datePoletanja, datePovratka);
			
		}
		else
		{
			System.out.println("usao u pretragu1");
			System.out.println(datumPoletanja);
			Date datePoletanja = new SimpleDateFormat("yyyy-MM-dd").parse(datumPoletanja);
			retFlights = servis.findActiveFlights(datePoletanja);
		}
		
		//ukoliko nije pronasao nijedan let
		System.out.println("usao ovde");
		System.out.println(retFlights.size());
		if(retFlights == null || retFlights.size() == 0)
			return new ResponseEntity<List<FlightDTO>>(new ArrayList<FlightDTO>(), HttpStatus.OK);
		//obrisemo tipove koji mi ne odgovaraju
		
		//poredjenje sa destinacijama
		Iterator<Flight> iter = retFlights.iterator();
		while(iter.hasNext())
		{
			Flight let = iter.next();
			if(!let.getPoletanje().getNaziv().equals(stDestination.getNaziv()) || !let.getSletanje().getNaziv().equals(enDestination.getNaziv()))
			{
				iter.remove();
			}
			
		}	
		
		
		
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
		
		String klasaPuta = flight.getKlasa();
		
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
		if(!klasaPuta.equals("mixed"))
		{
			for(Flight let : retFlights)
			{
				FlightDTO dto = formflightDto(let, klasaPuta);			
				retFlightDto.add(dto);
			}
			
		}
		else
		{
			for(Flight let : retFlights)
			{
				if(freeTickets(let, "ekonomska"))
				{
					FlightDTO dtoEconomic = formflightDto(let, "ekonomska");
					retFlightDto.add(dtoEconomic);
				}
				if(freeTickets(let, "biznis"))
				{
					FlightDTO dtoBiznis = formflightDto(let, "biznis");
					retFlightDto.add(dtoBiznis);
				}
				if(freeTickets(let, "first"))
				{
					FlightDTO dtoFirst = formflightDto(let, "first");
					retFlightDto.add(dtoFirst);
				}				
			}
			
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
	
	/*
	 * metoda koja mi vraca broj slobodnih karata po 
	 */
	public boolean freeTickets(Flight flight, String type)
	{
		Set<Ticket> tickets = flight.getKarte();
		for(Ticket ticket : tickets)
		{
			if(!ticket.isRezervisano() && ticket.getKlasa().equals(type))
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	public FlightDTO formflightDto(Flight let, String klasaPuta)
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
		dto.setDuzina(let.getDuzina());
		dto.setKlasa(klasaPuta);
		
	
		return dto;
	}
	
	
	
	@RequestMapping(value="/seats/{id}/{class}", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<SeatDTO>> returnSeats(@PathVariable("id") Long id,@PathVariable("class") String klasa)		
	{
		
		Flight flight = servis.findOneFlightById(id);
		
		//pokupim konfiguraciju
		String configuration = flight.getPlane().getKonfiguracija();
		
		ArrayList<SeatDTO> retSeats = new ArrayList<SeatDTO>();
		
		for(Ticket ticket : flight.getKarte())
		{
			if(ticket.getKlasa().equals(klasa))
			{
				Seat sediste = ticket.getSediste();
				SeatDTO dto = new SeatDTO();
				dto.setIdSedista(sediste.getId());
				dto.setIdKarte(ticket.getId());
				dto.setRezervisano(ticket.isRezervisano());
				dto.setBrojKolone(sediste.getKolona());
				dto.setBrojReda(sediste.getRed());
				dto.setKonfiguracija(configuration);
				retSeats.add(dto);
		
			}
		}
		
		return new ResponseEntity<List<SeatDTO>>(retSeats, HttpStatus.OK);	
	}
	
	
	@RequestMapping(value="/makeReservation/{id}/{class}", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> makeReservation(@RequestBody ArrayList<PassengerDTO> passengers)		
	{
		
		
		
		return null;
	}
	
	
	
	
	
	
}
