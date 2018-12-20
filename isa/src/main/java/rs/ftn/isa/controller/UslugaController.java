package rs.ftn.isa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.model.PricelistRentCar;
import rs.ftn.isa.model.RentACar;
import rs.ftn.isa.model.Usluga;
import rs.ftn.isa.model.Vehicle;
import rs.ftn.isa.service.RentACarService;
import rs.ftn.isa.service.UslugaServiceImpl;

@RestController
@RequestMapping(value="api/usluge")
public class UslugaController {

	@Autowired
	UslugaServiceImpl servis;
	
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public List<Usluga> getAllUsluge(){		
		return  servis.findAll();
	}
	
	@RequestMapping(value="/izbrisidodatnu/{pom}", method = RequestMethod.POST)
	public void getAllUsluge(@PathVariable String pom){		
		  servis.removeUsluga(Long.parseLong(pom));
	}
	
}
