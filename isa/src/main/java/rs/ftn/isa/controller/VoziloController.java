package rs.ftn.isa.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.dto.UserDTO;
import rs.ftn.isa.model.Filijala;
import rs.ftn.isa.model.RentACar;
import rs.ftn.isa.model.RezervacijaRentCar;
import rs.ftn.isa.model.StatusRezervacije;
import rs.ftn.isa.model.User;
import rs.ftn.isa.model.Vehicle;
import rs.ftn.isa.service.VoziloService;

@RestController
@RequestMapping(value="api/vozila")
public class VoziloController {

	@Autowired 
	VoziloService servis;
	
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public List<Vehicle> getAllVozila(){		
		return  servis.findAll();
	}	
	
			@RequestMapping(value="/registrovanje", 
					method = RequestMethod.POST,
					consumes = MediaType.APPLICATION_JSON_VALUE,
					produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody Vehicle registrujVozilo( @RequestBody Vehicle novo){		
		
		
		//validacija na serverskoj strani
		String naziv=novo.getNaziv();
		
		if(naziv.equals("")||naziv.equals("undefined")||naziv==null) {
			Vehicle povratni = new Vehicle();
			povratni.setGodiste(0);
			return povratni;
		}
		String marka=novo.getMarka();
		
		if(marka.equals("")||marka.equals("undefined")||marka==null) {
			Vehicle povratni = new Vehicle();
			povratni.setGodiste(1);
			return povratni;
		}
		
		String model=novo.getModel();
		
		if(model.equals("")||model.equals("undefined")||model==null) {
			Vehicle povratni = new Vehicle();
			povratni.setGodiste(2);
			return povratni;
		}
		
		int godiste=novo.getGodiste();
		
		if(godiste < 1990) {
			Vehicle povratni = new Vehicle();
			povratni.setGodiste(3);
			return povratni;
		}
		System.out.println("Usao u registrovanje vozila, naziv je "+ novo.getNaziv());

		Vehicle provera = servis.findVehicleByNaziv(novo.getNaziv());
			
		if(provera!=null) {
			//naziv mora biti jedinstven
			return null;
		}
		
		//servis.saveVehicle(novo);
		novo.setBroj(0);
		return novo;
		
		}	
		@RequestMapping(value="/deleteVozilo/{id}", method = RequestMethod.POST)
		public  void obrisiVozilo(@PathVariable String id){
				System.out.println("Usao u Delete vozilo dobio je "+id);
				
				
				servis.removeVehicle(Long.parseLong(id));
				
		}
		@RequestMapping(value="/vratiVozilo/{id}",
				method = RequestMethod.GET,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody Vehicle getVoziloById(@PathVariable Long id){	
			Vehicle povratna = servis.findVehicleById(id);
			System.out.println(" Nasao vozilo sa nazivom "+ povratna.getNaziv());
			return povratna;
		}


		@RequestMapping(value="/izmeniAuto", 
				method = RequestMethod.POST,
				consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody Vehicle izmeniVozilo(@RequestBody Vehicle nova){		
			
			System.out.println("Usao u izmeniVozilo");
			Long id=nova.getId();
			
			//prvo pronadjemo vozilo koje treba da se izmeni
			
			Vehicle vozilo = servis.findVehicleById(id);
			if(vozilo.equals(nova)) {
				System.out.println("Nista nije izmenjeno");
				vozilo.setModel(vozilo.getFilijala().getServis().getId().toString());
				return vozilo;
			}
			
			Vehicle proveraImena = servis.findVehicleByNaziv(nova.getNaziv());
			if(proveraImena != null) {
				 String kljuc1= proveraImena.getId().toString();
				 String kljuc2= nova.getId().toString();
				 if(kljuc1.equals(kljuc2)) {
					 System.out.println("Ne postoji isti naziv auta");
				 }else {

						System.out.println("Postoji vozilo sa tim ienom");
						return null; 
				 }
			}
			
			System.out.println("Provere su prosle");
			vozilo.setNaziv(nova.getNaziv());
			vozilo.setGodiste(nova.getGodiste());
			vozilo.setMarka(nova.getMarka());
			vozilo.setModel(nova.getModel());
			vozilo.setKategorija(nova.getKategorija());
			vozilo.setSedista(nova.getSedista());
			
			
			servis.saveVehicle(vozilo);
			Long idRent = vozilo.getFilijala().getServis().getId();
			
			vozilo.setModel(idRent.toString());
			return vozilo;

		}	
		@RequestMapping(value="/dodajRezervaciju/{podatak}", 
				method = RequestMethod.POST,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody User dodajRezervaciju(@PathVariable String podatak, @Context HttpServletRequest request){		
			//u podatku se nalazi idVozilo=datumPreuzimanja=datumVracanja=cena
			System.out.println("usao u dodajRezervaciju "+podatak);
			
			User korisnik = (User)request.getSession().getAttribute("ulogovan");		
			
			String[] niz=podatak.split("=");
			
			Long idVozilo=Long.parseLong(niz[0]);
			
			String startDatum=niz[1];
			String[] datP=startDatum.split("-");
			
			int year=Integer.parseInt(datP[0]);
			//meseci u javi od 0
			int month=Integer.parseInt(datP[1])-1;
			int day=Integer.parseInt(datP[2]);
		
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month, day);
			Date datPreuzimanja = calendar.getTime();
				
			
			System.out.println("Daatum je "+datPreuzimanja);
			
			String krajDatum=niz[2];
			String[] krajP=krajDatum.split("-");
			
			 year=Integer.parseInt(krajP[0]);
			 //meseci u javi idu od 0
			 month=Integer.parseInt(krajP[1])-1;
			 day=Integer.parseInt(krajP[2]);
			
			 calendar.set(year, month, day);
			 Date datVracanje = calendar.getTime();
			 System.out.println("Daatum je "+datVracanje);
					
			 
			int cena=Integer.parseInt(niz[3]);
	        			System.out.println("Usao u dodajRezervaciju u vozilo");
			
	        	
			//prvo pronadjemo vozilo koje treba da se izmeni
			RezervacijaRentCar rezervacija = new RezervacijaRentCar();
			rezervacija.setStatus(StatusRezervacije.AKTIVNA);
			rezervacija.setCena(cena);
			rezervacija.setKorisnik(korisnik);
			korisnik.getRezRent().add(rezervacija);
			System.out.println("Datum preuzimanja je "+datPreuzimanja);
			System.out.println("Datum vracanja  je "+datVracanje);
			rezervacija.setDatumPreuzimanja(datPreuzimanja);
			rezervacija.setDatumVracanja(datVracanje);
			System.out.println(rezervacija);
	        			
			Vehicle vozilo = servis.findVehicleById(idVozilo);
			System.out.println("Nasao je vozilo ");
			rezervacija.setVozilo(vozilo);
			vozilo.getRezervacije().add(rezervacija);
			vozilo.setBroj(vozilo.getBroj()+1);
			System.out.println("Id od vozila je "+vozilo.getId());
			servis.saveVehicle(vozilo);
			Long idRent = vozilo.getFilijala().getServis().getId();
			//vozilo.setModel(idRent.toString());	
			return korisnik;

		}
		
		
		@RequestMapping(value="/oceniVozilo/{podatak}", 
				method = RequestMethod.POST,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody Vehicle oceniVozilo(@PathVariable String podatak){		
		  System.out.println("Usao u oceni vozilo");
			String[] niz = podatak.split("=");
		    String idVoz=niz[0];
		    Integer novaOcena =Integer.parseInt(niz[1]);
		    String idRez= niz[2];
		    
			Vehicle vozilo = servis.findVehicleById(Long.parseLong(idVoz));
			
			if(vozilo!=null) {
					int brojOcena=vozilo.getBrojac();
					System.out.println("Broj ocena je "+brojOcena+ " trenutna ocena je "+vozilo.getOcena());
					double ukOcena = vozilo.getOcena()*brojOcena;
					System.out.println("Pomnozena ocena "+ukOcena);
					ukOcena = ukOcena+novaOcena;
					System.out.println("Dodata ocena "+ukOcena);
					brojOcena++;
					ukOcena=(double)ukOcena/brojOcena;
					System.out.println("Podeljena ocena je "+ukOcena);
					
					vozilo.setBrojac(brojOcena);
					vozilo.setOcena(ukOcena);
					
					Set<RezervacijaRentCar> rez=vozilo.getRezervacije();
					RezervacijaRentCar rezervacija=null;
					for(RezervacijaRentCar r : rez) {
						String idR=r.getId().toString();
						if(idR.equals(idRez)) {
							rezervacija=r;
							break;
						}
					}
					if(rezervacija == null) {
						return null;
					}
					vozilo.getRezervacije().remove(rezervacija);
					//setujemo da je vozilo ocenjeno da korisnik ne bi vise puta mogao da oceni vozilo
					rezervacija.setOcenjenVozilo(true);
					vozilo.getRezervacije().add(rezervacija);
					
					servis.saveVehicle(vozilo);
					
					return vozilo;
			}else {
				return vozilo;
			}
		
		}		
		@RequestMapping(value="/cekirajOcenu/{podatak}", 
				method = RequestMethod.POST,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody Vehicle cekirajOcenu(@PathVariable String podatak){		
		  System.out.println("Usao u oceni vozilo");
		  String[] niz = podatak.split("=");
		    String idVoz=niz[0];
		    Integer novaOcena =Integer.parseInt(niz[1]);
		    String idRez= niz[2];
		    
			Vehicle vozilo = servis.findVehicleById(Long.parseLong(idVoz));
			
			if(vozilo!=null) {
					
					Set<RezervacijaRentCar> rez=vozilo.getRezervacije();
					RezervacijaRentCar res=null;
					for(RezervacijaRentCar r : rez) {
						String idR=r.getId().toString();
						if(idR.equals(idRez)) {
							res=r;
							break;
						}
					}
					if(res == null) {
						return null;
					}
					vozilo.getRezervacije().remove(res);
					//setujemo da je rent-a-car servis ocenjen da korisnik ne bi vise puta mogao da oceni servis
					res.setOcenjenRent(true);;
					vozilo.getRezervacije().add(res);
					
					servis.saveVehicle(vozilo);
					vozilo.setOcena((double)novaOcena);
					return vozilo;
			}else {
				return vozilo;
			}
		
		}
		@RequestMapping(value="/otkaziVozilo/{podatak}", 
				method = RequestMethod.POST,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody Vehicle otkaziVozilo(@PathVariable String podatak){		
		  System.out.println("Usao u otkazi vozilo");
			String[] niz = podatak.split("=");
		    String idVoz=niz[0];
		    String idRez= niz[1];
		    
			Vehicle vozilo = servis.findVehicleById(Long.parseLong(idVoz));
			
			if(vozilo!=null) {
					Set<RezervacijaRentCar> rez=vozilo.getRezervacije();
					RezervacijaRentCar rezervacija=null;
					for(RezervacijaRentCar r : rez) {
						String idR=r.getId().toString();
						if(idR.equals(idRez)) {
							rezervacija=r;
							break;
						}
					}
					if(rezervacija == null) {
						return null;
					}
					vozilo.getRezervacije().remove(rezervacija);
					//setujemo da je vozilo ocenjeno da korisnik ne bi vise puta mogao da oceni vozilo
					rezervacija.setStatus(StatusRezervacije.OTKAZANA);
					vozilo.getRezervacije().add(rezervacija);
					
					servis.saveVehicle(vozilo);
					
					return vozilo;
			}else {
				return vozilo;
			}
		
		}		
}
