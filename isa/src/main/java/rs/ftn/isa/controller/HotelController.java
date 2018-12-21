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

import rs.ftn.isa.dto.HotelDTO;
import rs.ftn.isa.model.Category;
import rs.ftn.isa.model.CijenovnikSoba;
import rs.ftn.isa.model.Hotel;
import rs.ftn.isa.model.PricelistHotel;
import rs.ftn.isa.model.Room;
import rs.ftn.isa.model.Usluga;
import rs.ftn.isa.service.HotelService;
@RestController
@RequestMapping(value="api/hoteli")
public class HotelController {
		@Autowired
		private HotelService servis;
		
		@RequestMapping(value="/all", method = RequestMethod.GET)
		public List<Hotel> getAllHoteli(){	
			return  servis.findAll();
		}
		
		@RequestMapping(value="/getRooms/{id}", method = RequestMethod.GET)
		public Set<Room> getAllRooms(@PathVariable Long id){	
			Hotel pronadjeni = servis.findHotelById(id);
			if(pronadjeni == null) {
				System.out.println("Nisam pronasao hotel sa datim id");
				return null;
			}else{
				return pronadjeni.getSobe();
			}
		
		}
		
		@RequestMapping(value="/obrisiHotel/{id}", method = RequestMethod.POST)
		public  void obrisiHotel(@PathVariable Long id){
			System.out.println("brisanje hotel "+id);
			servis.removeHotel(id);
		
		}
		
		@RequestMapping(value = "/findById/{id}",
						method = RequestMethod.GET)
		public @ResponseBody Hotel findHotelById(@PathVariable Long id){
			System.out.println("find"  + id);
			
			Hotel pronadjeni = servis.findHotelById(id);
				if(pronadjeni == null) {
					System.out.println("Nisam pronasao hotel sa datim id");
					return pronadjeni;
				}else{
					return pronadjeni;
				}
		}
		
		@RequestMapping(value = "/findCijenovnik/{id}",
				method = RequestMethod.GET)
		public PricelistHotel findCijenovnik(@PathVariable Long id){
			System.out.println("find"  + id);
			
			Hotel pronadjeni = servis.findHotelById(id);
			if(pronadjeni.getCijenovnici() == null) {
				return null;
			}
			if(pronadjeni.getCijenovnici().size() == 0) {
				return null;
			}
			PricelistHotel aktivan = new PricelistHotel();
			for(PricelistHotel cc :pronadjeni.getCijenovnici()) {
				if(cc.isAktivan()) {
					aktivan = cc;
					break;
				}
			}
			
			return aktivan;
		}
		
