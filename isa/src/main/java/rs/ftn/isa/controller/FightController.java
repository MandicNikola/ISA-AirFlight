package rs.ftn.isa.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

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
import rs.ftn.isa.dto.PozivnicaDTO;
import rs.ftn.isa.dto.SeatDTO;
import rs.ftn.isa.model.AirplaneCompany;
import rs.ftn.isa.model.Destination;
import rs.ftn.isa.model.DiscountTicket;
import rs.ftn.isa.model.Flight;
import rs.ftn.isa.model.PassengerInfo;
import rs.ftn.isa.model.Pozivnica;
import rs.ftn.isa.model.ReservationTicket;
import rs.ftn.isa.model.Seat;
import rs.ftn.isa.model.StatusRezervacije;
import rs.ftn.isa.model.Ticket;
import rs.ftn.isa.model.User;
import rs.ftn.isa.service.DestinationServiceImp;
import rs.ftn.isa.service.DiscountTicketServiceImp;
import rs.ftn.isa.service.EmailService;
import rs.ftn.isa.service.FlightService;
import rs.ftn.isa.service.PozivnicaServiceImp;
import rs.ftn.isa.service.TicketServiceImp;
import rs.ftn.isa.service.UserServiceImpl;

@RestController
@RequestMapping(value="api/letovi")
public class FightController {

	@Autowired
	FlightService servis;
	
	@Autowired
	DestinationServiceImp destinationService;
	
	@Autowired
	TicketServiceImp servisKarata;
	
	@Autowired
	UserServiceImpl servisKorisnik;
	
	@Autowired
	EmailService servisMail;
	
	@Autowired
	PozivnicaServiceImp servisPozivnica;
	
	@Autowired
	DiscountTicketServiceImp servisPopusti;
	
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
		
		SimpleDateFormat formatDatum = new SimpleDateFormat("yyyy-MM-dd");
		String datumSletanja = formatDatum.format(let.getDatumSletanja());
		
		
		int brojPresedanja = let.getPresedanja().size();
		dto.setVremePoletanja(vremePoletanja);
		dto.setVremeSletanja(vremeSletanja);
		dto.setBrojPresedanja(brojPresedanja);
		dto.setDuzina(let.getDuzina());
		dto.setKlasa(klasaPuta);
		dto.setDatumSletanja(datumSletanja);
		
	
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
				
				//ukoliko ima popust ovako ne sme da je rezervise
				if(ticket.getPopustiKarte().size() > 0)
					dto.setRezervisano(true);
				
