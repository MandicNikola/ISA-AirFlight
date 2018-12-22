package rs.ftn.isa.controller;

import java.util.ArrayList;
import java.util.Comparator;
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
import rs.ftn.isa.dto.ReservationRentDTO;
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

	
	/*@RequestMapping(value="/postavivozilo/{id}",
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
	}*/
	@RequestMapping(value="/poveziFilijalu/{podatak}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE
			)
	public @ResponseBody RentACar poveziFilijalu(@PathVariable String podatak, @RequestBody Vehicle vozilo){
	
		String [] niz =podatak.split("=");
		Long id=Long.parseLong(niz[0]);
		Long idFilijala = Long.parseLong(niz[1]);
		RentACar rentServis = servis.findOneById(id);		
		System.out.println("Pronasao rent servis u poveziFilijalu"+ rentServis.getNaziv());
		
		Filijala stara =null;
		
		for(Filijala s : rentServis.getFilijale()) {
				if(s.getId()==idFilijala) {
						stara=s;
						break;
				}
		}
		//PROVERA DA LI U TOJ FILIJALI VEC POSTOJI ISTO VOZILO
		for(Vehicle V : stara.getVozila()) {
			if(V.getNaziv().equals(vozilo.getNaziv())) {
				return null;
			}
		}
		
		//brissemo staru filijalu da bismo joj dodali novo vozilo
		
		rentServis.getFilijale().remove(stara);
		
		
		//Set<Filijala> filLista=rentServis.getFilijale(); 
		vozilo.setFilijala(stara);
		stara.getVozila().add(vozilo);
		
		stara.setServis(rentServis);
		
		rentServis.getFilijale().add(stara);
		
		RentACar povratna = servis.saveRentACar(rentServis);
		
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
	
	@RequestMapping(value="/getAktivanCenovnik/{id}", method = RequestMethod.GET)
	public  @ResponseBody PricelistRentCar vratiAktivanCenovnik(@PathVariable Long id){	
		
		RentACar rent = servis.findOneById(id);
		PricelistRentCar cenovnik =servis.findAktivanCenovnik(id);
		
		if(cenovnik==null) {
			System.out.println("Nema aktivnog cenovnika");
		}
		return cenovnik;
	}
	//vraca usluge odredjenog servisa odredjene kategorije
	@RequestMapping(value="/katUsluge/{pom}", method = RequestMethod.GET)
	public  @ResponseBody ArrayList<Usluga> vratiUslugeKategorije(@PathVariable String pom){	
		//dobice oblik 1245=A <-1245 je id ,a A je kategorija
		String[] niz = pom.split("=");
		Long id = Long.parseLong(niz[0]);
		String kat=niz[1];
		
		RentACar rent = servis.findOneById(id);
		PricelistRentCar cenovnik =servis.findAktivanCenovnik(id);
		
		if(cenovnik == null) {
			System.out.println("Cenovnik je null");
			return null;
		}
		if(cenovnik.getUsluge().size() == 0) {
			System.out.println("Cenovnik je prazan");
			return null;
			}
		
		System.out.println("Datum cenovnika je "+cenovnik.getDatum_primene());
		System.out.println("Cenovnik nije prazan");
		Set<Usluga> sveUsluge = cenovnik.getUsluge();
		
		ArrayList<Usluga> katUsluge= new ArrayList<Usluga>();
		
		for(Usluga U : sveUsluge) {
				if(U.getKategorija().toString().equals(kat)) {
						katUsluge.add(U);
						System.out.println("Naziv usluge je "+U.getNaziv());
				}
		}
		katUsluge.sort(Comparator.comparingInt(Usluga :: getPrekoTrajanja));
		
		for(int i=0;i<katUsluge.size();i++) {
			System.out.println("Usluga ima trajanje "+katUsluge.get(i).getPrekoTrajanja());
		}
		
		return katUsluge;
	}
	
	
	
	@RequestMapping(value="/getVozila/{id}", method = RequestMethod.GET)
	public  List<Vehicle> vratiVozila(@PathVariable Long id){	
		ArrayList<Vehicle> rezultat = new ArrayList<Vehicle>();
		RentACar rent = servis.findOneById(id);
		
		System.out.println("Usao u get vozila, prolazimo kroz sve filijale");
		//prolazimo kroz sve filijale i preuzimamo sva vozila
		
		for(Filijala F:rent.getFilijale()) {

			
			for(Vehicle V : F.getVozila()) {
					System.out.println("Vozilo je "+ V.getNaziv());
					rezultat.add(V);
				
			}
			
		}
		return rezultat;
	}


	@RequestMapping(value="/dodajUslugu/{pomocna}", 
	method = RequestMethod.POST,
	produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody RentACar dodajUslugu( @PathVariable String pomocna){		
		
		String [] niz=pomocna.split("=");
		
		String id= niz[0];
		String trajanje = niz[1];
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
			
			int dana = Integer.parseInt(trajanje);
			Usluga uslugaA = new Usluga(Integer.parseInt(cenaA),dana, "A");
			Usluga uslugaB = new Usluga( Integer.parseInt(cenaB),dana, "B");
			Usluga uslugaC = new Usluga( Integer.parseInt(cenaC),dana, "C");
			Usluga uslugaD = new Usluga( Integer.parseInt(cenaD),dana, "D");
			Usluga uslugaE = new Usluga( Integer.parseInt(cenaE),dana, "E");
			
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
			

			int dana = Integer.parseInt(trajanje);
			
			Usluga uslugaA = new Usluga(Integer.parseInt(cenaA),dana, "A");
			Usluga uslugaB = new Usluga( Integer.parseInt(cenaB),dana, "B");
			Usluga uslugaC = new Usluga( Integer.parseInt(cenaC),dana, "C");
			Usluga uslugaD = new Usluga( Integer.parseInt(cenaD),dana, "D");
			Usluga uslugaE = new Usluga( Integer.parseInt(cenaE),dana, "E");
			
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
	
	
	@RequestMapping(value="/izmeniUslugu/{pomocna}", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
			public @ResponseBody RentACar izmeniUslugu( @PathVariable String pomocna){		
				
		System.out.println("Usao je u izmeni Usluugu" +pomocna);
				String [] niz=pomocna.split("=");
				
				String idUsluge= niz[0];
				String novaVrednost= niz[1];
				String idServisa= niz[2];
				
				RentACar rent = servis.findOneById(Long.parseLong(idServisa));
				System.out.println("Usao u izmeniUslugu "+ rent.getNaziv());
				//System.out.println("id je "+id+", naziv je "+naziv+ "cene : "+cenaA +" "+cenaB+ " "+cenaC+ " "+cenaD+ " "+cenaE);
				
				if(Integer.parseInt(novaVrednost) <=0) {
					return null;
				}
				
				
				PricelistRentCar stariCenovnik = servis.findAktivanCenovnik(rent.getId()) ;
				//pronasli smo trenutno aktivni cenovnik, koji postaje neaktivan
				Usluga stara= new Usluga();
				for(Usluga U : stariCenovnik.getUsluge()) {
					if(U.getId() == Integer.parseInt(idUsluge)) {
						stara=U;
						break;
					}
				}
				
				rent.getCenovnici().remove(stariCenovnik);
				stariCenovnik.setAktivan(false);
				rent.getCenovnici().add(stariCenovnik);
			
				PricelistRentCar noviCenovnik=new PricelistRentCar(new Date());
				noviCenovnik.setAktivan(true);
				
				stariCenovnik.getUsluge().remove(stara);
				
				for(Usluga usl : stariCenovnik.getUsluge()) {
						usl.setLista(noviCenovnik);
						noviCenovnik.getUsluge().add(usl);
				}
			
				stara.setCena(Integer.parseInt(novaVrednost));
				stara.setLista(noviCenovnik);
				noviCenovnik.getUsluge().add(stara);
				
				//postoji aktivan cenovnik, preuzmemo ga i obrisemo, jer cemo izmeniti jednu uslugu
				
				
				noviCenovnik.setRentcar(rent);
				//setovali odnos cenovnik i usluge
				
				//dodamo novi cenovnik
				rent.getCenovnici().add(noviCenovnik);
				RentACar povratna =servis.saveRentACar(rent);
				
				return povratna;
					
			}	
			
	@RequestMapping(value="/obrisiRent/{id}", method = RequestMethod.POST)
	public  void obrisiRent(@PathVariable Long id){
		
		servis.removeRentACar(id);
	
	}
	@RequestMapping(value="/izmena",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody RentACar changeRentACar(@RequestBody RentACar newRent) {
		
		RentACar rent = servis.findOneById(newRent.getId());
		
		if(rent.getNaziv().equals(newRent.getNaziv()) && (rent.getAdresa().equals(newRent.getAdresa())) && (rent.getOpis().equals(newRent.getOpis())) ) {
			//nista se nije izmenilo
			System.out.println("Nista se nije izmenilo");
			return rent;
		}
		
		RentACar provera =servis.findOneByNaziv(newRent.getNaziv());
		if(provera!=null && provera.getId()!=newRent.getId()) {
			System.out.println("Postoji vec taj  naziv rent");
			return null;
		}
		
		rent.setAdresa(newRent.getAdresa());
		rent.setNaziv(newRent.getNaziv());
		rent.setOpis(newRent.getOpis());
		
		servis.saveRentACar(rent);
		
		return rent;
	}	
	

	
	@RequestMapping(value="/checkRezervaciju",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody ArrayList<Vehicle> checkReservation(@RequestBody ReservationRentDTO rezervacija) {
		
		Long id=rezervacija.getRentId();
		ArrayList<Vehicle> ispunjeniUslovi=new  ArrayList<Vehicle>();
		//prvo gledamo u kom se servisu nalazimo
		RentACar rent = servis.findOneById(id);
			System.out.println("Usau u checkReservation");
			
		ArrayList<Vehicle> ispunjenaKategorija=new  ArrayList<Vehicle>();
		String kat=rezervacija.getTip();
		
		/*for(Vehicle V : rent.getVozila()) {
				if(V.getKategorija().toString().equals(kat)){
						ispunjenaKategorija.add(V);
				}
		}*/
		boolean postojiFilijala=false;
		String lokacija = rezervacija.getStartLocation(); 
				
		for(Filijala F : rent.getFilijale()) {
				if(F.getGrad().equals(lokacija)) {
					postojiFilijala=true;
					break;
				}
		}
		
		
		return ispunjeniUslovi;
	}	
	

}
