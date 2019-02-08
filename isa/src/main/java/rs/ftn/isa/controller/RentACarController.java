package rs.ftn.isa.controller;

//import static org.assertj.core.api.Assertions.setAllowComparingPrivateFields;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.dto.RentACarDTO;
import rs.ftn.isa.dto.ReservationRentDTO;
import rs.ftn.isa.dto.VehicleDTO;
import rs.ftn.isa.model.Discount;
import rs.ftn.isa.model.Filijala;
import rs.ftn.isa.model.PricelistRentCar;
import rs.ftn.isa.model.RentACar;
import rs.ftn.isa.model.RezervacijaRentCar;
import rs.ftn.isa.model.StatusRezervacije;
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
	//vraca rent servise sortirane po nekom kriterijumu
	@RequestMapping(value="/sort/{uslov}", method = RequestMethod.GET)
	public List<RentACar> getSortedRents(@PathVariable String uslov){		
		System.out.println("Uslov je "+uslov);
		List<RentACar> svi = servis.findAll();
		List<RentACar> sortiranaLista=new ArrayList<RentACar>();
		
		if(uslov.equals("NameA")) {
			//sortiraj po nazivu od A-Z
			Collections.sort(svi, RentACar.RentNameComparator);
			for(RentACar R : svi) {
				sortiranaLista.add(R);
			}
			
		}else if(uslov.equals("NameD")) {
			//sortiraj po nazivu od Z-A
			Collections.sort(svi, RentACar.RentNameComparator);
			for(int i=svi.size()-1; i>=0; i--) {
				sortiranaLista.add(svi.get(i));
			}
			
		}else if(uslov.equals("CityA")) {
			//sortiraj po gradu od A-Z
					Collections.sort(svi, RentACar.RentCityComparator);
			for(RentACar R : svi) {
				sortiranaLista.add(R);
			}
		}else {
			//sortiraj po gradu od Z-A
					Collections.sort(svi, RentACar.RentCityComparator);
			for(int i=svi.size()-1; i>=0; i--) {
				sortiranaLista.add(svi.get(i));
			}
		}
		
		
		return sortiranaLista;
	}
	//pretraga svih rent servisa po nazivu ili gradu, i vremenskom periodom
	@RequestMapping(value="/findRents/{podatak}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RentACar> findRents(@PathVariable String podatak){
		
		System.out.println("Usao u find rents "+podatak);
		String[] niz=podatak.split("=");
		String nazivGrad = niz[0];
		String startDat=niz[1];
		String endDat=niz[2];
		
		String[] datP=startDat.split("-");
		
		int year=Integer.parseInt(datP[0]);
		//meseci u javi od 0
		int month=Integer.parseInt(datP[1])-1;
		int day=Integer.parseInt(datP[2]);
	
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		Date datPreuzimanja = calendar.getTime();
		
		String[] krajP=endDat.split("-");
		
		 year=Integer.parseInt(krajP[0]);
		 //meseci u javi idu od 0
		 month=Integer.parseInt(krajP[1])-1;
		 day=Integer.parseInt(krajP[2]);
		
		 calendar.set(year, month, day);
		 Date datVracanje = calendar.getTime();
		
		
		 //imamo dva datuma i naziv ili grad od rent-a-car
		 //proveravamo da li se neki rent-a-car zove tako
		 List<RentACar> sviRent=servis.findAll();
		 List<RentACar> nadjeniRent=new ArrayList<RentACar>();
			System.out.println("Dobio je adresu "+nazivGrad);
		 for(RentACar R : sviRent) {
			 System.out.println("Adresa je "+R.getAdresa());
			 String naziv=nazivGrad.toLowerCase();
			 String gradRent=R.getGrad().toLowerCase();
			 String nazivRenta=R.getNaziv().toLowerCase();
			 
			 if(gradRent.equals(naziv)) {
				 System.out.println("Pronadjen rent po gradu" + R.getNaziv());
				 nadjeniRent.add(R);
			 }
			 if(nazivRenta.contains(naziv)) {
				 System.out.println("Pronadjen rent po nazivu" + R.getNaziv());
				 nadjeniRent.add(R);
			 }
		 }
		 
		 List<RentACar> povratniRent=new ArrayList<RentACar>();
			
		 for(RentACar R : nadjeniRent) {
			 //indikator da li postoji vozilo u filijali koje zadovoljava uslov
			 boolean postojiVozilo = false;
			 for(Filijala F : R.getFilijale()) {
				 
				 	for(Vehicle V : F.getVozila()) {
				 		//proveravamo da li je makar jedno vozilo iz rent-servisa
				 		//slobodno u izabranom terminu, ako jeste taj Rent prikazujemo korisniku
				 		
				 		Set<RezervacijaRentCar> rezervacije = V.getRezervacije(); 
				 		if(rezervacije.size()==0) {
				 			postojiVozilo=true;
				 			//vozilo nema rezervacija slobodno je 
				 		}
						//Provera 1 --> 
						//ako je nas datum preuzimanja pre datuma vracanja iz rezervacije,
						//Provera 2 --> 
						//onda gledamo da li je i datum vracanja naseg vozila nakon datuma preuzimanja iz
						//rezervacije
						
						//prolazimo kroz sve rezervacije koje su napravljene za ovo vozilo
						for(RezervacijaRentCar rez : rezervacije) {	
							if(rez.getStatus()==StatusRezervacije.AKTIVNA) {
								
								//ako je datum preuzimanja vozila pre datuma vracanja iz rezervacije
								if(datPreuzimanja.compareTo(rez.getDatumVracanja())<0) {
									 //datum vracanja auta posle datuma preuzimanja iz rezervacije, preklapaju se termini, vozilo nam ne odgovara
										if(datVracanje.compareTo(rez.getDatumPreuzimanja())>0){
											System.out.println("provera2--> Datum vracanja je posle datuma preuzimanja iz rezervacije");
										}else {
											postojiVozilo=true;
										}
								}else {
									postojiVozilo=true;
								}	
							}
							
							
						}
						
				 	}
			 }
			 //prosli smo kroz sva vozila svih filijali dovoljno je da je jedno vozilo ispunilo uslove
			 if(postojiVozilo) {
				 povratniRent.add(R);
			 }
		 }
		
		
		return  povratniRent;
	}
	
	//dodavanje novog servisa
	@RequestMapping(value="/newrentacar",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> newRentACar(@RequestBody RentACarDTO newRent) {
		RentACar rent = servis.findOneByNaziv(newRent.getNaziv());
		if(rent == null) {
			RentACar novi = new RentACar(newRent.getNaziv(),newRent.getGrad(),newRent.getAdresa(),newRent.getOpis());
			servis.saveRentACar(novi);
			return new ResponseEntity<>(novi, HttpStatus.CREATED);	

		}else {
			RentACar pom = new RentACar();
			return new ResponseEntity<>(pom, HttpStatus.OK);	

		}
	}	
		
	@RequestMapping(value="/vratiRentId/{id}",
			method = RequestMethod.GET)
	public @ResponseBody RentACar getRentId(@PathVariable Long id){
	
		RentACar rentServis = servis.findOneById(id);
		System.out.println("Usao je u getRentId "+ rentServis.getNaziv());
		return rentServis;
	}

//nastavak dodavanja vozila u filijalu
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
	//dodavanje filijale
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
	
	//vrati filijalu odredjenog rent-a-car servisa
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
		
		//vraca aktivni cenovnik odredjenog rent-a-car servisa
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
	
	
	//vraca sva vozila odredjenog rent-a-car servisa
	@RequestMapping(value="/getVozila/{id}", method = RequestMethod.GET)
	public  List<Vehicle> vratiVozila(@PathVariable Long id){	
		ArrayList<Vehicle> rezultat = new ArrayList<Vehicle>();
		RentACar rent = servis.findOneById(id);
		
		System.out.println("Usao u get vozila, prolazimo kroz sve filijale");
		//prolazimo kroz sve filijale i preuzimamo sva vozila
		
		for(Filijala F:rent.getFilijale()) {

			
			for(Vehicle V : F.getVozila()) {
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
			Usluga uslugaA = new Usluga(Double.parseDouble(cenaA),dana, "A");
			Usluga uslugaB = new Usluga(Double.parseDouble(cenaB),dana, "B");
			Usluga uslugaC = new Usluga(Double.parseDouble(cenaC),dana, "C");
			Usluga uslugaD = new Usluga(Double.parseDouble(cenaD),dana, "D");
			Usluga uslugaE = new Usluga(Double.parseDouble(cenaE),dana, "E");
			
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
			
			Usluga uslugaA = new Usluga(Double.parseDouble(cenaA),dana, "A");
			Usluga uslugaB = new Usluga( Double.parseDouble(cenaB),dana, "B");
			Usluga uslugaC = new Usluga( Double.parseDouble(cenaC),dana, "C");
			Usluga uslugaD = new Usluga(Double.parseDouble(cenaD),dana, "D");
			Usluga uslugaE = new Usluga( Double.parseDouble(cenaE),dana, "E");
			
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
	
	//izmena jedne usluge odredjene kategorije
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
				
				if(Double.parseDouble(novaVrednost) <=0) {
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
			
				stara.setCena(Double.parseDouble(novaVrednost));
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
			
	@RequestMapping(value="/obrisiRent/{id}", method = RequestMethod.DELETE)
	public  void obrisiRent(@PathVariable Long id){
		
		servis.removeRentACar(id);
	
	}
	@RequestMapping(value="/izmena",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody RentACar changeRentACar(@RequestBody RentACar newRent) {
		
		RentACar rent = servis.findOneById(newRent.getId());
		
		if(rent.getNaziv().equals(newRent.getNaziv())&&(rent.getGrad().equals(newRent.getGrad())) && (rent.getAdresa().equals(newRent.getAdresa())) && (rent.getOpis().equals(newRent.getOpis())) ) {
			//nista se nije izmenilo
			System.out.println("Nista se nije izmenilo");
			return rent;
		}
		
		RentACar provera =servis.findOneByNaziv(newRent.getNaziv());
		System.out.println("Pronadjen ID JE "+provera.getId());
		System.out.println("Id od starog servisa je "+newRent.getId());
		if(provera!=null) {
			System.out.println("Postoji vec taj  naziv rent");
			String kljuc1=provera.getId().toString();
			String kljuc2=newRent.getId().toString();
			if(kljuc1.equals(kljuc2)) {
				System.out.println("U pitanju je taj rent-a-car ");
			}else {
				System.out.println("U pitanju je drugi rent-a-car koji ima uneti naziv");
					return null;
			}
		}
		rent.setGrad(newRent.getGrad());
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
	@ResponseBody ArrayList<VehicleDTO> checkReservation(@RequestBody ReservationRentDTO rezervacija,@Context HttpServletRequest request) {
		User korisnik = (User)request.getSession().getAttribute("ulogovan");		
		int brojBodova  = korisnik.getBodovi();
		
		Long id=rezervacija.getRentId();
		ArrayList<Vehicle> ispunjeniUslovi=new  ArrayList<Vehicle>();
		ArrayList<VehicleDTO> povratnaLista=new  ArrayList<VehicleDTO>();
		
		//prvo gledamo u kom se servisu nalazimo
		RentACar rent = servis.findOneById(id);
		System.out.println("Usau u checkReservation");
			
		Filijala lociranaFilijala=null;
		//ako nema filijala nije korisnik mogao nista da izabere
		if(rent.getFilijale() == null) {

			System.out.println("nema filjala1");
			return new ArrayList<VehicleDTO>();
		}
		if(rent.getFilijale().size()==0) {

			System.out.println("nema filjala2");
			return new ArrayList<VehicleDTO>();
		}
	
		String startLokacija = rezervacija.getStartLocation();
		Long idPocetnaF = Long.parseLong(startLokacija);
		String idPocS=startLokacija;
		System.out.println("Id pocetna je");
		System.out.println(idPocS);
		for(Filijala F : rent.getFilijale()) {
			System.out.println(F.getId().toString());
				if(F.getId().toString().equals(idPocS)) {
					lociranaFilijala=F;
					System.out.println("Pronadjena filijala iz koje se uzima vozilo");
				}
		}
		
		ArrayList<Vehicle> ispunjenaKategorija=new  ArrayList<Vehicle>();
		String kat=rezervacija.getTip();
		if(lociranaFilijala.getVozila()==null) {
			System.out.println("nema vozila1");
			return new ArrayList<VehicleDTO>();
		}
		if(lociranaFilijala.getVozila().size()==0) {

			System.out.println("nema vozila2");
			return new ArrayList<VehicleDTO>();
		}
		for(Vehicle V : lociranaFilijala.getVozila()) {
				if(V.getKategorija().toString().equals(kat)){
						ispunjenaKategorija.add(V);//vozila koja se nalaze u trazenom gradu i kategoriji
				}
		}
		
		for(int i=0;i<ispunjenaKategorija.size();i++) {
				Vehicle vozilo = ispunjenaKategorija.get(i);
				Set<RezervacijaRentCar> rezervacije = vozilo.getRezervacije(); 
				
				//Provera 1 --> 
				//ako je nas datum preuzimanja pre datuma vracanja iz rezervacije,
				//Provera 2 --> 
				//onda gledamo da li je i datum vracanja naseg vozila nakon datuma preuzimanja iz
				//rezervacije
				 System.out.println("rezerv"+rezervacija.getPickUp().toString());
				 System.out.println("rezerv"+rezervacija.getDropOff().toString());
				
				boolean dozvolaPickUp = true;
				//prolazimo kroz sve rezervacije koje su napravljene za ovo vozilo
				for(RezervacijaRentCar R : rezervacije) {	
					if(R.getStatus()==StatusRezervacije.AKTIVNA) {
						//ako je datum preuzimanja vozila pre datuma vracanja iz rezervacije
						if(rezervacija.getPickUp().compareTo(R.getDatumVracanja())<0) {
							 //datum vracanja auta posle datuma preuzimanja iz rezervacije, preklapaju se termini, vozilo nam ne odgovara
								if(rezervacija.getDropOff().compareTo(R.getDatumPreuzimanja())>0){
									dozvolaPickUp = false;
									System.out.println("provera2--> Datum vracanja je posle datuma preuzimanja iz rezervacije");
								}
						}
					}
					
					
				}
				
				boolean dozvolaPutnici = true;
					
				//PROVERA3 da li mogu stati putnici
				if(vozilo.getSedista() < rezervacija.getPutnici()) {
						dozvolaPutnici=false;
						System.out.println("Ne mogu stati putnici");
				}
				
				
				PricelistRentCar cenovnik = servis.findAktivanCenovnik(rent.getId());
				if(cenovnik==null) {
					System.out.println("Cenovnik je null");
					return new ArrayList<VehicleDTO>();
				}
				if(cenovnik.getUsluge()==null) {
					System.out.println("nema usluga 1");
					return new ArrayList<VehicleDTO>();
				}
				if(cenovnik.getUsluge().size()==0) {

					System.out.println("nema usluga 2");
					return new ArrayList<VehicleDTO>();
				}
				Set<Usluga> usluge= cenovnik.getUsluge();
				double cena=0;
				
				ArrayList<Usluga> sortirane = new ArrayList<Usluga>();
				for(Usluga u : usluge) {
					if(u.getKategorija().toString().equals(kat)) {
						sortirane.add(u);
					}
				}
				sortirane.sort(Comparator.comparingInt(Usluga :: getPrekoTrajanja));
				int brojDana = daysBetween(rezervacija.getPickUp(), rezervacija.getDropOff());
				System.out.println("Broj dana je "+brojDana);
				
				
				cena= sortirane.get(0).getCena(); //uzima se najmanja cena
				for(int j = 0;j < sortirane.size();j++) {
						Usluga pom=sortirane.get(j);
						if(brojDana >= pom.getPrekoTrajanja()) {
							cena=pom.getCena();	
							System.out.println("Promenjena cena na "+cena);
						}
						
						
				}
				
				System.out.println("Cena po danu je "+cena);
				double ukupnaCena=brojDana*cena;
				
				if(dozvolaPickUp && dozvolaPutnici) {
					vozilo.setCena(ukupnaCena);
					ispunjeniUslovi.add(vozilo);
				}
		}
		for(Vehicle V: ispunjeniUslovi) {
			boolean dozvola = true;
			for(Discount dis: V.getPopusti()) {
				if(dis.getBodovi()>= brojBodova){
					Date pocetakPopusta = dis.getDatumod();
					Date krajPopusta = dis.getDatumdo();
					if(!(rezervacija.getPickUp().after(krajPopusta)||(rezervacija.getDropOff().before(pocetakPopusta)))) {
						dozvola = false;
					}
					System.out.println("Vozilo je na popustu za taj broj bodova");
					break;
				}
			}
			if(dozvola) {

				System.out.println("Dodato vozilo");
				VehicleDTO novoVozilo =new VehicleDTO(V.getId(), V.getMarka(), V.getModel(), V.getGodiste(), V.getGodiste(), V.getKategorija(), V.isImapopusta());
				novoVozilo.setCena(V.getCena());
				povratnaLista.add(novoVozilo);
			}
			
			
		}
		
		return povratnaLista;
	}	
	
	 public int daysBetween(Date d1, Date d2){
         return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
 }
		@RequestMapping(value="/oceniRent", 
				method = RequestMethod.POST,
				consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody RentACar oceniRent(@RequestBody Vehicle vozilo){		
		  System.out.println("Usao u oceni rent");
		System.out.println(vozilo);
		    Integer novaOcena =(int) vozilo.getOcena();
		    Long idRent = vozilo.getFilijala().getServis().getId();
			System.out.println("Id rent-a je "+idRent);
		    RentACar rent = servis.findOneById(idRent);
		    
			if(rent!=null) {
				System.out.println("Brojac jee"+ rent.getBrojac());
					int brojOcena=rent.getBrojac();
					System.out.println("Broj ocena je "+brojOcena+ " trenutna ocena je "+rent.getOcena());
					double ukOcena = rent.getOcena()*brojOcena;
					System.out.println("Pomnozena ocena "+ukOcena);
					ukOcena = ukOcena+novaOcena;
					System.out.println("Dodata ocena "+ukOcena);
					brojOcena++;
					ukOcena=(double)ukOcena/brojOcena;
					System.out.println("Podeljena ocena je "+ukOcena);
					
					rent.setBrojac(brojOcena);
					rent.setOcena(ukOcena);
					
					servis.saveRentACar(rent);
					
					return rent;
			}else {
				return null;
			}
		
		}	
}
