package rs.ftn.isa.controller;

import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.dto.AirplaneDTO;
import rs.ftn.isa.model.AirPlane;
import rs.ftn.isa.model.AirplaneCompany;
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
	public String addNewPlane(@RequestBody AirplaneDTO airplane, @PathVariable Long id)
	{
		AirPlane planeNew = new AirPlane();
		planeNew.setNaziv(airplane.getNaziv());
		AirplaneCompany company = companyService.findAirplaneCompanyById(id);
		
		if(company == null)
			return "neuspesno";
		
		planeNew.setAirComp(company);
		company.getAvioni().add(planeNew);
		companyService.saveAirplaneCompany(company);
		servis.saveAirPlane(planeNew);
		
		return "uspesno";
	}
	
	
	
	
	
	
	
	
	
	
}
