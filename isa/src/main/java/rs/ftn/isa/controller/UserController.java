package rs.ftn.isa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rs.ftn.isa.model.*;
import rs.ftn.isa.service.UserService;

@RestController
@RequestMapping(value="api/korisnici")
public class UserController {
	
	@Autowired
	private UserService servis;

	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public List<User> getAllKorisnici(){		
		return  servis.findAll();
	}
	
	@RequestMapping(value="/test")
	public String vrati() {
		
		return "Uspesno";
	}	
	
	@RequestMapping(value="/registracija", method = RequestMethod.POST)
	public User registrujKorisnika(User novi){		
		
		//provera da li je mail jedinstven
	User provera = servis.findKorisnikByMail(novi.getMail());
		
		if(provera!=null) {			
			servis.saveKorisnika(novi);
		}else {
			return null;
		}
		return novi;
	}
	
	
}
