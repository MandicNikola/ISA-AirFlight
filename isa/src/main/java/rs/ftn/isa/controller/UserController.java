package rs.ftn.isa.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import rs.ftn.isa.service.RelationService;
import rs.ftn.isa.service.UserService;
import rs.ftn.isa.model.Role;

@RestController
@RequestMapping(value="api/korisnici")
public class UserController {
	

	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService servis;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private RelationService relationService;
	
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public List<User> getAllKorisnici(){		
		return  servis.findAll();
	}
	

	@RequestMapping(value="/vratiAdmineSistema", method = RequestMethod.GET)
	public ArrayList<User> getAllAdminSistem(){		
		List<User> korisnici = servis.findAll();
		ArrayList<User> admini = new ArrayList<User>();
		for(User user:korisnici) {
				if(user.getTip()==(Role.ADMIN_SISTEM)) {
					admini.add(user);

					System.out.println("usao" + user.getPrezime());
				}
			}
		return admini;
	}
	
	@RequestMapping(value="/user", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody User getUser(@Context HttpServletRequest request){		
		
		User user = (User) request.getSession().getAttribute("ulogovan");
		
		return user;
	}
	
	
	@RequestMapping(value="/friends", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<String> getUserFriends(@Context HttpServletRequest request){		
		
		User user = (User) request.getSession().getAttribute("ulogovan");
		
		if(user == null)
			return null;
		ArrayList<String> retVal = new ArrayList<String>();
		Set<Relation> relations = user.getRelatingRel();
		if(relations.size() > 0)
		{
			
			
			for(Relation relation : relations)
			{
				if(relation.getTip().equals("FRIENDS") || relation.getTip().equals("ZAHTEV"))
				{
					String friendName = relation.getRelated().getIme();
					String friendLastName = relation.getRelated().getPrezime();
					Long friendID = relation.getRelated().getId();					
					retVal.add(friendName+"-"+friendLastName+"-"+friendID+"-"+relation.getTip()+"-"+relation.getId());				
				}
			}
			return retVal;
		}
		
		return retVal;
	}
	
	@RequestMapping(value="/requests", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<String> getUserRequests(@Context HttpServletRequest request){		
		
		User user = (User) request.getSession().getAttribute("ulogovan");
		
		if(user == null)
			return null;
		
		Set<Relation> relations = user.getRelatedRel();
		if(relations.size() > 0)
		{
			ArrayList<String> retVal = new ArrayList<String>();
			
			for(Relation relation : relations)
			{
				if( relation.getTip().equals("ZAHTEV"))
				{
					String friendName = relation.getRelated().getIme();
					String friendLastName = relation.getRelated().getPrezime();
					Long friendID = relation.getRelated().getId();					
					retVal.add(friendName+"-"+friendLastName+"-"+friendID+"-"+relation.getTip()+"-"+relation.getId());				
				}
			}
			return retVal;
		}
		
		return null;
	}
	/*
	 * Prvo saljem ime pa onda saljem prezime
	 * 
	 */
	@RequestMapping(value="/search/{path}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> search(@PathVariable("path") String path,@Context HttpServletRequest request){		
		
		
		String[] tokens = path.split("-");
		
		String name = tokens[0];
		String lastName = tokens[1];
		
		User user = (User) request.getSession().getAttribute("ulogovan");
		
		if(user == null)
			return null;
		
		if(name.equals("nothing"))
			name = "%%";
		else
			name = "%"+name+"%";
		
		if(lastName.equals("nothing"))
			lastName = "%%";
		else
			lastName = "%"+lastName+"%";
		
		//korisnici koji su mi rezultati pretrage koja mi treba
		List<User> korisnici = servis.findUserByImeAndPrz(name, lastName);
		
		return korisnici;
	}
	
	@RequestMapping(value="/changeInfo", method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public String changeProfileInfo(@RequestBody User novi,@Context HttpServletRequest request){		
		
		
		
		User user = (User) request.getSession().getAttribute("ulogovan");
		if(!user.getMail().equals(novi.getMail())) {
			User provera = servis.findUserByMail(novi.getMail());
			
			if(provera != null) {
				System.out.println("Mejl nije jedinstven");
				provera.setVerifikovan("null");
				return "Mejl nije jedinstven";
		
			}
			user.setMail(novi.getMail());
		}
		
			if(!user.getIme().equals(novi.getIme())) {
				user.setIme(novi.getIme());
			}
			if(!user.getPrezime().equals(novi.getPrezime())) {
				user.setPrezime(novi.getPrezime());
			}
			
			if(user.getTelefon() != novi.getTelefon()) {
				user.setTelefon(novi.getTelefon());
			}
			if(!user.getGrad().equals(novi.getGrad())) {
				user.setGrad(novi.getGrad());	
				
			}
			
		    servis.saveUser(user);
		    
		    
		    return "uspesno";
		    
	}
	
	
	@RequestMapping(value="/accept/{id}", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public String acceptFriend(@Context HttpServletRequest request,@PathVariable Long id){		
		
		User user = (User) request.getSession().getAttribute("ulogovan");
		if(user == null)
			return "neuspesno";
		
		
		Relation relacija = relationService.findOneById(id);
		if(relacija == null)
			return "nesupesno";
		
		relacija.setTip("FRIENDS");
		relationService.saveRelation(relacija);
		

		return "uspesno";
	}
	
	
	/*
		isti metod kao i za odbijanje zahteva
	 */
	@RequestMapping(value="/remove/{id}", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public String removeFriend(@Context HttpServletRequest request,@PathVariable Long id){		
		
		User user = (User) request.getSession().getAttribute("ulogovan");
		if(user == null)
			return "neuspesno";
		
		relationService.deleteRelation(id);
		

		return "uspesno";
	}
	
	@RequestMapping(value="/add/{id}", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public String addFriend(@Context HttpServletRequest request,@PathVariable Long id){		
		
		User user = (User) request.getSession().getAttribute("ulogovan");
		if(user == null)
			return "neuspesno";
		
		User userFriend = servis.findOneById(id);
		
		Relation relation = new Relation();
		relation.setRelated(userFriend);
		relation.setRelating(user);
		relation.setTip("ZAHTEV");
		
		user.getRelatingRel().add(relation);
		userFriend.getRelatedRel().add(relation);
		
		servis.saveUser(user);
		servis.saveUser(userFriend);
		relationService.saveRelation(relation);
		

		return "uspesno";
	}
	
	
	
	@RequestMapping(value="/getUsersForSistem", method = RequestMethod.GET)
	public ArrayList<User> getUsersForSistem(){		
		List<User> korisnici = servis.findAll();
		ArrayList<User> admini = new ArrayList<User>();
		for(User user:korisnici) {
				if(user.getTip()==(Role.REGISTROVAN)) {
					System.out.println("usao" + user.getPrezime());
					admini.add(user);
				}
			}
		if(admini.size() == 0) {
			return new ArrayList<User>();
		}
		return admini;
	}
	@RequestMapping(value="/getAdminsHotel/{id}", method = RequestMethod.GET)
	public ArrayList<User> getAdminsOfHotel(@PathVariable Long id){
		System.out.println("dosao u get admine hotela id hotela "+id);
		String idString = id.toString();
		List<User> korisnici = servis.findAll();
		ArrayList<User> admini = new ArrayList<User>();
		for(User user:korisnici) {
				if(user.getTip()==Role.ADMIN_HOTEL) {
					String servis = user.getServis().toString();
					if(servis.equals(idString)) {
						System.out.println("dosao po "+user.getIme());
						admini.add(user);
					}
				}
			}
		if(admini.size() == 0) {
			return new ArrayList<User>();
		}
		return admini;
	}

	@RequestMapping(value="/getAdminsRent/{id}", method = RequestMethod.GET)
	public ArrayList<User> getAdminsOfRents(@PathVariable Long id){
		System.out.println("dosao u get admine renta id servisa "+id);
		String idString = id.toString();
		List<User> korisnici = servis.findAll();
		ArrayList<User> admini = new ArrayList<User>();
		for(User user:korisnici) {
				if(user.getTip()==Role.ADMIN_RENT) {
					String servis = user.getServis().toString();
					if(servis.equals(idString)) {
						System.out.println("dosao po "+user.getIme());
						admini.add(user);
					}
				}
			}
		if(admini.size() == 0) {
			return new ArrayList<User>();
		}
		return admini;
	}

	
	@RequestMapping(value="/newAdminSistem/{id}", method = RequestMethod.POST)
	public  void noviAdminSistema(@PathVariable Long id){
		User korisnik = servis.findOneById(id);
		korisnik.setTip(Role.ADMIN_SISTEM);
		korisnik.setAdminPotvrdio(false);
		servis.saveUser(korisnik);
		System.out.println("sacuvan korisnik kao admin sistema");
	}
	
	@RequestMapping(value="/newAdminHotel/{pomocna}", method = RequestMethod.POST)
	public  void noviAdminHotela(@PathVariable String pomocna){
		System.out.println("usao u izmjeni admina hotela dobio "+pomocna);
		String[] pom = pomocna.split("-");
		String userID = pom[0];
		String hotelID = pom[1];
		Long userid = Long.parseLong(userID);
		Long hotelid = Long.parseLong(hotelID);
		User korisnik = servis.findOneById(userid);
		korisnik.setTip(Role.ADMIN_HOTEL);
		korisnik.setServis(hotelid);
		korisnik.setAdminPotvrdio(false);
		servis.saveUser(korisnik);
		System.out.println("sacuvan korisnik kao admin hotela");
	}
	
	@RequestMapping(value="/newAdminRent/{pomocna}", method = RequestMethod.POST)
	public  void noviAdminRenta(@PathVariable String pomocna){
		System.out.println("usao u izmjeni admina renta dobio "+pomocna);
		String[] pom = pomocna.split("-");
		String userID = pom[0];
		String rentID = pom[1];
		Long userid = Long.parseLong(userID);
		Long rentid = Long.parseLong(rentID);
		User korisnik = servis.findOneById(userid );
		korisnik.setTip(Role.ADMIN_RENT);
		korisnik.setServis(rentid);
		korisnik.setAdminPotvrdio(false);
		servis.saveUser(korisnik);
		System.out.println("sacuvan korisnik kao admin renta");
	}
	
	
	@RequestMapping(value="/removeAdminHotel/{id}", method = RequestMethod.POST)
	public  void removeAdminHotel(@PathVariable Long id){
		User korisnik = servis.findOneById(id);
		korisnik.setTip(Role.REGISTROVAN);	
		korisnik.setServis(0L);
		servis.saveUser(korisnik);
		System.out.println("sacuvan korisnik kao registrovan");
	}
	
	
	@RequestMapping(value="/removeAdminRent/{id}", method = RequestMethod.POST)
	public  void removeAdminRent(@PathVariable Long id){
		User korisnik = servis.findOneById(id);
		korisnik.setTip(Role.REGISTROVAN);	
		korisnik.setServis(0L);
		servis.saveUser(korisnik);
		System.out.println("sacuvan korisnik kao registrovan");
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
			String password="";
		    try {
				 password = enkriptuj(novi.getLozinka());
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				password=novi.getLozinka();
				e.printStackTrace();
			}
		    
			User newUser= new User(novi.getIme(),novi.getPrezime(), novi.getMail(), novi.getTelefon(),novi.getGrad(),password);
		    
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
		System.out.println("Usao da enkriptuje sifru " + sifra );
		MessageDigest md = MessageDigest.getInstance("SHA-256"); 
		  
		 //pretvori sifru  u bajte
         byte[] messageDigest = md.digest(sifra.getBytes()); 
         
         StringBuilder sb = new StringBuilder();
         for (byte b : messageDigest) {
             sb.append(String.format("%02x", b));
         }
        String povratna=sb.toString();
        System.out.println("Rezultat enkripcije je "+povratna);
    	
        return povratna;
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

	@RequestMapping(value="/logout",
			method = RequestMethod.POST)
	public User odjava(@Context HttpServletRequest request){
			
		User korisnik = (User)request.getSession().getAttribute("ulogovan");		
		
		System.out.println("Usao u funkciju logour");
		
		request.getSession().invalidate();
		if(korisnik == null) {
			return null;
		}
		
		return korisnik;
	
	
	}

	@RequestMapping(value="/aktiviraj/{mail}",
				method = RequestMethod.GET)
	public String activateUser(@PathVariable String mail){
	
		User user = servis.findUserByMail(mail);
		//slanje emaila
		user.setVerifikovan("da");	
		user.setTip(Role.REGISTROVAN);
	    //servis.removeUser(user.getId());
	    
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
		String sifra = lozinka;
		
		try {
			sifra = enkriptuj(lozinka);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!user.getLozinka().equals(sifra)) {
			//moraju se poklapati unesena lozinka i lozinka od korisnika sa unetim mailom 
				user.setVerifikovan("");
				return user;
		}
		
		System.out.println("Uspesno logovanje -> uneta loznika je "+lozinka);
		System.out.println("Enktiptovana lozinka je "+sifra);
		
		request.getSession().setAttribute("ulogovan", user);
		
		System.out.println("Ulogovan je korisnik "+ user.getIme());
		
		return user;
	}

	@RequestMapping(value="/dodajRez", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody User dodajRezervaciju(@RequestBody User korisnik ){		
	System.out.println("Usao u sacuvaj korisnika");
	System.out.println("Id je "+korisnik.getId());
	System.out.println("Ispis korisnika je "+korisnik);
	User kor=	servis.saveUser(korisnik);
		System.out.println("vratio je sacuvanog");
		return kor;
	}
	
	@RequestMapping(value="/addRezSobe", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody User dodajRezSobe(@RequestBody RezervacijaHotel rez ){		
		System.out.println("Usao u sacuvaj korisnika pri rez sobe"+rez.getUserHotel().getId());
		User korisnik = rez.getUserHotel();
		User kor=	servis.saveUser(korisnik);
		System.out.println("vratio je sacuvanog");
	return kor;
	}
	@RequestMapping(value="/istorijaHotela",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<RezervacijaHotel> getHistoryHotel(@Context HttpServletRequest request){		
	System.out.println("Usao u getHistoryHotel");
		ArrayList<RezervacijaHotel> rezervacije = new ArrayList<RezervacijaHotel>();
		User korisnik = (User)request.getSession().getAttribute("ulogovan");		
		if(korisnik!=null) {
				for(RezervacijaHotel rezervacija:korisnik.getRezHotela()) {
						System.out.println("Dodata rezervacija sa check in"+rezervacija.getDatumDolaska());
						rezervacije.add(rezervacija);
					}
		}
		return rezervacije;
	}
	@RequestMapping(value="/istorijaRent",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<RezervacijaRentCar> getHistoryRent(@Context HttpServletRequest request){		
		ArrayList<RezervacijaRentCar> rezervacije = new ArrayList<RezervacijaRentCar>();
		User korisnik = (User)request.getSession().getAttribute("ulogovan");		
		if(korisnik!=null) {
				for(RezervacijaRentCar rezervacija:korisnik.getRezRent()) {
						System.out.println("Dodata rezervacija sa check in"+rezervacija.getDatumPreuzimanja());
						rezervacije.add(rezervacija);
					}
		}
		return rezervacije;
	}
	
	@RequestMapping(value="/changePass",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
public @ResponseBody User changePassAdmin(@RequestParam String oldPass,@RequestParam String lozinka1,@RequestParam String lozinka2 ,@Context HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("ulogovan");
		System.out.println("ulogovan je "+user.getIme()+" a tip mu je "+user.getTip());
		
		String starasifra = oldPass;
		String novasifra = lozinka1;
		String ponovljenasifra = lozinka2;
		String  sifraStara = "";
		
		try {
			sifraStara = enkriptuj(starasifra);
			if(!sifraStara.equals(user.getLozinka())) {
				user = new User();
				user.setVerifikovan("stara");
				return user;
		
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!novasifra.equals(ponovljenasifra)) {
			user = new User();
			user.setVerifikovan("ponavljanje");
			return user;
		}
		
		
		try {
			String newSifra = enkriptuj(novasifra);
			user.setAdminPotvrdio(true);
			user.setLozinka(newSifra);
			//System.out.println("usao da sacuva promjenjenu sifru");
			servis.saveUser(user);
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
}
	@RequestMapping(value="/changePersonalData", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
public @ResponseBody User changePersonalData(@RequestBody UserDTO novi,@Context HttpServletRequest request){		

		System.out.println("Usao u promjeni podatke, mail je "+ novi.getMail());
		
		//provera da li je mail jedinstven
		User user = (User) request.getSession().getAttribute("ulogovan");
		if(!user.getMail().equals(novi.getMail())) {
			User provera = servis.findUserByMail(novi.getMail());
			
			if(provera != null) {
				System.out.println("Mejl nije jedinstven");
				provera.setVerifikovan("null");
				return provera;
		
			}
			user.setMail(novi.getMail());
		}
		
			if(!user.getIme().equals(novi.getIme())) {
				user.setIme(novi.getIme());
			}
			if(!user.getPrezime().equals(novi.getPrezime())) {
				user.setPrezime(novi.getPrezime());
			}
			
			if(user.getTelefon() != novi.getTelefon()) {
				user.setTelefon(novi.getTelefon());
			}
			if(!user.getGrad().equals(novi.getGrad())) {
				user.setGrad(novi.getGrad());	
				
			}
			
		    servis.saveUser(user);
			
			System.out.println("Update korisnika");
			return user;
		
}	


}
