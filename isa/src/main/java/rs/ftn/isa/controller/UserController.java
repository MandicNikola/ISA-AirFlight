package rs.ftn.isa.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.dto.UserDTO;
import rs.ftn.isa.model.*;
import rs.ftn.isa.service.EmailService;
import rs.ftn.isa.service.UserService;

@RestController
@RequestMapping(value="api/korisnici")
public class UserController {
	

	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService servis;
	
	@Autowired
	private EmailService emailService;
	
	
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
	public @ResponseBody User registrujKorisnika(@RequestBody UserDTO novi){		
		
		System.out.println("Usao u registraciju, mail je "+ novi.getMail());
	
		//provera da li je mail jedinstven
		User provera = servis.findUserByMail(novi.getMail());
		
		
		if(provera == null) {	
			System.out.println("Provera je null");
		    User newUser= new User(novi.getIme(),novi.getPrezime(), novi.getMail(), novi.getTelefon(),novi.getGrad(),novi.getLozinka());
		    newUser.setVerifikovan("ne");
			servis.saveUser(newUser);
			
			System.out.println("Sacuvao korisnika");
			return newUser;
		}else {
			System.out.println("Null vratio");
			provera.setVerifikovan("null");
			return provera;
		}
	}	
	
	@RequestMapping(value="/verifikacija/{mail}",
				method = RequestMethod.GET)
	public String signUpAsync(@PathVariable String mail){

		User user = servis.findUserByMail(mail);
		//slanje emaila
		try {
			emailService.sendNotificaitionAsync(user);
		}catch( Exception e ){
			logger.info("Greska prilikom slanja emaila: " + e.getMessage());
		}

		return "success";
	}

}
