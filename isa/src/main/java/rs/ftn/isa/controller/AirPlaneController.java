package rs.ftn.isa.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Path;

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

import rs.ftn.isa.dto.AirplaneDTO;
import rs.ftn.isa.dto.PlaneDTO;
import rs.ftn.isa.dto.SeatDTO;
import rs.ftn.isa.dto.UslugaAvionDTO;
import rs.ftn.isa.model.AirPlane;
import rs.ftn.isa.model.AirplaneCompany;
import rs.ftn.isa.model.Seat;
import rs.ftn.isa.model.Segment;
import rs.ftn.isa.model.Ticket;
import rs.ftn.isa.model.UslugaAvion;
import rs.ftn.isa.model.Vehicle;
import rs.ftn.isa.service.AirPlaneServiceImpl;
import rs.ftn.isa.service.AirplaneServiceCompanyImpl;
import rs.ftn.isa.service.UslugaAvionService;

@RestController
@RequestMapping("api/avioni")
public class AirPlaneController {

	@Autowired
	AirPlaneServiceImpl servis;
	
	@Autowired
	AirplaneServiceCompanyImpl companyService;
	
	@Autowired
	UslugaAvionService servisUsluga;
	
	@RequestMapping(value="/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody AirPlane getPlaneById(@PathVariable Long id){	
		AirPlane povratna = servis.findAirPlaneById(id);
		System.out.println(" Nasao avion");
		return povratna;
	}
	
	@RequestMapping(value="/obrisiAvion/{id}", method = RequestMethod.POST)
	public String obrisiAvion(@PathVariable Long id){
		
		AirPlane plane = servis.findAirPlaneById(id);
		AirplaneCompany company = plane.getAirComp();
		
		company.getAvioni().remove(plane);
		companyService.saveAirplaneCompany(company);
		servis.removeAirPlane(id);
		
		
		return "uspesno";
	}
	
