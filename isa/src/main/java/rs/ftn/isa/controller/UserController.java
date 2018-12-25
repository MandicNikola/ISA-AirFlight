package rs.ftn.isa.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.*;

import javax.ws.rs.core.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	public String enkriptuj(String sifra) throws NoSuchAlgorithmException {
		 MessageDigest md = MessageDigest.getInstance("SHA-256"); 
		  
		 //pretvori sifru  u bajte
         byte[] messageDigest = md.digest(sifra.getBytes()); 
         
         StringBuilder sb = new StringBuilder();
         for (byte b : messageDigest) {
             sb.append(String.format("%02x", b));
         }
         System.out.println(sb.toString());
		
		return "";
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

	@RequestMapping(value="/aktiviraj/{mail}",
				method = RequestMethod.GET)
	public String activateUser(@PathVariable String mail){
	
		User user = servis.findUserByMail(mail);
		//slanje emaila
		user.setVerifikovan("da");	
	    servis.removeUser(user.getId());
	    
	    servis.saveUser(user);
		//servis.verifikujKorisnika("da", mail);
		return "Verifikovali ste mail, mozete posetiti sajt.";
	}

	@RequestMapping(value="/logovanje",
					method = RequestMethod.GET,
					produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody User loginUser(@RequestParam String mail,@RequestParam String lozinka ,@Context HttpServletRequest request){
		
		User user = servis.findUserByMail(mail);
		
		if(user == null) {
			//nije pronadjen mail u bazi
			user = new User();
			user.setVerifikovan("null");
			return user;
		}
		
		if(user.getVerifikovan().equals("ne")) { //korisnik nije aktivirao profil
				user.setVerifikovan("aktivacija");
				return user;
		}
		
		
		if(!user.getLozinka().equals(lozinka)) {
			//moraju se poklapati unesena lozinka i lozinka od korisnika sa unetim mailom 
				user.setVerifikovan("");
				return user;
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("ulogovan", user);
		
		System.out.println("Ulogovan je korisnik "+ user.getIme());
		
		return user;
	}

}
