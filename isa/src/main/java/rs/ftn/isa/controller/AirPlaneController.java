package rs.ftn.isa.controller;

import java.util.ArrayList;
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
import rs.ftn.isa.dto.UslugaAvionDTO;
import rs.ftn.isa.model.AirPlane;
import rs.ftn.isa.model.AirplaneCompany;
import rs.ftn.isa.model.Seat;
import rs.ftn.isa.model.Segment;
import rs.ftn.isa.model.UslugaAvion;
import rs.ftn.isa.model.Vehicle;
import rs.ftn.isa.service.AirPlaneServiceImpl;
import rs.ftn.isa.service.AirplaneServiceCompanyImpl;

@RestController
@RequestMapping("api/avioni")
public class AirPlaneController {

	@Autowired
	AirPlaneServiceImpl servis;
	
	@Autowired
	AirplaneServiceCompanyImpl companyService;
	
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
		
		if(!konfiguracijaNova.equals(konfiguracijaStara))
		{
			
		}
		
		servis.saveAirPlane(avion);
		
		return new ResponseEntity<String>("uspesan update", HttpStatus.OK);
	}
	
	/*
	 * metoda sa kojom cu podesiti sedista u novoj konfiguraciji
	 */
	private void setNewConfiguration()
	{
		
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
			if(segment.getClass().equals(uslugaNew.getKlasa()))
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
