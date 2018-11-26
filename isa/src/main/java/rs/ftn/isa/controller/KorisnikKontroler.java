package rs.ftn.isa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rs.ftn.isa.model.*;
import rs.ftn.isa.service.KorisnikServis;

@RestController
@RequestMapping(value="api/korisnici")
public class KorisnikKontroler {
	
	@Autowired
	private KorisnikServis servis;
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public List<Korisnik> getAllKorisnici(){		
		return  servis.findAll();
	}
	
	@RequestMapping(value="/test")
	public String vrati() {
		
		return "Uspesno";
	}	
}
