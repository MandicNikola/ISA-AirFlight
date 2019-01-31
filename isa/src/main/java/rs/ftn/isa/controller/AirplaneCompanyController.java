package rs.ftn.isa.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.dto.AirplaneDTO;
import rs.ftn.isa.dto.DestinationDTO;
import rs.ftn.isa.dto.FlightDTO;
import rs.ftn.isa.dto.HotelDTO;
import rs.ftn.isa.model.AirPlane;
import rs.ftn.isa.model.AirplaneCompany;
import rs.ftn.isa.model.CijenovnikSoba;
import rs.ftn.isa.model.Destination;
import rs.ftn.isa.model.Flight;
import rs.ftn.isa.model.Hotel;
import rs.ftn.isa.model.RentACar;
import rs.ftn.isa.model.Room;
import rs.ftn.isa.model.Usluga;
import rs.ftn.isa.service.AirPlaneServiceImpl;
import rs.ftn.isa.service.AirplaneServiceCompanyImpl;
import rs.ftn.isa.service.DestinationService;
import rs.ftn.isa.service.DestinationServiceImp;
import rs.ftn.isa.service.FlightService;


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
		Destination destinationNew = new Destination();
		
		if(company == null)
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		
		destinationNew.setNaziv(destination.getNaziv());
		company.getDestinacije().add(destinationNew);
		destinationNew.setAvioKomp(company);
		
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
	public ResponseEntity<Long> addFlight(@RequestBody FlightDTO flight){		
		
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
		 
		 flightNew.setVremePoletanja(formirajDate(flight.getDatumPoletanja(), flight.getVremePoletanja()));
		 flightNew.setVremeSletanja(formirajDate(flight.getDatumSletanja(), flight.getVremeSletanja()));
		 
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
	
	
	@RequestMapping(value="/flight/{id}", 
			method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public Flight getFlight(@PathVariable Long id){		
		
		//System.out.println("usao u metodu koja mi treba");
		Flight flight = flightService.findOneFlightById(id);
		
		return flight;
			 
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
			
			Date datePoletanje = flight.getVremePoletanja();
			Date dateSletanje = flight.getVremeSletanja();
			
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatVreme = new SimpleDateFormat("HH:mm");
			String datumPoletanja = format.format(datePoletanje);
			String datumSletanja = format.format(dateSletanje);
			
			String vremePoletanja = formatVreme.format(datePoletanje);
			String vremeSletanja = formatVreme.format(dateSletanje);
		
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
		}
		
		return new ResponseEntity<Set<FlightDTO>>(retSet, HttpStatus.OK);
			 
	}
	
	
	

	
	
	

}
