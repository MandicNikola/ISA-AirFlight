package rs.ftn.isa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
	
	@RequestMapping(value="/registracija", 
					method = RequestMethod.POST,
					consumes = MediaType.APPLICATION_JSON_VALUE,
					produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody User registrujKorisnika(@RequestBody User novi){		
		
		System.out.println("Usao u registraciju, mail je "+ novi.getMail());
	
		//provera da li je mail jedinstven
		User provera = servis.findUserByMail(novi.getMail());
		
		if(provera==null) {	
			System.out.println("Provera je null");
			servis.saveUser(novi);
			System.out.println("Sacuvao korisnika");
		}else {
			System.out.println("Null vratio");
			return null;
		}
		return novi;
	}
	
	
}
