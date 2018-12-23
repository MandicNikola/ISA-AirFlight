package rs.ftn.isa.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
			if(proveraImena != null && proveraImena.getId() != nova.getId()) {
				System.out.println("Postoji vozilo sa tim ienom");
				return null;
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
		public @ResponseBody Vehicle dodajRezervaciju(@PathVariable String podatak){		
			//u podatku se nalazi idVozilo=datumPreuzimanja=datumVracanja=cena
			System.out.println("usao u dodajRezervaciju "+podatak);
			
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
			rezervacija.setCena(cena);
			System.out.println("Datum preuzimanja je "+datPreuzimanja);
			System.out.println("Datum vracanja  je "+datVracanje);
			rezervacija.setDatumPreuzimanja(datPreuzimanja);
			rezervacija.setDatumVracanja(datVracanje);
			System.out.println(rezervacija);
	        			
			Vehicle vozilo = servis.findVehicleById(idVozilo);
			System.out.println("Nasao je vozilo ");
			rezervacija.setVozilo(vozilo);
			vozilo.getRezervacije().add(rezervacija);
			
			servis.saveVehicle(vozilo);
			Long idRent = vozilo.getFilijala().getServis().getId();
			
			vozilo.setModel(idRent.toString());	
			return vozilo;

		}

}
