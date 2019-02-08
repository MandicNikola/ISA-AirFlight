package rs.ftn.isa.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.dto.AirplaneDTO;
import rs.ftn.isa.dto.DestinationDTO;
import rs.ftn.isa.dto.FlightDTO;
import rs.ftn.isa.dto.HotelDTO;
import rs.ftn.isa.dto.TicketDTO;
import rs.ftn.isa.model.AirPlane;
import rs.ftn.isa.model.AirplaneCompany;
import rs.ftn.isa.model.CijenovnikSoba;
import rs.ftn.isa.model.Destination;
import rs.ftn.isa.model.DiscountTicket;
import rs.ftn.isa.model.Flight;
import rs.ftn.isa.model.Hotel;
import rs.ftn.isa.model.RentACar;
import rs.ftn.isa.model.Room;
import rs.ftn.isa.model.Seat;
import rs.ftn.isa.model.Segment;
import rs.ftn.isa.model.Ticket;
import rs.ftn.isa.model.User;
import rs.ftn.isa.model.Usluga;
import rs.ftn.isa.model.Vehicle;
import rs.ftn.isa.service.AirPlaneServiceImpl;
import rs.ftn.isa.service.AirplaneServiceCompanyImpl;
import rs.ftn.isa.service.DestinationService;
import rs.ftn.isa.service.DestinationServiceImp;
import rs.ftn.isa.service.FlightService;
import rs.ftn.isa.service.UserServiceImpl;


@RestController
@RequestMapping(value="api/kompanije")
public class AirplaneCompanyController {
	
	@Autowired
	private AirplaneServiceCompanyImpl service ;
	
	@Autowired 
	private FlightService flightService;
	
	@Autowired
	private AirPlaneServiceImpl planeService;
	
	@Autowired 
	private DestinationServiceImp destinationService;
	
	@Autowired 
	private UserServiceImpl korisnikServis;
	