		@RequestMapping(value="/newhotel", 
				method = RequestMethod.POST,
				consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody Hotel newHotel(@RequestBody HotelDTO hotel){		
		//jedinstven po nazivu
			 Hotel pom = servis.findHotelByNaziv(hotel.getNaziv());	
			 
			 if(pom == null) {
		//inicijalno ocjena je 0
				 Hotel newHotel = new Hotel(hotel.getNaziv(),hotel.getAdresa(),hotel.getOpis(),0);
				 servis.saveHotel(newHotel);
				 return newHotel; 
			 }else {
				 Hotel povratna = new Hotel(); 
				 return povratna;
				 
			 }
		}
	
		@RequestMapping(value="/all")
		public List<Hotel> getAllKorisnici(){		
			return  servis.findAll();
		}
		@RequestMapping(value="/test")
		public String vrati() {
			
			return "Uspesno";
		}		

		@RequestMapping(value="/addRoom/{id}", 
				method = RequestMethod.POST,
				consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody Hotel dodajSobu(@RequestBody Room room,@PathVariable Long id){		
				//jedinstven po nazivu
			 Hotel pom = servis.findHotelById(id);	
			 
			 if(pom == null) {
		
				 System.out.println(" ne postoji ti taj hotel ");
				 return null;
			 }
			 
			 Usluga u = new Usluga("cijena_noc",room.getCijena());
			 //kad dodaje sobu ne postoji cijenovnik za nju
			 Date datum = new Date();
			 CijenovnikSoba cijenovik = new CijenovnikSoba();			 
			 cijenovik.setDatum_primene(datum);
			 u.setCenesoba(cijenovik);
			 Set<Usluga> usluge = new HashSet<Usluga>();
			 usluge.add(u);
			 cijenovik.setAktivan(true);
			 cijenovik.setUsluge(usluge);
			 cijenovik.setSoba(room);
			 Set<CijenovnikSoba> cijenovnici = new HashSet<CijenovnikSoba>();
			 cijenovnici.add(cijenovik);
			 room.setCijenovnici(cijenovnici);
			 room.setHotel(pom);
			 pom.getSobe().add(room);
			 	//update hotela
			 servis.saveHotel(pom);
			 return pom;
				 
		}
		@RequestMapping(value="/promjenidodatnu/{slanje}", 
				method = RequestMethod.POST,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody Hotel changeDodatnu(@PathVariable String slanje){	
			System.out.println("dobio sam "+slanje);
			
			String[] parts = slanje.split("-");
			String uslugaID = parts[0];
			String cijenaStr = parts[1];
			String hotelID = parts[2];
			Long hotelId = Long.parseLong(hotelID);
			int cijena = Integer.parseInt(cijenaStr);
			Long uslugaId = Long.parseLong(uslugaID);
			Hotel pom = servis.findHotelById(hotelId);	
			   
			PricelistHotel aktivni = new PricelistHotel();
 			System.out.println("dosao je u if 1 kad je broj cj razlicit od 0");
 			for(PricelistHotel cc:pom.getCijenovnici()) {
 				if(cc.isAktivan()) {
 					aktivni = cc;
 					break;
 				}
 			}
			Usluga usluga = new Usluga();
 			for(Usluga uu:aktivni.getUsluge()) {
 				if(uu.getId() == uslugaId) {
 					usluga = uu;
 					break;
 				}
 				
 			}
 			//trenutno aktivni prestaje da bude aktivan jer smo izmjenili cijenu
 			pom.getCijenovnici().remove(aktivni);
 			aktivni.setAktivan(false);
 			pom.getCijenovnici().add(aktivni);
 			
 			//formiram novi cijenovnik
 			Date datum = new Date();
 			PricelistHotel cijenovnik = new PricelistHotel(datum,true);
 			aktivni.getUsluge().remove(usluga);
 			for(Usluga pomocna:aktivni.getUsluge()) {
 				pomocna.setCijene(cijenovnik);
 				cijenovnik.getUsluge().add(pomocna);
 			}
 			
 			usluga.setCena(cijena);
 			usluga.setCijene(cijenovnik);
 			cijenovnik.getUsluge().add(usluga);
 			
 			cijenovnik.setHotelski(pom);
 			pom.getCijenovnici().add(cijenovnik);
 			servis.saveHotel(pom);
 			return pom;
		}
		//dodavanje popusta na dodatnu uslugu koja ga jos uvijek nema
		@RequestMapping(value="/dodajPopust/{slanje}", 
				method = RequestMethod.POST,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody Hotel dodajPopust(@PathVariable String slanje){	
			System.out.println("dobio sam "+slanje);
			//idUsluga+"-"+vr+"-"+id;

			String[] parts = slanje.split("-");
			String uslugaID = parts[0];
			String popust = parts[1];
			String hotelID = parts[2];
			Long hotelId = Long.parseLong(hotelID);
			int popustINT = Integer.parseInt(popust);
			Long uslugaId = Long.parseLong(uslugaID);
			Hotel pom = servis.findHotelById(hotelId);	
			  System.out.println(" usluga je "+uslugaId);
			PricelistHotel aktivni = new PricelistHotel();
 			
			for(PricelistHotel cc:pom.getCijenovnici()) {
 				if(cc.isAktivan()) {
 					aktivni = cc;
 					break;
 				}
 			}
			Usluga usluga = new Usluga();
 			for(Usluga uu:aktivni.getUsluge()) {
 				if(uu.getId() == uslugaId) {
 					usluga = uu;
 					break;
 				}
 				
 			}
 			pom.getCijenovnici().remove(aktivni);
 			aktivni.getUsluge().remove(usluga);
 			
 			usluga.setKonfiguracija("da");
 			usluga.setPopust(popustINT);
 			aktivni.getUsluge().add(usluga);
 			
 			pom.getCijenovnici().add(aktivni); 	
 			servis.saveHotel(pom);
 			return pom;
		}
		
		
		@RequestMapping(value="/promjeniPopust/{slanje}", 
				method = RequestMethod.POST,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody Hotel changePopust(@PathVariable String slanje){	
			System.out.println("dobio sam "+slanje);
			
			String[] parts = slanje.split("-");
			String uslugaID = parts[0];
			String cijenaStr = parts[1];
			String hotelID = parts[2];
			Long hotelId = Long.parseLong(hotelID);
			int popust = Integer.parseInt(cijenaStr);
			Long uslugaId = Long.parseLong(uslugaID);
			Hotel pom = servis.findHotelById(hotelId);	
			   
			PricelistHotel aktivni = new PricelistHotel();
 			System.out.println("dosao je u if 1 kad je broj cj razlicit od 0");
 			for(PricelistHotel cc:pom.getCijenovnici()) {
 				if(cc.isAktivan()) {
 					aktivni = cc;
 					break;
 				}
 			}
			Usluga usluga = new Usluga();
 			for(Usluga uu:aktivni.getUsluge()) {
 				if(uu.getId() == uslugaId) {
 					usluga = uu;
 					break;
 				}
 				
 			}
 			

 			pom.getCijenovnici().remove(aktivni);
 			aktivni.getUsluge().remove(usluga);
 			usluga.setPopust(popust);
 			aktivni.getUsluge().add(usluga);
 			
 			pom.getCijenovnici().add(aktivni);
 			servis.saveHotel(pom);
 			return pom;
		}

		
		@RequestMapping(value="/changePrice/{slanje}", 
				method = RequestMethod.GET,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody Hotel changePrice(@PathVariable String slanje){		
			
			System.out.println("dobio sam string" + slanje);
			String[] parts = slanje.split("-");
			String roomID = parts[0];
			String newPrice = parts[1];
			String hotelID = parts[2];
			Long hotelId = Long.parseLong(hotelID);
			int cijena = Integer.parseInt(newPrice);
			Long roomId = Long.parseLong(roomID);
			   
			Hotel pom = servis.findHotelById(hotelId);	
			Room room = new Room();
		    Set<Room> sobe = pom.getSobe();
			for (Room soba : sobe) {
				if(soba.getId() == roomId) {
					room = soba;
					break;
				}
			}
			System.out.println("dobio sam dosao do 1");
			
			sobe.remove(room);
			Set<CijenovnikSoba> cijenovnici = room.getCijenovnici();
			CijenovnikSoba pomCJ = new CijenovnikSoba();
			for(CijenovnikSoba cs :cijenovnici){
				if(cs.isAktivan()) {
					pomCJ = cs;
					break;
				}
			}
			room.getCijenovnici().remove(pomCJ);
			pomCJ.setAktivan(false);
			room.getCijenovnici().add(pomCJ);
			
			 Usluga nova = new Usluga("cijena_noc",cijena);
			 Date datum = new Date();
			 CijenovnikSoba cijenovik = new CijenovnikSoba(datum,true);			  
			 cijenovik.setAktivan(true);
			 nova.setCenesoba(cijenovik);
			 
			 Set<Usluga> usluge = new HashSet<Usluga>();
			 usluge.add(nova);
			 cijenovik.setUsluge(usluge);
			 
			 room.setCijena(cijena);
			 cijenovik.setSoba(room);

				System.out.println("dobio sam dosao do 2");
			 cijenovnici.add(cijenovik);
			 room.setCijenovnici(cijenovnici);
			 room.setHotel(pom);
			 sobe.add(room);
			 pom.setSobe(sobe);
			 	//update hotela

				System.out.println("dobio sam dosao do 3");
			 servis.saveHotel(pom);
			
			return pom;
				 
		}
	
		@RequestMapping(value="/sacuvajKat/{id}", 
				method = RequestMethod.POST,
				consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody Category dodajKat(@RequestBody Category kat,@PathVariable Long id){		
			 Hotel pom = servis.findHotelById(id);	 
			 if(pom == null) {
				 System.out.println(" ne postoji ti taj hotel ");
				 return null;
			 }
			 	kat.setHotelKat(pom);
			 	pom.getKategorije().add(kat);
			 	servis.saveHotel(pom);
			 return kat;
		}
		
		
		@RequestMapping(value="/dodatnausluga/{id}", 
				method = RequestMethod.POST,
				consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody Hotel dodajDodatnuUslugu(@RequestBody Usluga u,@PathVariable Long id){		
			 	Hotel pom = servis.findHotelById(id);
			 	System.out.println("dosao da doda dodantu uslugu");
			 	
			 	if(pom.getCijenovnici()!= null) {
			 		
			 		if(pom.getCijenovnici().size() != 0) {
			 		
			 			PricelistHotel aktivni = new PricelistHotel();
			 			System.out.println("dosao je u if 1 kad je broj cj razlicit od 0");
			 			for(PricelistHotel cc:pom.getCijenovnici()) {
			 				if(cc.isAktivan()) {
			 					aktivni = cc;
			 					break;
			 				}
			 			}
			 		for(Usluga uu :aktivni.getUsluge()) {
			 			if(uu.getNaziv().equals(u.getNaziv())) {
			 				//postoji vec usluga sa datim nazivom za gresku
			 				System.out.println("postoji vec ta usluga");
			 				pom.setOpis("Usluga");
			 				return pom;
			 			}
			 		}
			 		u.setKonfiguracija("ne");
			 		u.setCijene(aktivni);
			 		aktivni.getUsluge().add(u);
			 	
			 		//izmjeni cijenovnik kod hotela
			 		pom.getCijenovnici().remove(aktivni);
			 		pom.getCijenovnici().add(aktivni);
			 		servis.saveHotel(pom);
			 		return pom;
			 		}else{
			 			//ne postoje cijenovnici
				 		PricelistHotel aktivni = new PricelistHotel();
				 		Date datum = new Date();
				 		aktivni.setDatum_primene(datum);
				 		aktivni.setAktivan(true);
				 		u.setKonfiguracija("ne");
				 		
				 		u.setCijene(aktivni);
				 		
				 		Set<Usluga> usluge = new HashSet<Usluga>();
					 	usluge.add(u);
					 	aktivni.setUsluge(usluge);
					 	
				 		aktivni.setHotelski(pom);
				 		Set<PricelistHotel> cijenovnici = new HashSet<PricelistHotel>();
				 		cijenovnici.add(aktivni);
				 		
				 		pom.setCijenovnici(cijenovnici);
				 		servis.saveHotel(pom);
				 		return pom;

			 			
			 		}
			 	}else {
			 		//ne postoje cijenovnici
			 		
			 		PricelistHotel aktivni = new PricelistHotel();
			 		Date datum = new Date();
			 		aktivni.setDatum_primene(datum);
			 		aktivni.setAktivan(true);
			 		u.setKonfiguracija("ne");
			 		
			 		u.setCijene(aktivni);
			 		
			 		Set<Usluga> usluge = new HashSet<Usluga>();
				 	usluge.add(u);
				 	aktivni.setUsluge(usluge);
				 	
			 		aktivni.setHotelski(pom);
			 		Set<PricelistHotel> cijenovnici = new HashSet<PricelistHotel>();
			 		cijenovnici.add(aktivni);
			 		pom.setCijenovnici(cijenovnici);
			 		servis.saveHotel(pom);
			 		return pom;
			 	}
			 	
		}
		
		@RequestMapping(value="/getUsluge/{id}", 
				method = RequestMethod.GET,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody ArrayList<Usluga> vratiUsluge(@PathVariable Long id){	
			Hotel pom = servis.findHotelById(id);
		 	System.out.println("dosao da doda dodantu uslugu");
		 	ArrayList<Usluga> usluge = new ArrayList<Usluga>(); 
		 	PricelistHotel cijenovnik = new PricelistHotel();
		 	System.out.println("hotel je "+pom.getId());
		 	
		 	if(pom.getCijenovnici()!= null) {
		 		System.out.println("postoje  neki cj");
				 
		 		if(pom.getCijenovnici().size() != 0) {
		 			//postoje dodatne usluge u hotelu
		 			for(PricelistHotel cc:pom.getCijenovnici()) {
		 				if(cc.isAktivan()) {
		 					cijenovnik = cc;
		 					break;
		 				}
		 			
		 			}
		 			System.out.println("postoji cjenovnik dodatnih usluga");
		 				for(Usluga uu:cijenovnik.getUsluge()) {
		 					usluge.add(uu);
		 				}
		 				return usluge;
		 		}
		 	}		 		
		 	
			return null;
		}
		
		
		@RequestMapping(value="/getDodatneUsluge/{id}", 
				method = RequestMethod.GET,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody ArrayList<Usluga> vratiDodatneUsluge(@PathVariable Long id){	
			Hotel pom = servis.findHotelById(id);
		 	ArrayList<Usluga> usluge = new ArrayList<Usluga>(); 
		 	PricelistHotel cijenovnik = new PricelistHotel();
		 	if(pom.getCijenovnici() == null) {
		 		System.out.println("usao u null");
		 		return null;
		 	}
		 	if(pom.getCijenovnici().size() == 0) {
		 		System.out.println("usao u size jednako 0");	
		 		return null;
		 	}
		 	
		 	if(pom.getCijenovnici()!= null) {
		 		System.out.println("postoje  neki cj");
				 
		 		if(pom.getCijenovnici().size() != 0) {
		 			//postoje dodatne usluge u hotelu
		 			for(PricelistHotel cc:pom.getCijenovnici()) {
		 				if(cc.isAktivan()) {
		 					cijenovnik = cc;
		 					break;
		 				}
		 			
		 			}
		 			System.out.println("postoji cjenovnik dodatnih usluga");
		 				for(Usluga uu:cijenovnik.getUsluge()) {
		 					if(uu.getKonfiguracija().equals("ne") ) {
		 						usluge.add(uu);
		 					}
		 				}
		 				return usluge;
		 		}
		 	}		 		
		 	
			return null;
		}
		
		@RequestMapping(value="/changehotel/{id}", 
				method = RequestMethod.POST,
				consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody Hotel changeHotel(@RequestBody Hotel hotel,@PathVariable Long id){		
			Hotel old = servis.findHotelById(id);
			System.out.println("dosao da izmjeni hotel");
			List<Hotel> hoteli = servis.findAll();
			//jedinstveni su hoteli po nazivu
			for(Hotel hh :hoteli) {
				if(hh.getId() != old.getId()) {
					if(hh.getNaziv().equals(hotel.getNaziv())) {
						old.setOpis("naziv");
						return old;
					}
				}
				
			}
			old.setNaziv(hotel.getNaziv());
			old.setAdresa(hotel.getAdresa());
			old.setOpis(hotel.getOpis());
			servis.saveHotel(old);
			System.out.println("sacuvao hotel");
			return old;
		}	
		
		
		@RequestMapping(value="/getKonfiguracije/{id}", 
				method = RequestMethod.GET,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody ArrayList<Category> vratiKonfiguracije(@PathVariable Long id){	
			Hotel pom = servis.findHotelById(id);
		 	
			ArrayList<Category> kat = new ArrayList<Category>(); 
			for(Category kk:pom.getKategorije()) {
				kat.add(kk);
				System.out.println("id je " + kk.getId());
			}
		 			 
			return kat;
		}
		
		

}
