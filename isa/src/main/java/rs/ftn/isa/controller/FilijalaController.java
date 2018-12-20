package rs.ftn.isa.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.model.Filijala;
import rs.ftn.isa.model.RentACar;
import rs.ftn.isa.model.Vehicle;
import rs.ftn.isa.service.FilijalaServiceImpl;

@RestController
@RequestMapping(value="api/filijale")
public class FilijalaController {

	@Autowired
	private FilijalaServiceImpl servis;
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public List<Filijala> getAllFilijale(){		
		return  servis.findAll();
	}
	
	@RequestMapping(value="/registrovanje", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
public @ResponseBody Filijala registrujFilijalu( @RequestBody Filijala nova){		
	
	System.out.println("Usao u registrujFilijalu");

	ArrayList<Filijala> lista = (ArrayList<Filijala>) servis.findAllByUlica(nova.getUlica());
	boolean postoji = false;
	
	for(Filijala F : lista) {
		//ako postoje filijale sa istim nazivom ulice proveravamo da li se nalaze u istom gradu
		if(F.getGrad().equals(nova.getGrad())) {
			postoji=true;
			break;
		}
	}
	if(postoji) {
		//ulica+grad mora biti jedinstven
		return null;
	}
	
	return nova;
}	

	@RequestMapping(value="/deleteFilijalu/{id}", method = RequestMethod.POST)
	public  void obrisiFilijalu(@PathVariable String id){
			System.out.println("Usao u Delete filijalu dobio je "+id);
			
			
			servis.removeFilijala(Long.parseLong(id));
			
	}
		


}