	@RequestMapping(value="/novaAvioKompanija", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody AirplaneCompany newHotel(@RequestBody AirplaneDTO kompanija){		
	//jedinstven po nazivu
		 AirplaneCompany pom = service.findAirplaneCompanyByNaziv(kompanija.getNaziv());
		 if(pom == null) {
			AirplaneCompany avio = new AirplaneCompany(kompanija.getNaziv(),kompanija.getAdresa(),kompanija.getOpis());
			service.saveAirplaneCompany(avio);
			return avio; 
		 }else {
			 AirplaneCompany povratna = new AirplaneCompany(); 
			 return povratna;
			 
		 }
	}
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public List<AirplaneCompany> getAllRents(){		
		return  service.findAll();
	}
	//vraca avio kompanije sortirane po nekom kriterijumu
	@RequestMapping(value="/sort/{uslov}", method = RequestMethod.GET)
	public List<AirplaneCompany> getSortedAirCompanies(@PathVariable String uslov){		
		System.out.println("Uslov je "+uslov);
		
		List<AirplaneCompany> svi = service.findAll();
		List<AirplaneCompany> sortiranaLista=new ArrayList<AirplaneCompany>();
		
		if(uslov.equals("NameA")) {
			//sortiraj po nazivu od A-Z
			System.out.println("Sortiraj po imenu rastuce");
			Collections.sort(svi, AirplaneCompany.AirplaneNameComparator);
			for(AirplaneCompany A : svi) {
				System.out.println(A.getNaziv());
				sortiranaLista.add(A);
			}
			
		}else if(uslov.equals("NameD")) {
			System.out.println("Sortiraj po imenu opadajuce");
			//sortiraj po nazivu od Z-A
			Collections.sort(svi, AirplaneCompany.AirplaneNameComparator);
			for(int i=svi.size()-1; i>=0; i--) {
				System.out.println(svi.get(i).getNaziv());
				sortiranaLista.add(svi.get(i));
			}
			
		}else if(uslov.equals("CityA")) {
			//sortiraj po gradu od A-Z
			System.out.println("Sortiraj po gradu rastuce");

			Collections.sort(svi, AirplaneCompany.AirplaneCityComparator);
			for(AirplaneCompany A : svi) {
				System.out.println(A.getAdresa());
				sortiranaLista.add(A);
			}
		}else {
			//sortiraj po gradu od Z-A
			System.out.println("Sortiraj po gradu rastuce");

			Collections.sort(svi, AirplaneCompany.AirplaneCityComparator);
			for(int i=svi.size()-1; i>=0; i--) {
				System.out.println(svi.get(i).getAdresa());
				sortiranaLista.add(svi.get(i));
			}
		}
		
		
		return sortiranaLista;
	}
	
	@RequestMapping(value="/obrisiKompaniju/{id}", method = RequestMethod.POST)
	public  void obrisiKompaniju(@PathVariable Long id){
		System.out.println("brisanje hotel "+id);
		service.removeAirPlaneCompany(id);
	
	}
	 
	
	@RequestMapping(value = "/findById/{id}",
			method = RequestMethod.GET)
	public @ResponseBody AirplaneCompany findCompanyById(@PathVariable Long id)
	{
		System.out.println("find"  + id);
		
		AirplaneCompany pronadjeni = service.findAirplaneCompanyById(id);
			if(pronadjeni == null) {
				return pronadjeni;
			}else{
				return pronadjeni;
			}
	}
	
	@RequestMapping(value = "/airplanes/{id}",
			method = RequestMethod.GET)
	public ResponseEntity<Set<AirPlane>> getAirPlanes(@PathVariable Long id)
	{
		System.out.println("find"  + id);
		
		AirplaneCompany pronadjeni = service.findAirplaneCompanyById(id);
		if(pronadjeni == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		
		return new ResponseEntity<Set<AirPlane>>(pronadjeni.getAvioni(), HttpStatus.OK);
		
	}
	
	
	
	@RequestMapping(value="/addPlane/{id}", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public String addPlane(@RequestBody AirPlane plane,@PathVariable Long id){		
		
		AirplaneCompany company = service.findAirplaneCompanyById(id);
		 
		 if(company == null) {
	
			 System.out.println(" ne postoji avio kompanija ");
			 return "neuspesno";
		 }
		 
		 company.getAvioni().add(plane);
		 planeService.saveAirPlane(plane);

		 return "uspesno";
			 
	}
	
	@RequestMapping(value="/addDestination/{id}", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Long> addDestination(@RequestBody DestinationDTO destination,@PathVariable Long id){		
		
		//System.out.println("usao u metodu koja mi treba");
		AirplaneCompany company = service.findAirplaneCompanyById(id);
		
		
		Destination destinationNew = destinationService.findDestinationByName(destination.getNaziv());
		
		if(destinationNew == null)
		{
			destinationNew = new Destination();
			destinationNew.setNaziv(destination.getNaziv());
		}
		if(company == null)
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		
		company.getDestinacije().add(destinationNew);
		destinationNew.getAvioKomp().add(company);
		
		service.saveAirplaneCompany(company);
		
		return new ResponseEntity<Long>(id, HttpStatus.OK);
			 
	}
	
	@RequestMapping(value="/getDestinations/{id}", 
			method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<DestinationDTO>> getDestinations(@PathVariable Long id){		
		
		AirplaneCompany company = service.findAirplaneCompanyById(id);
		if(company == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		Set<DestinationDTO> destDtos = new HashSet<DestinationDTO>();
		for(Destination destination : company.getDestinacije())
		{
			DestinationDTO dto = new DestinationDTO();
			dto.setNaziv(destination.getNaziv());
			dto.setId(destination.getId());
			destDtos.add(dto);
		}
		
		return new ResponseEntity<Set<DestinationDTO>>(destDtos, HttpStatus.OK);
		
			 
	}
	
	
	
	@RequestMapping(value="/addFlight", 
			method = {RequestMethod.POST,RequestMethod.GET},
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> addFlight(@RequestBody FlightDTO flight) throws ParseException{		
		
		 AirPlane plane = planeService.findAirPlaneById(flight.getIdAviona());
		 AirplaneCompany company = plane.getAirComp();
		 
		 Flight flightNew = new Flight();
		 
		 //kupim sve destinacije koje su mi potrebne za let
		 Destination poletanje = destinationService.findDestinationById(flight.getLokacijaPoletanja());
		 Destination sletanje = destinationService.findDestinationById(flight.getLokacijaSletanja());
		 
		 flightNew.setPlane(plane);
		 plane.getLetovi().add(flightNew);
		 
		 flightNew.setAvioKomp(company);
		 company.getLetovi().add(flightNew);
		 
		 flightNew.setPoletanje(poletanje);
		 flightNew.setSletanje(sletanje);
		 poletanje.getPoletanja().add(flightNew);
		 sletanje.getSletanja().add(flightNew);
	 
		 //**kupim presedanja leta sva koja mi trebaju
		 
		 for(Long idDest : flight.getPresedanja())
		 {
			 Destination destinacijaPresedanje = destinationService.findDestinationById(idDest);
			 flightNew.getPresedanja().add(destinacijaPresedanje);
			 destinacijaPresedanje.getLetovi().add(flightNew); 
		 }
		 
		 flightNew.setDuzina(flight.getDuzina());
		 flightNew.setCena(flight.getCena());
		 flightNew.setVreme(flight.getVreme());
		 flightNew.setTip(flight.getTip());
		 
		 
		 SimpleDateFormat formatVreme = new SimpleDateFormat("HH:mm");
		 SimpleDateFormat formatDatum = new SimpleDateFormat("yyyy-MM-dd");
		 
		 Date vremePoletanja = formatVreme.parse(flight.getVremePoletanja());
		 Date vremeSletanja = formatVreme.parse(flight.getVremeSletanja());
		 Date datumPoletanja = formatDatum.parse(flight.getDatumPoletanja());
		 Date datumSletanja = formatDatum.parse(flight.getDatumSletanja());
		 
		 flightNew.setVremePoletanja(vremePoletanja);
		 flightNew.setVremeSletanja(vremeSletanja);
		 flightNew.setDatumPoletanja(datumPoletanja);
		 flightNew.setDatumSletanja(datumSletanja);
		 
		 
		 if(!(flight.getDatumPovratka().equals("nema") || flight.getDatumPovratka().isEmpty() || flight.getDatumPovratka() == null))
		 { 
			 Date datumPovratka = formatDatum.parse(flight.getDatumPovratka());
			 flightNew.setVremePovratka(datumPovratka);
		 }
		 
		 formirajKarte(plane, flightNew);
		 
		 service.saveAirplaneCompany(company);
		
		 return new ResponseEntity<Long>(company.getId(), HttpStatus.OK);
			 
	}
	
	
	@SuppressWarnings("deprecation")
	public Date formirajDate(String date, String time)
	{
		String[] podaci = date.split("-");
		
		 int godina=Integer.parseInt(podaci[0]);
		 int mesec=Integer.parseInt(podaci[1])-1;
		 int dan=Integer.parseInt(podaci[2]);
		
		 String[] vreme = time.split(":");
		 
		 int hours = Integer.parseInt(vreme[0]);
		 int minutes = Integer.parseInt(vreme[1]);
		 
		 return new Date(godina-1900, mesec, dan, hours, minutes);
		 
	}
	
	public void formirajKarte(AirPlane plane, Flight flight)
	{
		for(Segment segment : plane.getSegmenti())
		{
			for(Seat sediste : segment.getSeats())
			{
				Ticket ticket = new Ticket(false,sediste.getKlasa());
				ticket.setSediste(sediste);
				sediste.getKarte().add(ticket);
				flight.getKarte().add(ticket);
				ticket.setLet(flight);
				
			}
			
		}
		
	}
	
	
	@RequestMapping(value="/flight/{id}", 
			method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public Flight getFlight(@PathVariable Long id){		
		
		//System.out.println("usao u metodu koja mi treba");
		Flight flight = flightService.findOneFlightById(id);
		
		return flight;
			 
	}
	
	@RequestMapping(value="/tickets/{id}", 
			method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<List<TicketDTO>> getTickets(@PathVariable Long id) throws ParseException{		
		
		AirplaneCompany company = service.findAirplaneCompanyById(id);
		
		if(company == null)
			return new ResponseEntity<List<TicketDTO>>(HttpStatus.BAD_REQUEST);
		
		
		Date currentDate = new Date();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		
		String dateStr = formater.format(currentDate);
		currentDate = formater.parse(dateStr);
		
		ArrayList<TicketDTO> retTicketDtos = new ArrayList<TicketDTO>();
		for(Flight let : company.getLetovi())
		{
			if(let.getDatumPoletanja().after(currentDate))
			{
				for(Ticket ticket : let.getKarte())
				{
					if(!ticket.isRezervisano())
					{
						TicketDTO dto = new TicketDTO(ticket);
						dto.setDatumPoletanja(formater.format(let.getDatumPoletanja()));
						dto.setLokPoletanja(let.getPoletanje().getNaziv());
						dto.setLokSletanja(let.getSletanje().getNaziv());
						dto.setCena(ticket.getCena());
						
						System.out.println("ispis");
						
						retTicketDtos.add(dto);
					}
				}
			}
		}
		Collections.sort(retTicketDtos);
		
		
		return new ResponseEntity<List<TicketDTO>>(retTicketDtos, HttpStatus.OK);
	}
	
	
	
	
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/flights/{id}", 
			method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<FlightDTO>> getFlights(@PathVariable Long id){		
		
		//System.out.println("usao u metodu koja mi treba");
		AirplaneCompany company = service.findAirplaneCompanyById(id);
		
		if(company == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		Set<FlightDTO> retSet = new HashSet<FlightDTO>();
		
		for(Flight flight : company.getLetovi())
		{
			FlightDTO flightDto = new FlightDTO();
			
			flightDto.setIdLeta(flight.getId());
			flightDto.setCena(flight.getCena());
			flightDto.setAvion(flight.getPlane().getNaziv());
			flightDto.setDuzina(flight.getDuzina());
			flightDto.setLokPoletanja(flight.getPoletanje().getNaziv());
			flightDto.setLokSletanja(flight.getSletanje().getNaziv());
			
			Date vrPoletanje = flight.getVremePoletanja();
			Date vrSletanje = flight.getVremeSletanja();
			
			Date datePoletanje = flight.getDatumPoletanja();
			Date dateSletanje = flight.getDatumSletanja();
			
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatVreme = new SimpleDateFormat("HH:mm");
			String datumPoletanja = format.format(datePoletanje);
			String datumSletanja = format.format(dateSletanje);
			
			String vremePoletanja = formatVreme.format(vrPoletanje);
			String vremeSletanja = formatVreme.format(vrSletanje);
		
			flightDto.setDatumPoletanja(datumPoletanja);
			flightDto.setDatumSletanja(datumSletanja);
			flightDto.setVremePoletanja(vremePoletanja);
			flightDto.setVremeSletanja(vremeSletanja);
			
			if(!flight.getPresedanja().isEmpty())
			{
				for(Destination destination : flight.getPresedanja())
				{
					flightDto.getPresedanja().add(destination.getId());
				}
			}
			retSet.add(flightDto);
		}
		
		return new ResponseEntity<Set<FlightDTO>>(retSet, HttpStatus.OK);
			 
	}
	
	
	

	@RequestMapping(value="/oceniKompaniju/{parametar}", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody AirplaneCompany oceniKompaniju(@PathVariable String parametar){		
	  System.out.println("Usao u oceni avion");
	 String[] niz = parametar.split("=");
	 Long id= Long.parseLong(niz[0]);
	 int ocena =Integer.parseInt(niz[1]);
	 AirplaneCompany kompanija =service.findAirplaneCompanyById(id);
	    
	if(kompanija!=null) {
			System.out.println("Brojac jee"+ kompanija.getBrojac());
				int brojOcena=kompanija.getBrojac();
				System.out.println("Broj ocena je "+brojOcena+ " trenutna ocena je "+kompanija.getOcena());
				double ukOcena = kompanija.getOcena()*brojOcena;
				System.out.println("Pomnozena ocena "+ukOcena);
				ukOcena = ukOcena+ocena;
				System.out.println("Dodata ocena "+ukOcena);
				brojOcena++;
				ukOcena=(double)ukOcena/brojOcena;
				System.out.println("Podeljena ocena je "+ukOcena);
				
				kompanija.setBrojac(brojOcena);
				kompanija.setOcena(ukOcena);
				
				service.saveAirplaneCompany(kompanija);
				
				return kompanija;
		}else {
			return null;
		}
	
	}	

	@RequestMapping(value="/fastTickets/{id}", 
			method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<List<TicketDTO>> getFastTickets(@PathVariable Long id, @Context HttpServletRequest request) throws ParseException{		
		
		AirplaneCompany company = service.findAirplaneCompanyById(id);
		
		if(company == null)
			return new ResponseEntity<List<TicketDTO>>(HttpStatus.BAD_REQUEST);
		
		User user = (User) request.getSession().getAttribute("ulogovan");
		if(user == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		user = korisnikServis.findOneById(user.getId());
		
		if(user == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		int bodovi = user.getBodovi();
		
		Date currentDate = new Date();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		
		String dateStr = formater.format(currentDate);
		currentDate = formater.parse(dateStr);
		
		ArrayList<TicketDTO> retTicketDtos = new ArrayList<TicketDTO>();
		for(Flight let : company.getLetovi())
		{
			if(let.getDatumPoletanja().after(currentDate))
			{
				for(Ticket ticket : let.getKarte())
				{
					if(!ticket.isRezervisano() && ticket.getPopustiKarte().size() > 0)
					{
						System.out.println("usao");
						TicketDTO dto = new TicketDTO(ticket);
						dto.setDatumPoletanja(formater.format(let.getDatumPoletanja()));
						dto.setLokPoletanja(let.getPoletanje().getNaziv());
						dto.setLokSletanja(let.getSletanje().getNaziv());
						dto.setCena(ticket.getCena());
						
						podesiPopust(ticket.getPopustiKarte(), dto, bodovi);
						
						retTicketDtos.add(dto);
					}
				}
			}
		}
		Collections.sort(retTicketDtos);
		
		
		return new ResponseEntity<List<TicketDTO>>(retTicketDtos, HttpStatus.OK);
	}
	
	private void podesiPopust(Set<DiscountTicket> popusti, TicketDTO dto,int brojBodova)
	{
		
		ArrayList<Integer> brojevi = new ArrayList<Integer>();
		HashMap<Integer, DiscountTicket> mapa = new HashMap<Integer, DiscountTicket>();
		for(DiscountTicket popust : popusti)
		{
			brojevi.add(popust.getBodovi());
			mapa.put(popust.getBodovi(), popust);
		}
		
		boolean pronasao = false;
		Collections.sort(brojevi); 
		
		
		int trenutni = 0;
		for(int i : brojevi)
		{
			System.out.println(i);
			if(i <= brojBodova)
				trenutni = i;
		}
		
		dto.setIdPopusta(mapa.get(trenutni).getId());
		dto.setPopust(mapa.get(trenutni).getVrednost());
	}
	
	
}
