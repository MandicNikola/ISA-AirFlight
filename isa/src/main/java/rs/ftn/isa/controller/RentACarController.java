package rs.ftn.isa.controller;

import java.util.ArrayList;
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

import rs.ftn.isa.dto.RentACarDTO;
import rs.ftn.isa.model.Filijala;
import rs.ftn.isa.model.Hotel;
import rs.ftn.isa.model.PricelistRentCar;
import rs.ftn.isa.model.RentACar;
import rs.ftn.isa.model.User;
import rs.ftn.isa.model.Usluga;
import rs.ftn.isa.model.Vehicle;
import rs.ftn.isa.service.RentACarServiceImpl;

@RestController
@RequestMapping(value="api/rents")
public class RentACarController {
	@Autowired 
	private RentACarServiceImpl servis;
	
	
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public List<RentACar> getAllRents(){		
		return  servis.findAll();
	}
	
	@RequestMapping(value="/newrentacar",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody RentACar newRentACar(@RequestBody RentACarDTO newRent) {
		RentACar rent = servis.findOneByNaziv(newRent.getNaziv());
		if(rent == null) {
			RentACar novi = new RentACar(newRent.getNaziv(),newRent.getAdresa(),newRent.getOpis());
			servis.saveRentACar(novi);
			return novi;
		}else {
			RentACar pom = new RentACar();
			return pom;
		}
	}	
		
	@RequestMapping(value="/vratiRentId/{id}",
			method = RequestMethod.GET)
	public @ResponseBody RentACar getRentId(@PathVariable Long id){
	
		RentACar rentServis = servis.findOneById(id);
		System.out.println("Usao je u getRentId "+ rentServis.getNaziv());
		return rentServis;
	}

	
	@RequestMapping(value="/postavivozilo/{id}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE
			)
	public @ResponseBody RentACar postaviVozilo(@PathVariable Long id, @RequestBody Vehicle vozilo){
	
		RentACar rentServis = servis.findOneById(id);		
		System.out.println("Pronasao rent servis "+ rentServis.getNaziv());
		
		Vehicle novo = vozilo;
		System.out.println(novo.getNaziv() + " " + novo.getCena());
		System.out.println(novo.toString());
		
		
		novo.setServisrent(rentServis);
		Set<Vehicle> vozilaLista=rentServis.getVozila(); 
		vozilaLista.add(novo);
		rentServis.setVozila(vozilaLista);
		
	    //baza prilikom cuvanja izmeni id
		RentACar povratna = servis.saveRentACar(rentServis);
		
		System.out.println("NOVI id od vozila je "+povratna.getId());
		//treba mi za povratak na profil od ovog rent-a-car-a
		String br=povratna.getId().toString();
		povratna.setAdresa(br);
		return povratna;
	}
	@RequestMapping(value="/postavifilijalu/{id}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE
			)
	public @ResponseBody RentACar postaviFilijalu(@PathVariable Long id, @RequestBody Filijala filijala){
		
		RentACar rentServis = servis.findOneById(id);		
		System.out.println("Pronasao rent servis "+ rentServis.getNaziv());
		
		Filijala novo = filijala;
		System.out.println("Naziv filijale " + novo.getGrad() + " " + novo.getUlica());
		
		
		novo.setServis(rentServis);;
		Set<Filijala> filijalaLista=rentServis.getFilijale(); 
		filijalaLista.add(novo);
		rentServis.setFilijale(filijalaLista);
		
	    //baza prilikom cuvanja izmeni id
		RentACar povratna = servis.saveRentACar(rentServis);
		
		String br=povratna.getId().toString();
		povratna.setAdresa(br);
	
		return povratna;
	}
	
	@RequestMapping(value="/getFilijale/{id}", method = RequestMethod.GET)
	public  @ResponseBody ArrayList<Filijala> vratiFilijale(@PathVariable Long id){	
		ArrayList<Filijala> rezultat = new ArrayList<Filijala>();
		RentACar rent = servis.findOneById(id);
		
		System.out.println("Usao u vratiFilijale");
		for(Filijala F : rent.getFilijale()) {
				rezultat.add(F);
				System.out.println("Ulica filijale "+F.getUlica());
			
		}
		return rezultat;
	}
	@RequestMapping(value="/getVozila/{id}", method = RequestMethod.GET)
	public  List<Vehicle> vratiVozila(@PathVariable Long id){	
		ArrayList<Vehicle> rezultat = new ArrayList<Vehicle>();
		RentACar rent = servis.findOneById(id);
		
		for(Vehicle V : rent.getVozila()) {
				rezultat.add(V);
			
		}
		return rezultat;
	}
	

	@RequestMapping(value="/dodajUslugu/{pomocna}", 
	method = RequestMethod.POST,
	produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody RentACar dodajUslugu( @PathVariable String pomocna){		
		
		String [] niz=pomocna.split("=");
		
		String id= niz[0];
		String naziv = niz[1];
		String cenaA= niz[2];
		String cenaB= niz[3];
		String cenaC= niz[4];
		String cenaD= niz[5];
		String cenaE= niz[6];
		
		RentACar rent = servis.findOneById(Long.parseLong(id));
		System.out.println("Pronadjen servis "+ rent.getNaziv());
		//System.out.println("id je "+id+", naziv je "+naziv+ "cene : "+cenaA +" "+cenaB+ " "+cenaC+ " "+cenaD+ " "+cenaE);
		
		
		
		PricelistRentCar noviCenovnik ;
		PricelistRentCar cenovnik = servis.findAktivanCenovnik(rent.getId()) ;
		
		if(cenovnik == null) { //nema aktivnih cenovnika, treba dodati cenovnik
			System.out.println("cenovnik je null");
			//jos nije postavljen cenovnik
			Date datum = new Date();
			noviCenovnik = new PricelistRentCar(datum);
			noviCenovnik.setAktivan(true);
			
			Usluga uslugaA = new Usluga(naziv, Integer.parseInt(cenaA), "A");
			Usluga uslugaB = new Usluga(naziv, Integer.parseInt(cenaB), "B");
			Usluga uslugaC = new Usluga(naziv, Integer.parseInt(cenaC), "C");
			Usluga uslugaD = new Usluga(naziv, Integer.parseInt(cenaD), "D");
			Usluga uslugaE = new Usluga(naziv, Integer.parseInt(cenaE), "E");
			
			uslugaA.setLista(noviCenovnik);
			uslugaB.setLista(noviCenovnik);
			uslugaC.setLista(noviCenovnik);
			uslugaD.setLista(noviCenovnik);
			uslugaE.setLista(noviCenovnik);
			
			Set<Usluga> nove =  noviCenovnik.getUsluge();
			nove.add(uslugaA);
			nove.add(uslugaB);
			nove.add(uslugaC);
			nove.add(uslugaD);
			nove.add(uslugaE);
			
			noviCenovnik.setUsluge(nove);
			
			noviCenovnik.setRentcar(rent);
			Set<PricelistRentCar> cenovnici = rent.getCenovnici();
			cenovnici.add(noviCenovnik);
			rent.setCenovnici(cenovnici);
			
			RentACar povratna =servis.saveRentACar(rent);
			return povratna;
			
		}else {
			rent.getCenovnici().remove(cenovnik);
			//postoji vec aktivan cenovnik, preuzmemo ga i dodamo mu nove usluge
			Usluga uslugaA = new Usluga(naziv, Integer.parseInt(cenaA), "A");
			Usluga uslugaB = new Usluga(naziv, Integer.parseInt(cenaB), "B");
			Usluga uslugaC = new Usluga(naziv, Integer.parseInt(cenaC), "C");
			Usluga uslugaD = new Usluga(naziv, Integer.parseInt(cenaD), "D");
			Usluga uslugaE = new Usluga(naziv, Integer.parseInt(cenaE), "E");
			
			uslugaA.setLista(cenovnik);
			uslugaB.setLista(cenovnik);
			uslugaC.setLista(cenovnik);
			uslugaD.setLista(cenovnik);
			uslugaE.setLista(cenovnik);
			
			Set<Usluga> nove = cenovnik.getUsluge();
			nove.add(uslugaA);
			nove.add(uslugaB);
			nove.add(uslugaC);
			nove.add(uslugaD);
			nove.add(uslugaE);
			
			cenovnik.setUsluge(nove);
			
			cenovnik.setRentcar(rent);
			Set<PricelistRentCar> cenovnici =  rent.getCenovnici();
			cenovnici.add(cenovnik);
			rent.setCenovnici(cenovnici);
			
			RentACar povratna =servis.saveRentACar(rent);
			return povratna;
			
		}
		
		
	
	}	
	
	
		

	
		
}