	@RequestMapping(value="/addNewPlane/{id}", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> addNewPlane(@RequestBody PlaneDTO airplane, @PathVariable Long id)
	{
		AirPlane planeNew = new AirPlane();
		planeNew.setNaziv(airplane.getNaziv());
		AirplaneCompany company = companyService.findAirplaneCompanyById(id);
		
		if(company == null)
			new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		planeNew.setAirComp(company);
		planeNew.setBiznis(airplane.getBiznis());
		planeNew.setEkonomska(airplane.getEkonomska());
		planeNew.setPrvaKlasa(airplane.getPrvaKlasa());
		planeNew.setKonfiguracija(airplane.getKonfiguracija());
		setConfigurationAndSeats(planeNew);
		
		company.getAvioni().add(planeNew);
		
		
		
		companyService.saveAirplaneCompany(company);
		return new ResponseEntity<Long>(id, HttpStatus.OK);
	}
	
	@RequestMapping(value="updatePlane/{id}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updatePlane(@PathVariable Long id, @RequestBody PlaneDTO airplane){	
		
		System.out.println(" Nasao avion");
		AirPlane avion = servis.findAirPlaneById(id);
		if(avion == null)
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		
		avion.setNaziv(airplane.getNaziv());
		String konfiguracijaNova = airplane.getKonfiguracija();
		String konfiguracijaStara = avion.getKonfiguracija();
		
		ArrayList<Seat> sedista = new ArrayList<Seat>();
		if(!konfiguracijaNova.equals(konfiguracijaStara))
		{
			avion.setKonfiguracija(konfiguracijaNova);
			for(Segment segment : avion.getSegmenti())
			{
				for(Seat seat : segment.getSeats())
				{
					sedista.add(seat);		
				}
				Collections.sort(sedista);
				setNewConfiguration(konfiguracijaNova, sedista);
				sedista = new ArrayList<Seat>();
				System.out.println("odradio");
			}
			
		}
		
		servis.saveAirPlane(avion);
		
		return new ResponseEntity<String>("uspesan update", HttpStatus.OK);
	}
	
	/*
	 * metoda sa kojom cu podesiti sedista u novoj konfiguraciji
	 */
	private void setNewConfiguration(String konfiguracija, ArrayList<Seat> seats)
	{
		String[] configuration = konfiguracija.split("-");
		
		int redovi = 0;
		for(int i = 0; i < configuration.length; i++)
		{
			int broj = Integer.parseInt(configuration[i]);
			redovi += broj;
		}
		int red = 0;
		for(int i = 0; i < seats.size(); i++)
		{
			int brojac = i % redovi;
			Seat sediste = seats.get(i);
			sediste.setRed(red);
			sediste.setKolona(brojac);
			
			if((brojac+1) == redovi)
				red++;
			
		}	
	}
	
	
	
	
	
	
	
	/*
	 * 	metoda za dodavanje nove usluge u avion
	 */
	@RequestMapping(value="addService/{id}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addService(@PathVariable Long id, @RequestBody UslugaAvionDTO usluga){	
		
		System.out.println(" Nasao avion");
		AirPlane avion = servis.findAirPlaneById(id);
		if(avion == null)
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		
		UslugaAvion uslugaNew = new UslugaAvion();
		uslugaNew.setNaziv(usluga.getNaziv());
		uslugaNew.setCena(usluga.getCena());
		uslugaNew.setKlasa(usluga.getKlasa());
		uslugaNew.setOpis(usluga.getOpis());
		
		Set<Segment> segmenti = avion.getSegmenti();
		for(Segment segment : segmenti)
		{
			if(segment.getNaziv().equals(uslugaNew.getKlasa()))
			{
				uslugaNew.setSegment(segment);
				segment.getUsluge().add(uslugaNew);
				break;
			}
		}
		
		servis.saveAirPlane(avion);
		
		return new ResponseEntity<String>("uspesan update", HttpStatus.OK);
	}
	
	
	
	private void setConfigurationAndSeats(AirPlane plane)
	{
		String[] configuration = plane.getKonfiguracija().split("-");
		
		int redovi = 0;
		for(int i = 0; i < configuration.length; i++)
		{
			int broj = Integer.parseInt(configuration[i]);
			redovi += broj;
		}
		Segment segmentBiznis = new Segment("biznis",redovi,3);
		Segment segmentFirst = new Segment("ekonomska",redovi,2);
		Segment segmentEconomic = new Segment("first",redovi,1);
		
		segmentBiznis.setPlane(plane);
		plane.getSegmenti().add(segmentBiznis);
		segmentFirst.setPlane(plane);
		plane.getSegmenti().add(segmentFirst);
		segmentEconomic.setPlane(plane);
		plane.getSegmenti().add(segmentEconomic);
		int red = 0;
		//podesavanja sedista u segmentima
		for(int i = 0; i < plane.getEkonomska(); i++)
		{
			int brojac = i % redovi;
			Seat sediste = new Seat(red, brojac, "ekonomska");
			sediste.setStatus("aktivno");
			segmentEconomic.getSeats().add(sediste);
			sediste.setSegment(segmentEconomic);
			
			if((brojac+1) == redovi)
				red++;
			
		}
		red = 0;
		for(int i = 0; i < plane.getBiznis(); i++)
		{
			int brojac = i % redovi;
			Seat sediste = new Seat(red, brojac, "biznis");
			sediste.setStatus("aktivno");
			segmentBiznis.getSeats().add(sediste);
			sediste.setSegment(segmentBiznis);
			
			if((brojac+1) == redovi)
				red++;
			
		}
		red = 0;
		for(int i = 0; i < plane.getPrvaKlasa(); i++)
		{
			int brojac = i % redovi;
			Seat sediste = new Seat(red, brojac, "first");
			sediste.setStatus("aktivno");
			segmentFirst.getSeats().add(sediste);
			sediste.setSegment(segmentFirst);
			
			if((brojac+1) == redovi)
				red++;
			
		}
		
		
	}
	
	@RequestMapping(value="/usluge/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UslugaAvionDTO>> getUsluge(@PathVariable Long id){	
		
		AirPlane avion = servis.findAirPlaneById(id);
		if(avion == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		
		ArrayList<UslugaAvionDTO> uslugaAvionDTOs = new ArrayList<UslugaAvionDTO>();
		for(Segment segment : avion.getSegmenti())
		{
			for(UslugaAvion usluga : segment.getUsluge())
			{
				UslugaAvionDTO dto = new UslugaAvionDTO(usluga);
				uslugaAvionDTOs.add(dto);
			}
		}
		
		return new ResponseEntity<List<UslugaAvionDTO>>(uslugaAvionDTOs, HttpStatus.OK);
	}
	
	@RequestMapping(value="/seats/{id}/{klasa}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<SeatDTO>> getSedistaKlasa(@PathVariable("id") Long id, @PathVariable("klasa") String klasa){	
		
		AirPlane avion = servis.findAirPlaneById(id);
		if(avion == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		Date currentDate = new Date();
		ArrayList<SeatDTO> retDTOs = new ArrayList<SeatDTO>();
		for(Segment segment : avion.getSegmenti())
		{
			if(segment.getNaziv().equals(klasa))
			{
				for(Seat seat : segment.getSeats())
				{
					SeatDTO dto = new SeatDTO(seat);
					dto.setKonfiguracija(avion.getKonfiguracija());
					dto.setStatus(seat.getStatus());
					retDTOs.add(dto);
					if(seat.getKarte().size() == 0)
					{
						dto.setRezervisano(false);
					}
					else
					{
						for(Ticket ticket : seat.getKarte())
						{
							if(ticket.isRezervisano() || ticket.getPopustiKarte().size()>0)
							{
								if(ticket.getLet().getDatumPoletanja().after(currentDate))
								{
									dto.setRezervisano(true);
									break;
								}
							}
						}
						dto.setRezervisano(false);
					}
				}
				break;
			}
		}
		//sortiram sedista radi lakseg iscrtavanja
		if(retDTOs.size() > 0)
			Collections.sort(retDTOs);
		
		return new ResponseEntity<List<SeatDTO>>(retDTOs, HttpStatus.OK);
	}
	
	
	//proveriti zasto mi post metoda ne vraca nista
	@RequestMapping(value="/obrisiUslugu/{id}",
			method = RequestMethod.POST)
	public ResponseEntity<String> obrisiUslugu(@PathVariable Long id){	
		
		UslugaAvion usluga = servisUsluga.findOneById(id);
		
		if(usluga == null)
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		
		Segment segment = usluga.getSegment();
		AirPlane avion = segment.getPlane();
		
		segment.getUsluge().remove(usluga);
		usluga.setSegment(null);
		servis.saveAirPlane(avion);
		servisUsluga.removeUslugaAvion(usluga);
		
		
		return new ResponseEntity<String>( HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/obrisiSediste/{id}/{klasa}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> obrisiSediste(@PathVariable("id") Long id,@PathVariable("klasa") String klasa,@RequestBody SeatDTO sediste){	
		
		AirPlane avion = servis.findAirPlaneById(id);
		
		Date currentDate = new Date();
		
		boolean dozvola = true;
		
		if(avion == null)
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		
		for(Segment segment : avion.getSegmenti())
		{
			if(segment.getNaziv().equals(klasa))
			{
				Set<Seat> sedista = segment.getSeats();
				for(Seat seat : sedista)
				{
					if(seat.getId().equals(sediste.getIdSedista()))
					{
						for(Ticket ticket : seat.getKarte())
						{
							if(ticket.isRezervisano())
							{
								if(ticket.getLet().getDatumPoletanja().after(currentDate))
								{
									dozvola = false;
									break;
								}
							}
						}
						if(dozvola)
							seat.setStatus("obrisano");
						break;
					}
				}
				break;
			}
		}
		servis.saveAirPlane(avion);
		
		
		return new ResponseEntity<String>("Sediste uspesno obrisano", HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/dodajSediste/{id}/{klasa}",
			method = RequestMethod.POST)
	public ResponseEntity<String> dodajSediste(@PathVariable("id") Long id,@PathVariable("klasa") String klasa){	
		
		AirPlane avion = servis.findAirPlaneById(id);
		
		if(avion == null)
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		
		for(Segment segment : avion.getSegmenti())
		{
			if(segment.getNaziv().equals(klasa))
			{
				Set<Seat> sedista = segment.getSeats();
				ArrayList<Seat> sedistaList = new ArrayList<Seat>();
				for(Seat seat : sedista)
				{
					sedistaList.add(seat);
				}
				
				Collections.sort(sedistaList);
				boolean dodato = false;
				
				for(Seat seat : sedistaList)
				{
					if(seat.getStatus().equals("obrisano"))
					{
						seat.setStatus("aktivno");
						dodato = true;
						break;
					}
				}
				if(!dodato)
				{
					Seat seatNew = new Seat();
					seatNew.setStatus("aktivno");
					
					sedistaList.add(seatNew);
					
					setNewConfiguration(avion.getKonfiguracija(), sedistaList);
				}
				break;
			}
		}
		servis.saveAirPlane(avion);
		
		
		return new ResponseEntity<String>("uspesno odradjeno dodavanje", HttpStatus.OK);
	}
	
	
	
	
	

	
	
}
