package rs.ftn.isa.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
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

import rs.ftn.isa.dto.VehicleDTO;
import rs.ftn.isa.model.Discount;
import rs.ftn.isa.model.PricelistRentCar;
import rs.ftn.isa.model.RezervacijaRentCar;

import rs.ftn.isa.model.StatusRezervacije;
import rs.ftn.isa.model.User;
import rs.ftn.isa.model.Usluga;
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
			return povratna;
		}


		@RequestMapping(value="/izmeniAuto", 
				method = RequestMethod.POST,
				consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Vehicle> izmeniVozilo(@RequestBody Vehicle nova){		
			
			System.out.println("Usao u izmeniVozilo");
			Long id=nova.getId();
			
			//prvo pronadjemo vozilo koje treba da se izmeni
			
			Vehicle vozilo = servis.findVehicleById(id);
			if(vozilo.equals(nova)) {
				System.out.println("Nista nije izmenjeno");
				vozilo.setModel(vozilo.getFilijala().getServis().getId().toString());
				return new ResponseEntity<>(vozilo, HttpStatus.OK);
			}
			
			vozilo.setGodiste(nova.getGodiste());
			vozilo.setMarka(nova.getMarka());
			vozilo.setModel(nova.getModel());
			vozilo.setKategorija(nova.getKategorija());
			vozilo.setSedista(nova.getSedista());
			
			
			try {
				servis.saveVehicle(vozilo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println();
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
			Long idRent = vozilo.getFilijala().getServis().getId();
			
			vozilo.setModel(idRent.toString());
			return new ResponseEntity<>(vozilo, HttpStatus.OK);

		}	
		@RequestMapping(value="/dodajRezervaciju/{podatak}", 
				method = RequestMethod.POST,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<RezervacijaRentCar> dodajRezervaciju(@PathVariable String podatak, @Context HttpServletRequest request){		
			System.out.println("usao u dodajRezervaciju "+podatak);
			
			User korisnik = (User)request.getSession().getAttribute("ulogovan");		
			
			String[] niz=podatak.split("=");
			Long glavnaRez=0L;
			if(niz.length==6) {
				glavnaRez=Long.parseLong(niz[5]);
			}
			Long idVozilo=Long.parseLong(niz[0]);
			Vehicle vozilo = servis.findVehicleById(idVozilo);
			
			
			String startDatum=niz[1];
			String[] datP=startDatum.split("-");
			
			int year=Integer.parseInt(datP[0]);
			//meseci u javi od 0
			int month=Integer.parseInt(datP[1])-1;
			int day=Integer.parseInt(datP[2]);
		
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month, day);
			Date datPreuzimanja = calendar.getTime();
			
			System.out.println("Daatum preuzimanja je "+datPreuzimanja);
			String krajDatum=niz[2];
			String[] krajP=krajDatum.split("-");
			
			 year=Integer.parseInt(krajP[0]);
			 //meseci u javi idu od 0
			 month=Integer.parseInt(krajP[1])-1;
			 day=Integer.parseInt(krajP[2]);
			
			 calendar.set(year, month, day);
			 Date datVracanje = calendar.getTime();
			 System.out.println("Daatum vracanja je "+datVracanje);
			
			 
			 Set<RezervacijaRentCar> rezervacije = vozilo.getRezervacije(); 
				boolean dozvolaPickUp = true;
				//prolazimo kroz sve rezervacije koje su napravljene za ovo vozilo
				for(RezervacijaRentCar R : rezervacije) {	
					if(R.getStatus()==StatusRezervacije.AKTIVNA) {
					
						Date rezDatPreuzimanje= R.getDatumPreuzimanja();
			            Date rezDatVracanja= R.getDatumVracanja();
			            
			            System.out.println(rezDatPreuzimanje.toString());
						System.out.println(rezDatVracanja.toString());
						
						//ako je datum preuzimanja vozila pre datuma vracanja iz rezervacije
								if(datPreuzimanja.compareTo(rezDatVracanja)<0) {
									System.out.println("provera1-> Datum preuzimanja je pre datuma vracanja iz liste rezervacije");
									 //datum vracanja auta posle datuma preuzimanja iz rezervacije, preklapaju se termini, vozilo nam ne odgovara
										if(datVracanje.compareTo(rezDatPreuzimanje)>0){
											dozvolaPickUp = false;
											System.out.println("provera2--> Datum vracanja je posle datuma preuzimanja iz rezervacije");
										
										}
								}
					}
					
							
				}
				
			System.out.println("dozvola jee"+dozvolaPickUp);
			
			if(dozvolaPickUp==false) {
				System.out.println("False je pick up");
					RezervacijaRentCar povr=null;
					return null;
			}
			System.out.println("ispod jee");	
				
				
				
			 
			 double cenaVozila=0;
			 String flag = niz[4];
			 if(flag.equals("0")) {
				 cenaVozila=Double.parseDouble(niz[3]);
		        	
			 }else {
				 Integer popust= Integer.parseInt(flag);
				int brojDana= daysBetween(datPreuzimanja,datVracanje);
				cenaVozila= Double.parseDouble(niz[3]);
				System.out.println("Pocetna cena je "+cenaVozila);
				cenaVozila = (double)cenaVozila*((double)(100-popust)/100);

				System.out.println("Pocetna cena 2 je "+cenaVozila);
				cenaVozila=(double)cenaVozila*brojDana;

				System.out.println("Pocetna cena3 je "+cenaVozila);
			 }
			 System.out.println("Usao u dodajRezervaciju u vozilo");
			
	        	
			//prvo pronadjemo vozilo koje treba da se izmeni
			RezervacijaRentCar rezervacija = new RezervacijaRentCar();
			rezervacija.setStatus(StatusRezervacije.AKTIVNA);
			rezervacija.setCena(cenaVozila);
			rezervacija.setGlavnRez(glavnaRez);
			rezervacija.setKorisnik(korisnik);
			korisnik.getRezRent().add(rezervacija);
			System.out.println("Datum preuzimanja je "+datPreuzimanja);
			System.out.println("Datum vracanja  je "+datVracanje);
			rezervacija.setDatumPreuzimanja(datPreuzimanja);
			rezervacija.setDatumVracanja(datVracanje);
			System.out.println(rezervacija);
	        			
			System.out.println("Nasao je vozilo ");
			rezervacija.setVozilo(vozilo);
			vozilo.getRezervacije().add(rezervacija);
			vozilo.setBroj(vozilo.getBroj()+1);
			System.out.println("Id od vozila je "+vozilo.getId());
			try {
				servis.saveVehicle(vozilo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return new ResponseEntity<>( HttpStatus.CONFLICT);

			}
			return new ResponseEntity<>(rezervacija, HttpStatus.OK);


		}
		
		
		@RequestMapping(value="/oceniVozilo/{podatak}", 
				method = RequestMethod.POST,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Vehicle> oceniVozilo(@PathVariable String podatak){		
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
					
					try {
						servis.saveVehicle(vozilo);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						return new ResponseEntity<>( HttpStatus.CONFLICT);
					}
					
					return new ResponseEntity<>(vozilo, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.OK);
			}
		
		}		
		@RequestMapping(value="/cekirajOcenu/{podatak}", 
				method = RequestMethod.POST,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Vehicle> cekirajOcenu(@PathVariable String podatak){		
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
					
					try {
						servis.saveVehicle(vozilo);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						return new ResponseEntity<>( HttpStatus.CONFLICT);
					}
					vozilo.setOcena((double)novaOcena);
					return new ResponseEntity<>(vozilo, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(vozilo, HttpStatus.OK);
			}
		
		}
		@RequestMapping(value="/otkaziVozilo/{podatak}", 
				method = RequestMethod.POST,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Vehicle> otkaziVozilo(@PathVariable String podatak){		
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
					
					try {
						servis.saveVehicle(vozilo);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						return new ResponseEntity<>(HttpStatus.CONFLICT);
					}
					
					return new ResponseEntity<>(vozilo,HttpStatus.OK);
			}else {

				return new ResponseEntity<>(vozilo,HttpStatus.OK);
			}
		
		}	
		
		
		@RequestMapping(value="/definisiPopust/{id}/pocetak/{pocetak}/kraj/{kraj}/bodovi/{bodovi}/procenat/{procenat}", 
				method = RequestMethod.POST,
				produces = MediaType.APPLICATION_JSON_VALUE
				)
		public ResponseEntity<Vehicle> popustZaVozilo(@PathVariable("id") Long id,
	            @PathVariable("pocetak") String pocetak,@PathVariable("kraj") String kraj,@PathVariable("bodovi") String bodovi,@PathVariable("procenat") String procenat){
			System.out.println("dosao u fu dobio"+id+" pocetak "+pocetak+" kraj "+kraj+" bodovi "+bodovi+" procenti "+procenat);
			int bod = Integer.parseInt(bodovi);
			int proc = Integer.parseInt(procenat);
			Vehicle vozilo = servis.findVehicleById(id);
			Discount popust = new Discount();
			String[] datIN=pocetak.split("-");
			int godina=Integer.parseInt(datIN[0]);
			//mjesec krece od 0
			int mjesec=Integer.parseInt(datIN[1])-1;
			int dan=Integer.parseInt(datIN[2]);
		
			Calendar calendar = Calendar.getInstance();
			calendar.set(godina, mjesec, dan);
			Date datumOd = calendar.getTime();
				
			
			System.out.println("Daatum je "+datumOd);
			String[] datOUT=kraj.split("-");
			
			 godina=Integer.parseInt(datOUT[0]);
			//mjesec krece od 0
			 mjesec=Integer.parseInt(datOUT[1])-1;
			 dan=Integer.parseInt(datOUT[2]);
			 calendar.set(godina, mjesec, dan);
			 Date datumDo = calendar.getTime();
			 popust.setDatumdo(datumDo);
			 popust.setDatumod(datumOd);
			 popust.setBodovi(bod);
			 popust.setVrijednost(proc);
			 //slucaj kada mjenja postojeci popust
			 Discount postojeciPopust = null;
			 boolean flag = false;
			 for(Discount disc:vozilo.getPopusti()) {
				 if(disc.getBodovi() == bod){
					 flag = true;
					 postojeciPopust = disc;
					 break;
				 }
			 }
			 if(flag) {
				 vozilo.getPopusti().remove(postojeciPopust);
				 postojeciPopust.setBodovi(popust.getBodovi());
				 postojeciPopust.setDatumod(popust.getDatumod());
				 postojeciPopust.setDatumdo(popust.getDatumdo());
				 postojeciPopust.setVrijednost(popust.getVrijednost());
			 }
			 popust.setVozilopopust(vozilo);
			 if(flag) {
				 vozilo.getPopusti().add(postojeciPopust);
			 }else {
				 vozilo.getPopusti().add(popust);
			 }
			 if(!vozilo.isImapopusta()) {
				 vozilo.setImapopusta(true);
			 }
			 
			 try {
				servis.saveVehicle(vozilo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}

				return new ResponseEntity<>(vozilo,HttpStatus.OK);
		}

		@RequestMapping(value="/getVoziloDiscount/{id}", 
				method = RequestMethod.GET,
				produces = MediaType.APPLICATION_JSON_VALUE
				)
		public @ResponseBody ArrayList<Discount> popustiSobe(@PathVariable("id") Long id){
			Vehicle vozilo = servis.findVehicleById(id);
			ArrayList<Discount> popusti = new ArrayList<Discount>();
			for(Discount dis:vozilo.getPopusti()) {
				popusti.add(dis);
			}
			return popusti;
		}
		
		@RequestMapping(value="/ukloniPopust/{slanje}", 
				method = RequestMethod.POST,
				produces = MediaType.APPLICATION_JSON_VALUE
				)
		public ResponseEntity<Vehicle> ukloniPopust(@PathVariable("slanje") String slanje){
			System.out.println("Dosao da ukloni popust");
			String[] pom = slanje.split("\\.");
			String voziloId = pom[1];
			Long id = Long.parseLong(voziloId);
			Vehicle vozilo = servis.findVehicleById(id);
			if(vozilo.getPopusti() == null) {
				vozilo.setImapopusta(false);
				
			}else {

				if(vozilo.getPopusti().size()== 0) {
					vozilo.setImapopusta(false);
				}
			}
			
			try {
				servis.saveVehicle(vozilo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return new ResponseEntity<>(HttpStatus.CONFLICT);
						
			}
			return new ResponseEntity<>(vozilo,HttpStatus.OK);
		}
		
		@RequestMapping(value="/getFast/{id}/start/{start}/end/{end}/grad/{grad}", 
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
		)
public @ResponseBody ArrayList<VehicleDTO> nadjiVozilaPopust(@PathVariable String id,@PathVariable String start,@PathVariable String end,@PathVariable String grad, @Context HttpServletRequest request) throws ParseException{
			ArrayList<VehicleDTO> ponuda = new ArrayList<VehicleDTO>();
			System.out.println("id "+id+" start " + start+" grad"+grad);
			User korisnik = (User)request.getSession().getAttribute("ulogovan");		
			if(korisnik.getBodovi()==0) {
				return ponuda;
			}
			System.out.println("Broj bodova je "+korisnik.getBodovi());
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
			Date pocetak = formater.parse(start);
			Date kraj = formater.parse(end);
			Calendar c = Calendar.getInstance();
			int bodovi = korisnik.getBodovi();
			List<Vehicle> svaVozila = servis.findAll();
			
			for(Vehicle V: svaVozila) {	
				System.out.println("Proverava vozilo "+V.getMarka());
				String idVRent=V.getFilijala().getServis().getId().toString();
				String gradV=V.getFilijala().getGrad();
				Discount popust = null;
					if(gradV.equals(grad) && idVRent.equals(id)) {
						System.out.println("Pripada filijali i ren");
						//da vidimo da li je na popustu
							if(V.getPopusti().size() != 0) {
								System.out.println("Postoje popusti");
								ArrayList<Discount> popusti =new ArrayList<Discount>();
								for(Discount DD : V.getPopusti()) {
										popusti.add(DD);
								}
								popusti.sort(Comparator.comparingInt(Discount :: getBodovi));
									for(Discount D : popusti) {
										System.out.println("Provera bodova "+D.getBodovi()+" i "+bodovi);
											//mora da ima dovoljnno bodova
											if(bodovi >= D.getBodovi()) {
												System.out.println("Ima dovoljno bodova");
												c.setTime(D.getDatumod());
												c.add(Calendar.DATE,-1);
												Date pocPopusta= c.getTime();
												pocPopusta.setHours(0);
												pocPopusta.setMinutes(0);
												pocPopusta.setSeconds(0);
												System.out.println(pocPopusta.toString());
												if(pocetak.after(pocPopusta) && kraj.before(D.getDatumdo())) {
														System.out.println("Ispunjava uslove");
														popust=D;
												}
											}
									}
							}
							
					}
				if(popust != null) {
					//treba proveriti da li je soba slobodna u tom periodu
					System.out.println("Vozilo poseduje popust");
					
					Set<RezervacijaRentCar> rezervacije = V.getRezervacije(); 
					
					//Provera 1 --> 
					//ako je nas datum preuzimanja pre datuma vracanja iz rezervacije,
					//Provera 2 --> 
					//onda gledamo da li je i datum vracanja naseg vozila nakon datuma preuzimanja iz
					//rezervacije
					
					boolean dozvolaPickUp = true;
					//prolazimo kroz sve rezervacije koje su napravljene za ovo vozilo
					for(RezervacijaRentCar R : rezervacije) {	
						if(R.getStatus()==StatusRezervacije.AKTIVNA) {
							//ako je datum preuzimanja vozila pre datuma vracanja iz rezervacije
							if(pocetak.before(R.getDatumVracanja())) {
								System.out.println("provera1-> Datum preuzimanja je pre datuma vracanja iz liste rezervacije");
								 //datum vracanja auta posle datuma preuzimanja iz rezervacije, preklapaju se termini, vozilo nam ne odgovara
									if(kraj.after(R.getDatumPreuzimanja())){
										dozvolaPickUp = false;
										System.out.println("provera2--> Datum vracanja je posle datuma preuzimanja iz rezervacije");
									}
							}	
						}
						
						
					}
					
					if(dozvolaPickUp) {
						int brojDana= daysBetween(pocetak, kraj);
						PricelistRentCar cenovnik = null;
						
						for(PricelistRentCar C : V.getFilijala().getServis().getCenovnici()) {
								if(C.isAktivan()){
									cenovnik=C;
									break;
								}
						}
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
							if(u.getKategorija().toString().equals(V.getKategorija().toString())) {
								sortirane.add(u);
							}
						}
						sortirane.sort(Comparator.comparingInt(Usluga :: getPrekoTrajanja));
						System.out.println("Broj dana je "+brojDana);
						
						
						cena= sortirane.get(0).getCena(); //uzima se najmanja cena
						for(int j = 0;j < sortirane.size();j++) {
								Usluga pom=sortirane.get(j);
								if(brojDana >= pom.getPrekoTrajanja()) {
									cena=pom.getCena();	
									System.out.println("Promenjena cena na "+cena);
								}
						}

						
						VehicleDTO novaPonuda = new VehicleDTO(V.getId(), V.getMarka(), V.getModel(), V.getGodiste(), V.getSedista(), V.getKategorija(), V.isImapopusta());
						novaPonuda.setCena(cena);
						novaPonuda.setPopust(popust.getVrijednost());
						novaPonuda.setBodovi(popust.getBodovi());
						ponuda.add(novaPonuda);
				
					}
					
				}
			}
			for(VehicleDTO VDTO:ponuda) {
				System.out.println("ispiss");
				System.out.println(VDTO);
					
			}
			return ponuda;
}
		 public int daysBetween(Date d1, Date d2){
	         return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	 }
}
