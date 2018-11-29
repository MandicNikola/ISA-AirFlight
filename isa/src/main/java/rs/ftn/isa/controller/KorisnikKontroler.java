package rs.ftn.isa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
	//(@PathVariable leLong id )
	
	@RequestMapping(value="/registracija", method = RequestMethod.POST)
	public Korisnik registrujKorisnika(Korisnik novi){		
		
		//provera da li je mail jedinstven
	Korisnik provera = servis.findKorisnikByMail(novi.getMail());
		
		if(provera!=null) {			
			servis.saveKorisnika(novi);
		}else {
			return null;
		}
		return novi;
	}
	
	/*
	 @POST
	@Path("/registracija")
	public Korisnik registrovanjeKorisnika(Korisnik novi, @Context HttpServletRequest request) {
		
		korisnikDAO= (KorisnikDAO)ctx.getAttribute("dao");
		
		
		//ako nije null to znaci da taj korisnik nije do sad registrovan
		//moramo sacuvati registrovanog korisnika
	
			int greska=0;
			for(Korisnik pom : korisnikDAO.getKorisnici().values()) {
				if(pom.getKorisnickoIme().equals(novi.getKorisnickoIme())) {
					greska=1;
					break;
				}
			}
	
			if(greska == 1) {
				return null;
			}
			greska=0;
			
			for(Korisnik pom : korisnikDAO.getKorisnici().values()) {
					if(pom.getMail().equals(novi.getMail())) {
						greska=1;
						break;
					}
			}
			
			if(greska == 1) {
				Korisnik povratni = new Korisnik();
				povratni.setIme("Mail");
				return povratni;
			}
			String broj= novi.getTelefon();
			if(broj.equals("")||broj.equals("undefined")||broj==null) {
				Korisnik povratni = new Korisnik();
				povratni.setIme("Telefon");
				return povratni;
			}
			int brojT=0;
			try {
				brojT= Integer.parseInt(broj);
			}catch(NumberFormatException e){
				Korisnik povratni = new Korisnik();
				povratni.setIme("Telefon");
				return povratni;
			}
			
			if(brojT <= 0) {
				Korisnik povratni = new Korisnik();
				povratni.setIme("Telefon");
				return povratni;
			}
			
			novi.setUloga("Korisnik");
			korisnikDAO.getKorisnici().put(novi.getKorisnickoIme(), novi);
			
			ctx.setAttribute("dao", korisnikDAO);
			String contextPath = ctx.getRealPath("");
			korisnikDAO.sacuvajKorisnika(novi,contextPath);
			
		return novi; //vraca null ako ne uspe da registruje
	
	}
	 */
}
