package rs.ftn.isa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.model.PricelistRentCar;
import rs.ftn.isa.model.Vehicle;
import rs.ftn.isa.service.RentACarService;
import rs.ftn.isa.service.UslugaServiceImpl;

@RestController
@RequestMapping(value="api/usluge")
public class UslugaController {

	@Autowired
	UslugaServiceImpl servis;
	
	
	
	@RequestMapping(value="/dodajUslugu", 
	method = RequestMethod.POST,
	produces = MediaType.APPLICATION_JSON_VALUE)
public @ResponseBody PricelistRentCar dodajUslugu( @PathVariable String pomocna){		
	
	String [] niz=pomocna.split("=");
	
	String id= niz[0];
	String naziv = niz[1];
	String cenaA= niz[2];
	String cenaB= niz[3];
	String cenaC= niz[4];
	String cenaD= niz[5];
	String cenaE= niz[6];
	
	
	
	return null;

}	


	
	
	
}
