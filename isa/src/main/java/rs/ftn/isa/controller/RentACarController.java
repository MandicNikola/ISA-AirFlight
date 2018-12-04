package rs.ftn.isa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.dto.RentACarDTO;
import rs.ftn.isa.model.RentACar;
import rs.ftn.isa.service.RentACarServiceImpl;

@RestController
@RequestMapping(value="api/rents")
public class RentACarController {
	@Autowired 
	private RentACarServiceImpl servis;
	
	
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public List<RentACar> getAllKorisnici(){		
		return  servis.findAll();
	}
	
	@RequestMapping(value="/newrentacar",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody RentACar newRentACar(@RequestBody RentACarDTO newRent) {
		RentACar rent = servis.findOneByNaziv(newRent.getNaziv());
		if(rent == null) {
			RentACar novi = new RentACar(newRent.getNaziv(),newRent.getAdresa(),newRent.getOpis());
			servis.saveRentACar(novi);
			return novi;
		}else {
			RentACar pom = new RentACar();
			return pom;
		}
	}	
		
	
	
}