				retSeats.add(dto);
				
				
				
		
			}
		}
		
		return new ResponseEntity<List<SeatDTO>>(retSeats, HttpStatus.OK);	
	}
	
	
	@RequestMapping(value="/makeReservation/{id}", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> makeReservation(@RequestBody ArrayList<PassengerDTO> passengers,@Context HttpServletRequest request,@PathVariable("id") Long id) throws ParseException		
	{
		User user = (User) request.getSession().getAttribute("ulogovan");
		if(user == null)
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		ReservationTicket rezervation = new ReservationTicket();
		user = servisKorisnik.findOneById(user.getId());
		
		rezervation.setKorisnik(user);
		rezervation.setStatus(StatusRezervacije.AKTIVNA);
		user.getResTicket().add(rezervation);
		
		SimpleDateFormat formatDatum = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		rezervation.setDatumRezervacije(date);
		
		if(passengers.size() > 0)
		{
			for(PassengerDTO passDto : passengers)
			{
				Ticket ticket = servisKarata.findOneById(passDto.getIdKarte());
				
				Date datum = formatDatum.parse(passDto.getDatumRodjenja());
				
				
				PassengerInfo info = new PassengerInfo(passDto.getIme(), passDto.getPrezime(), passDto.getMail(), passDto.getTelefon(), passDto.getPassport(), datum);
				ticket.setPassengerInfo(info);
				info.setKarta(ticket);
				ticket.setRezervisano(true);
				rezervation.getKarte().add(ticket);
				ticket.setReservationTicket(rezervation);
				
				try {
					servisKarata.saveTicket(ticket);
					
				} catch (Exception e) {
					return new ResponseEntity<Long>(HttpStatus.CONFLICT);
				}
			}		
		}
		//ostaje mi jos da podesim pasos sto mi fali kod karte
		
		Ticket karta = servisKarata.findOneById(id);
		PassengerInfo info = new PassengerInfo();
		info.setIme(user.getIme());
		info.setMail(user.getMail());
		info.setPrezime(user.getPrezime());
		karta.setPassengerInfo(info);
		info.setKarta(karta);
		karta.setRezervisano(true);
		rezervation.getKarte().add(karta);
		karta.setReservationTicket(rezervation);
		
		int bodovi = 0;
		
		for(int i = 0; i < karta.getLet().getDuzina(); i=i+100)
				bodovi++;
		
		user.setBodovi(user.getBodovi()+bodovi);
		
		try {
			servisKarata.saveTicket(karta);
			
		} catch (Exception e) {
			return new ResponseEntity<Long>(HttpStatus.CONFLICT);
		}
		
		servisKorisnik.saveUser(user);
		System.out.println(rezervation.getId());
		
		karta = servisKarata.findOneById(id);
		
		
		
		return new ResponseEntity<Long>(karta.getReservationTicket().getId(), HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value="/makeInvitations", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> makeInvitations(@RequestBody ArrayList<PozivnicaDTO> pozivnice,@Context HttpServletRequest request) throws ParseException, MessagingException		
	{
		
		if(pozivnice.size() > 0)
		{
			for(PozivnicaDTO pozivnicaDTO : pozivnice)
			{
				User korisnik = servisKorisnik.findOneById(pozivnicaDTO.getKorisnikID());
				Ticket ticket = servisKarata.findOneById(pozivnicaDTO.getTicketID());
				Pozivnica pozivnica = new Pozivnica();
				
				pozivnica.setDatum(new Date());
				pozivnica.setKorisnik(korisnik);
				pozivnica.setTicket(ticket);
				ticket.setPozivnica(pozivnica);
				korisnik.getPozivnice().add(pozivnica);
				
				servisMail.sendInvitationAsync(korisnik, pozivnica);
				
				servisKorisnik.saveUser(korisnik);
				
			}
		}
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/acceptInvitation/{id}", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> acceptInvitation(@PathVariable("id") Long id) throws ParseException		
	{
		Pozivnica pozivnica = servisPozivnica.findOneById(id);
		if(pozivnica == null)
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		
		Ticket ticket = pozivnica.getTicket();
		User korisnik = pozivnica.getKorisnik();
			
		ReservationTicket rezervacija = new ReservationTicket();
		rezervacija.setDatumRezervacije(new Date());
		
		rezervacija.setKorisnik(korisnik);
		rezervacija.setStatus(StatusRezervacije.AKTIVNA);
		korisnik.getResTicket().add(rezervacija);
		
		PassengerInfo info = new PassengerInfo();
		info.setIme(korisnik.getIme());
		info.setMail(korisnik.getMail());
		info.setPrezime(korisnik.getPrezime());
		
		ticket.setPassengerInfo(info);
		info.setKarta(ticket);
		ticket.setRezervisano(true);
		ticket.setReservationTicket(rezervacija);
		
		
		rezervacija.getKarte().add(ticket);
		
		try {
			servisKarata.saveTicket(ticket);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		korisnik.getPozivnice().remove(pozivnica);
		
		servisKorisnik.saveUser(korisnik);
	
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/declineInvitation/{id}", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> declineInvitation(@PathVariable("id") Long id) throws ParseException		
	{
		
		Pozivnica pozivnica = servisPozivnica.findOneById(id);
		if(pozivnica == null)
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		
		User korisnik = pozivnica.getKorisnik();
		korisnik.getPozivnice().remove(pozivnica);
		
		servisKorisnik.saveUser(korisnik);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/pozivnicaFlight/{id}", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FlightDTO> getFlightPozivnica(@PathVariable("id") Long id) throws ParseException		
	{
		
		Pozivnica pozivnica = servisPozivnica.findOneById(id);
		if(pozivnica == null)
			return new ResponseEntity<FlightDTO>(HttpStatus.BAD_REQUEST);
		
		
		Ticket karta = pozivnica.getTicket();
		
		Flight let = karta.getLet();
		
		FlightDTO retDto = new FlightDTO();
		retDto.setLokPoletanja(let.getPoletanje().getNaziv());
		retDto.setLokSletanja(let.getSletanje().getNaziv());
		
		SimpleDateFormat formatDatum = new SimpleDateFormat("yyyy-MM-dd");
		retDto.setDatumPoletanja(formatDatum.format(let.getDatumPoletanja()));
		retDto.setDatumSletanja(formatDatum.format(let.getDatumSletanja()));
		SimpleDateFormat formatVreme = new SimpleDateFormat("HH:mm");
		String vremePoletanja = formatVreme.format(let.getVremePoletanja());
		String vremeSletanja = formatVreme.format(let.getVremeSletanja());
		
		retDto.setVremePoletanja(vremePoletanja);
		retDto.setVremeSletanja(vremeSletanja);
		
		retDto.setCena(let.getCena());
		retDto.setKlasa(karta.getKlasa());
		
		
		return new ResponseEntity<FlightDTO>(retDto, HttpStatus.OK);
	}
	

	@RequestMapping(value="/oceniLet/{parametar}", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Flight oceniLet(@PathVariable String parametar){		
	  System.out.println("Usao u oceni let");
	 String[] niz = parametar.split("=");
	 Long id= Long.parseLong(niz[0]);
	 int ocena =Integer.parseInt(niz[1]);
	 Flight let =servis.findOneFlightById(id);
	    
	if(let!=null) {
			System.out.println("Brojac jee"+ let.getBrOcena());
				int brojOcena=let.getBrOcena();
				System.out.println("Broj ocena je "+brojOcena+ " trenutna ocena je "+let.getOcena());
				double ukOcena = let.getOcena()*brojOcena;
				System.out.println("Pomnozena ocena "+ukOcena);
				ukOcena = ukOcena+ocena;
				System.out.println("Dodata ocena "+ukOcena);
				brojOcena++;
				ukOcena=(double)ukOcena/brojOcena;
				System.out.println("Podeljena ocena je "+ukOcena);
				
				let.setBrOcena(brojOcena);
				let.setOcena(ukOcena);
				
				servis.saveFlight(let);
				
				return let;
		}else {
			return null;
		}
	
	}	

	
	@RequestMapping(value="/fastReservation/{id}/{idPopust}", 
			method = RequestMethod.POST)
	public ResponseEntity<Long> fastReservation(@PathVariable Long id,@PathVariable("idPopust") Long idPopust, @Context HttpServletRequest request) throws MessagingException
	{
		User user = (User) request.getSession().getAttribute("ulogovan");
		if(user == null)
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		
		DiscountTicket discount = servisPopusti.findOneById(idPopust);
		if(discount == null)
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		
		//ukoliko mi korisnik ima manje bodova od predvidjenih
		user = servisKorisnik.findOneById(user.getId());
		
		if(user.getBodovi() >= discount.getBodovi())
			user.setBodovi(user.getBodovi() - discount.getBodovi());
		else
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		
		ReservationTicket rezervation = new ReservationTicket();
		user = servisKorisnik.findOneById(user.getId());
		
		rezervation.setKorisnik(user);
		rezervation.setStatus(StatusRezervacije.AKTIVNA);
		user.getResTicket().add(rezervation);
		
		
		Date date = new Date();
		rezervation.setDatumRezervacije(date);
		
		Ticket karta = servisKarata.findOneById(id);
		PassengerInfo info = new PassengerInfo();
		info.setIme(user.getIme());
		info.setMail(user.getMail());
		info.setPrezime(user.getPrezime());
		karta.setPassengerInfo(info);
		info.setKarta(karta);
		karta.setRezervisano(true);
		rezervation.getKarte().add(karta);
		karta.setReservationTicket(rezervation);
		
		try {
			servisKarata.saveTicket(karta);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		
		servisKorisnik.saveUser(user);
		System.out.println(rezervation.getId());
		
		servisMail.sendReservationInfoAsync(user, rezervation, null, null);
		
		
		karta = servisKarata.findOneById(id);
		
		return new ResponseEntity<Long>(karta.getLet().getAvioKomp().getId(), HttpStatus.OK);
	}
	
	
	
	
	
	

	
	
}
