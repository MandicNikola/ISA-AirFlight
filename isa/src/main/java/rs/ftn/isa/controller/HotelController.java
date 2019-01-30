package rs.ftn.isa.controller;


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

import rs.ftn.isa.dto.HotelDTO;
import rs.ftn.isa.dto.ReservationHotelDTO;
import rs.ftn.isa.dto.RoomDTO;
import rs.ftn.isa.model.Category;
import rs.ftn.isa.model.CijenovnikSoba;
import rs.ftn.isa.model.Hotel;
import rs.ftn.isa.model.PricelistHotel;
import rs.ftn.isa.model.RentACar;
import rs.ftn.isa.model.RezervacijaHotel;
import rs.ftn.isa.model.Room;
import rs.ftn.isa.model.StatusRezervacije;
import rs.ftn.isa.model.User;
import rs.ftn.isa.model.Usluga;
import rs.ftn.isa.model.Vehicle;
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
		
		//vraca hotele sortirane po nekom kriterijumu
		@RequestMapping(value="/sort/{uslov}", method = RequestMethod.GET)
		public List<Hotel> getSortedHotels(@PathVariable String uslov){		
			
			System.out.println("Uslov je "+uslov);
			
			List<Hotel> svi = servis.findAll();
			List<Hotel> sortiranaLista=new ArrayList<Hotel>();
			
			if(uslov.equals("NameA")) {
				//sortiraj po nazivu od A-Z
				System.out.println("Sortiraj po imenu rastuce");
				Collections.sort(svi, Hotel.HotelNameComparator);
				for(Hotel H : svi) {
					System.out.println(H.getNaziv());
					sortiranaLista.add(H);
				}
				
			}else if(uslov.equals("NameD")) {
				System.out.println("Sortiraj po imenu opadajuce");
				//sortiraj po nazivu od Z-A
				Collections.sort(svi, Hotel.HotelNameComparator);
				for(int i=svi.size()-1; i>=0; i--) {
					System.out.println(svi.get(i).getNaziv());
					sortiranaLista.add(svi.get(i));
				}
				
			}else if(uslov.equals("CityA")) {
				//sortiraj po gradu od A-Z
				System.out.println("Sortiraj po gradu rastuce");

				Collections.sort(svi, Hotel.HotelCityComparator);
				for(Hotel H : svi) {
					System.out.println(H.getAdresa());
					sortiranaLista.add(H);
				}
			}else {
				//sortiraj po gradu od Z-A
				System.out.println("Sortiraj po gradu rastuce");

				Collections.sort(svi, Hotel.HotelCityComparator);
				for(int i=svi.size()-1; i>=0; i--) {
					System.out.println(svi.get(i).getAdresa());
					sortiranaLista.add(svi.get(i));
				}
			}
			
			
			return sortiranaLista;
		}
		@RequestMapping(value="/getRooms/{id}", method = RequestMethod.GET)
		public Set<Room> getAllRooms(@PathVariable Long id){	
			Hotel pronadjeni = servis.findHotelById(id);
			System.out.println("id hoteal " +id);
			for(Room ss:pronadjeni.getSobe()) {
				System.out.println("id so "+ss.getId());
				
			}
				return pronadjeni.getSobe();
			
		
		}
		//sobe za def popusta za admina ssitema
		@RequestMapping(value="/getRoomsForDiscount/{id}", method = RequestMethod.GET)
		public ArrayList<RoomDTO> getAllRoomsForDiscount(@PathVariable Long id){	
			Hotel pronadjeni = servis.findHotelById(id);
			System.out.println("id hoteal " +id);
			ArrayList<RoomDTO> sobeDTO = new ArrayList<RoomDTO>();
			//public RoomDTO(Long id, String tip, int kapacitet, int sprat,boolean imapopust) 
			
			for(Room ss:pronadjeni.getSobe()) {
				RoomDTO room = new RoomDTO(ss.getId(),ss.getTip(),ss.getKapacitet(),ss.getSprat(),ss.isImapopusta());
				sobeDTO.add(room);
			}
				return sobeDTO;
		
		}
		//metoda koja vraca tipove soba u odredjenom hotelu - jednokrevetna,dvokrevetna itd
		@RequestMapping(value="/vratiTipoveSoba/{id}", method = RequestMethod.GET)
		public ArrayList<Integer> getTipovaSoba(@PathVariable Long id){	
			Hotel pronadjeni = servis.findHotelById(id);
			Set<Room> sobe = pronadjeni.getSobe();
			ArrayList<Integer> tipovi = new ArrayList<Integer>();
			for(Room soba :sobe) {
				int brojkreveta = soba.getKapacitet();
				if(!tipovi.contains(brojkreveta)) {
					tipovi.add(brojkreveta);
				}
			}
			if(tipovi.size() == 0) {
				return new ArrayList<Integer>();
			}
			
			return tipovi;
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
		public ResponseEntity<HotelDTO> newHotel(@RequestBody HotelDTO hotel){		
				//jedinstven po nazivu
			 Hotel pom = servis.findHotelByNaziv(hotel.getNaziv());	
			 
			 if(pom == null) {
		//inicijalno ocjena je 0
				 Hotel newHotel = new Hotel(hotel.getNaziv(),hotel.getAdresa(),hotel.getOpis(),0);
				 servis.saveHotel(newHotel);
				 return new ResponseEntity<>(new HotelDTO(newHotel), HttpStatus.CREATED); 
			 }else {
				 Hotel povratna = new Hotel(); 
				 return  new ResponseEntity<>(new HotelDTO(povratna), HttpStatus.CREATED);
				 
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
			
			Hotel pom = servis.findHotelById(id);	
			 
			 if(pom == null) {
		
				 System.out.println(" ne postoji hotel ");
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
			double cijena = Double.parseDouble(cijenaStr);
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
			double cijena = Double.parseDouble(newPrice);
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
		//vrati konf za izmjenu sobe
		@RequestMapping(value="/getKonf/{pom}", 
				method = RequestMethod.GET,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody ArrayList<String> vratiKonf(@PathVariable String pom){	
			
			String[] parts = pom.split("-");
			String roomID = parts[0];
			String hotelID = parts[1];
			Long idR = Long.parseLong(roomID);
			Long idH = Long.parseLong(hotelID);
			
			Hotel hotel = servis.findHotelById(idH);
		 	
			ArrayList<String> kat = new ArrayList<String>(); 
			String trenutni = "";
			for(Room soba:hotel.getSobe()) {
				if(soba.getId() == idR) {
					 trenutni= soba.getTip();
					break;
				}
				
			}
			kat.add(trenutni);
			for(Category kk:hotel.getKategorije()) {
				if(!kk.getNaziv().equals(trenutni)) {
				kat.add(kk.getNaziv());
				}
			}
		 		
			for(String ss:kat) {
				System.out.println("string " + ss);
			}
			return kat;
		}
		
		// da li postoje ponude za unesenu rezervaciju bez hoteala 

		@RequestMapping(value="/vratiPonude/{id}", 
				method = RequestMethod.POST,
				consumes = MediaType.APPLICATION_JSON_VALUE)
		public ArrayList<RoomDTO> vratiPonude(@RequestBody ReservationHotelDTO rez,@PathVariable Long id){		
			Hotel hotel = servis.findHotelById(id);
			
			System.out.println("dosao da vrati ponude " + rez.getBrojKreveta());
			ArrayList<Room> sobe = new ArrayList<Room>();
			
			for(Room soba:hotel.getSobe()) {
				//provjera za sobu da li zadovoljava uslove
				Room room = soba;
				Set<RezervacijaHotel> rezervacije = room.getRezervacije(); 
				
				//moram provjeriti prvi slucaj: da li je check in > od krajeva svih rezervacija koje postoje za tu sobu
				boolean odobrenCheckIN = true;
				for(RezervacijaHotel pom:rezervacije) {	
					
					if(rez.getCheckIn().compareTo(pom.getDatumOdlaska())<=0) {
						System.out.println("nije odobren check in");
						odobrenCheckIN = false;
						break;
					}
				}
				//odobren check in,provjeravam check out ..da li je check out < od pocetaka svih rezervacija koje postoje za datu sobu
				boolean odobrenCheckOUT= true;
				
					for(RezervacijaHotel pom:rezervacije) {	
						
						if(rez.getCheckOut().compareTo(pom.getDatumDolaska())>=0) {
							System.out.println("nije odobren check out");
							odobrenCheckOUT = false;
							break;
						}
					}
					
				
				//odobrena je soba
				if(odobrenCheckIN == true || odobrenCheckOUT == true) {
					System.out.println("odobrena soba");
					sobe.add(soba);
				}
				
			}
			
			//ubacim samo sobe koje su trazenog tipa(jednokrevetne,dvokrevetne itd)
			ArrayList<RoomDTO> pronadjeneSobe = new ArrayList<RoomDTO>();
			for(Room soba:sobe) {
				if(soba.getKapacitet() == rez.getBrojKreveta()) {
					System.out.println("ubacio sobu");
					pronadjeneSobe.add(new RoomDTO(soba.getId(), soba.getTip(), soba.getOcjena(), soba.getSprat(), soba.getKapacitet(), soba.getCijena(), soba.getBalkon()));
				}
				
			}
			
		
			//provjera da li imam dovoljan broj soba
			if(pronadjeneSobe.size() < rez.getBrojSoba()) {
				System.out.println("nedovoljan broj soba");
				
				 return new ArrayList<RoomDTO>();
			}
			int suma = 0;
			for(RoomDTO sobica:pronadjeneSobe) {
				suma += sobica.getKapacitet();
			}
			
			//provjera da li imam dovoljno kapaciteta
			if(rez.getBrojLjudi() > suma) {
				System.out.println("nedovoljan kapacitet");
				
				 return new ArrayList<RoomDTO>();
			}

			int dani = daysBetween(rez.getCheckIn(),rez.getCheckOut());
			int pom = 0;
			for(RoomDTO ss:pronadjeneSobe) {
				double ukupnaCijena = ss.getCijena()*dani;
				pronadjeneSobe.get(pom).setCijena(ukupnaCijena);
				pom++;
			}
			return pronadjeneSobe;
		}	
		//metoda koja formira rezervaciju
		@RequestMapping(value="/rezervisi/{info}/sobe/{nizSoba}/nizUsluga/{listaUsl}/idHotela/{id}", 
				method = RequestMethod.POST,
				produces = MediaType.APPLICATION_JSON_VALUE
				)
		public @ResponseBody RezervacijaHotel rezervacija(@PathVariable("info") String info,
	            @PathVariable("nizSoba") String nizSoba,@PathVariable("listaUsl") String listaUsl,@PathVariable("id") Long id,@Context HttpServletRequest request){
			System.out.println("uusao u rezervisi u rezervaciji");
			System.out.println("info "+info);
			System.out.println("nizSoba "+nizSoba);
			System.out.println("listaUsl "+listaUsl);
			
			User korisnik = (User)request.getSession().getAttribute("ulogovan");		
			
			//info 2018-12-30*2019-01-01*2
			//nizSoba 4
			//listaUsl 3
			String[] infoPom = info.split("\\*");
			String checkIN = infoPom[0];
			String checkOUT = infoPom[1];
			String broj = infoPom[2];
			int brLjudi = Integer.parseInt(broj);
			ArrayList<String> indexSoba = new ArrayList<String>() {};
			
			if(nizSoba.contains(".")) {
				String[] pomocna = nizSoba.split("\\.");
				for(int i = 0;i<pomocna.length;i++) {
					indexSoba.add(pomocna[i]);
				}
			}else {
				indexSoba.add(nizSoba);
			}
			boolean imaUsluga = true;
			if(listaUsl.equals("nema")) {
				imaUsluga = false;
			}
			ArrayList<String> indexUsluga = new ArrayList<String>() {};
			
			if(imaUsluga == true) {
				if(listaUsl.contains(".")) {
					String[] pomocna = listaUsl.split("\\.");
					for(int i = 0;i<pomocna.length;i++) {
						indexUsluga.add(pomocna[i]);
					}
				}else {
						indexUsluga.add(listaUsl);
				}
			}
			
			String[] datIN=checkIN.split("-");
			
			int godina=Integer.parseInt(datIN[0]);
			//mjesec krece od 0
			int mjesec=Integer.parseInt(datIN[1])-1;
			int dan=Integer.parseInt(datIN[2]);
		
			Calendar calendar = Calendar.getInstance();
			calendar.set(godina, mjesec, dan);
			Date datumCheckIn = calendar.getTime();
				
			
			System.out.println("Daatum je "+datumCheckIn);
			String[] datOUT=checkOUT.split("-");
			
			 godina=Integer.parseInt(datOUT[0]);
			//mjesec krece od 0
			 mjesec=Integer.parseInt(datOUT[1])-1;
			 dan=Integer.parseInt(datOUT[2]);
			 calendar.set(godina, mjesec, dan);
			Date datumCheckOut = calendar.getTime();
			RezervacijaHotel povratna = new RezervacijaHotel();
			
			povratna.setDatumDolaska(datumCheckIn);
			povratna.setDatumOdlaska(datumCheckOut);
			
			System.out.println("Daatum je "+datumCheckOut);
			Hotel hotel = servis.findHotelById(id);
			ArrayList<Room> sobe = new ArrayList<Room>();
			for(int i =0;i<indexSoba.size();i++) {
				Long idSobe = Long.parseLong(indexSoba.get(i));
				
				for(Room ss:hotel.getSobe()) {
					if(ss.getId() == idSobe) {
						Room sobica = ss;
						//povecaj broj aktivnih rez
						System.out.println("broj rez je "+sobica.getBrojRezervacija());
						int brojRez = ss.getBrojRezervacija()+1;
						sobica.setBrojRezervacija(brojRez);
						sobe.add(sobica);
						break;
					}
				}
			}
			
			ArrayList<Usluga> usluge = new ArrayList<Usluga>();
			ArrayList<Usluga> popusti = new ArrayList<Usluga>();
			
			//preuzmi aktivni cjenovnik
			PricelistHotel aktivni = null;
			for(PricelistHotel ph:hotel.getCijenovnici()) {
				if(ph.isAktivan()) {
					aktivni = ph;
					break;
				}
				
			}
			//ako je selektovao usluge
			if(imaUsluga) {
				for(int i = 0;i<indexUsluga.size();i++) {
					Long idUsluge = Long.parseLong(indexUsluga.get(i));
					
					for(Usluga usl:aktivni.getUsluge()) {
						if(usl.getId() ==idUsluge) {
							usluge.add(usl);
							if(usl.getKonfiguracija().equals("da")) {
								popusti.add(usl);
							}
							break;
						}
					}
				}
				
			}
			
			if(popusti.size() != 0) {
				popusti.sort(Comparator.comparingDouble(Usluga :: getPopust));	
			}
			
			RezervacijaHotel rez = new RezervacijaHotel();
			rez.setDatumDolaska(datumCheckIn);
			rez.setDatumOdlaska(datumCheckOut);
			int dani = daysBetween(datumCheckIn, datumCheckOut);
			double cijena = 0;
			//popust na cijenu sobe dobija
			for(int i = 0;i<sobe.size();i++) {
				cijena += (dani*sobe.get(i).getCijena());
			}
			if(imaUsluga) {
				for(int i = 0;i<usluge.size();i++) {
					cijena += (dani*brLjudi*usluge.get(i).getCena());
				}
			}
			if(popusti.size() == 0) {
				rez.setCijena(cijena);
				povratna.setCijena(cijena);
			}else {
				System.out.println("usao ovdje "+cijena);
				double popustMax = popusti.get(0).getPopust();
				rez.setCijena(cijena*((100-popustMax)/100));
				povratna.setCijena(cijena*((100-popustMax)/100));
			}
			if(korisnik != null) {
			rez.setUserHotel(korisnik);
			korisnik.getRezHotela().add(rez);
				System.out.println("dodao korisnika u rez "+korisnik.getIme());
			}
			for(Room room:sobe) {
				Room soba = room;
				hotel.getSobe().remove(room);
				Set<RezervacijaHotel> rezSobe = room.getRezervacije();
				rezSobe.add(rez);				
				System.out.println("Dodao je rezervaciju "+rez.getId());
				soba.setRezervacije(rezSobe);
				hotel.getSobe().add(soba);
			}
			if(imaUsluga) {
				hotel.getCijenovnici().remove(aktivni);
				
				for(Usluga usl:usluge) {
					aktivni.getUsluge().remove(usl);
					Usluga usluga = usl;
					Set<RezervacijaHotel> rezUsluge = usluga.getRezHotela();
					rezUsluge.add(rez);
					usluga.setRezHotela(rezUsluge);
					aktivni.getUsluge().add(usluga);
				}
				hotel.getCijenovnici().add(aktivni);
			}
			servis.saveHotel(hotel);
			System.out.println("cijena je "+povratna.getCijena());
		
			return povratna;
		
		}
		
		 public int daysBetween(Date d1, Date d2){
	         return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	 }
		 //metoda koja radi pretragu hotela sa pocetne strane
		 @RequestMapping(value="/pronadjiHotele/{info}",
					method = RequestMethod.POST,
					consumes = MediaType.APPLICATION_JSON_VALUE,
					produces = MediaType.APPLICATION_JSON_VALUE)
public ArrayList<Hotel> pronadjiHotele(@RequestBody ReservationHotelDTO rez,@PathVariable String info){
			
			 System.out.println("Usao u pronadji hotele "+info);
			 ArrayList<Hotel> povratna = new ArrayList<Hotel>();
			 List<Hotel> hoteli = servis.findAll();
			 for(Hotel hotel:hoteli) {
				if(hotel.getAdresa().toLowerCase().equals(info.toLowerCase()) || hotel.getNaziv().toLowerCase().contains(info.toLowerCase())) {
					for(Room soba:hotel.getSobe()) {
						//provjera za sobu da li zadovoljava uslove
						Room room = soba;
						Set<RezervacijaHotel> rezervacije = room.getRezervacije(); 
						
						//moram provjeriti prvi slucaj: da li je check in > od krajeva svih rezervacija koje postoje za tu sobu
						boolean odobrenCheckIN = true;
						for(RezervacijaHotel pom:rezervacije) {	
							
							if(rez.getCheckIn().compareTo(pom.getDatumOdlaska())<=0) {
								System.out.println("nije odobren check in");
								odobrenCheckIN = false;
								break;
							}
						}
						//odobren check in,provjeravam check out ..da li je check out < od pocetaka svih rezervacija koje postoje za datu sobu
						boolean odobrenCheckOUT= true;
						
							for(RezervacijaHotel pom:rezervacije) {	
								
								if(rez.getCheckOut().compareTo(pom.getDatumDolaska())>=0) {
									System.out.println("nije odobren check out");
									odobrenCheckOUT = false;
									break;
								}
							}
							
						
						//odobrena je soba
						if(odobrenCheckIN == true || odobrenCheckOUT == true) {
							System.out.println("odobrena soba pa i hotel");
							povratna.add(hotel);
							break;
						}
						
					}
					
				} 
				 
			 }
			 	if(povratna.size() == 0) {
			 		return new ArrayList<Hotel>();
			 	}else {
			 		return povratna;
			 	}
		 }
	@RequestMapping(value="/oceniHotel/{podatak}", 
					method = RequestMethod.POST,
					produces = MediaType.APPLICATION_JSON_VALUE )
	public Hotel oceniHotel(@PathVariable String podatak){
		 System.out.println("Usao u oceni hotel");
		String[] niz = podatak.split("=");
		Integer ocena = Integer.parseInt(niz[1]);
		String idRez=niz[0];
		
		List<Hotel> sviHoteli = servis.findAll();
		RezervacijaHotel rezervacija = null;
		Hotel hotel = null;	 
		
		for(Hotel H : sviHoteli) {
			for(Room R : H.getSobe()) {
				   for(RezervacijaHotel rez: R.getRezervacije()) {
					    String idRezervacije = rez.getId().toString();
					    	if(idRez.equals(idRezervacije)) {
					    		System.out.println("Pronadjena soba sa tom rezervacijom");
					    		hotel=H;
					    		rezervacija=rez;
					    		break;
					    	}
				   }
			}
		}
		//treba promeniti ocenu u hotelu

		if(hotel!=null) {
				System.out.println("Brojac jee"+ hotel.getBrojac());
					int brojOcena=hotel.getBrojac();
					System.out.println("Broj ocena je "+brojOcena+ " trenutna ocena je "+hotel.getOcena());
					double ukOcena = hotel.getOcena()*brojOcena;
					System.out.println("Pomnozena ocena "+ukOcena);
					ukOcena = ukOcena+ocena;
					System.out.println("Dodata ocena "+ukOcena);
					brojOcena++;
					ukOcena=(double)ukOcena/brojOcena;
					System.out.println("Podeljena ocena je "+ukOcena);
					
					hotel.setBrojac(brojOcena);
					hotel.setOcena(ukOcena);
					
					//potrebno je podesiti u rezervaciji da je hotel vec ocenjen
					ArrayList<Room> sveSobe = new ArrayList<Room>();
					for(Room rr : hotel.getSobe()) {
						sveSobe.add(rr);
					}
					
					for(Room room : sveSobe) {
							Set<RezervacijaHotel> rezSobe = room.getRezervacije();
							RezervacijaHotel rezIzmena = null;
							//prolazimo kroz rezervacije sobe da vidimo da li je
							//ta rezervacija koja treba da se izmeni vezana za sobu
								for(RezervacijaHotel rH : rezSobe) {
										String idrH = rH.getId().toString();
										if(idrH.equals(idRez)) {
											rezIzmena=rH;
											System.out.println("Pronadjena rez u sobi "+room.getId()+" a hotel je "+hotel.getNaziv());
											break;
										}
								}
								if(rezIzmena!=null) {
									hotel.getSobe().remove(room);
									room.getRezervacije().remove(rezIzmena);
									rezIzmena.setOcenjenHotel(true);
									room.getRezervacije().add(rezIzmena);
									hotel.getSobe().add(room);
								}
					}
					servis.saveHotel(hotel);
					
					return hotel;

		}else {
			return null;
		}
		 
	}
		@RequestMapping(value="/oceniSobu/{podatak}", 
				method = RequestMethod.POST,
				produces = MediaType.APPLICATION_JSON_VALUE )
	public Hotel oceniSobu(@PathVariable String podatak){
	 System.out.println("Usao u oceni sobu");
	 //podatak sadrzi idRez = idSobe = ocena
	String[] niz = podatak.split("=");
	Integer ocena = Integer.parseInt(niz[2]);
	String idRez = niz[0];
	String idSobe =  niz[1];
	
	
	List<Hotel> sviHoteli = servis.findAll();
	RezervacijaHotel rezervacija = null;
	Room soba = null;
	Hotel hotel = null;	 
	
	for(Hotel H : sviHoteli) {
		for(Room R : H.getSobe()) {
			   String idS=R.getId().toString();
			   if(idS.equals(idSobe)) {
				   soba=R;
				   hotel = R.getHotel();
				   break;
			   }
		}
	}
	
	if(soba!=null) {
		
		Set<RezervacijaHotel> rezSobe = soba.getRezervacije();
		//trazimo rezervaciju u kojoj je soba ocenjena
		for(RezervacijaHotel rH : rezSobe) {
					String idrH = rH.getId().toString();
					if(idrH.equals(idRez)) {
						rezervacija=rH;
						System.out.println("Pronadjena rez u sobi "+soba.getId()+" a hotel je "+hotel.getNaziv());
						break;
					}
	   }
		if(rezervacija==null) {
			return null;
		}

		hotel.getSobe().remove(soba);

		System.out.println("Brojac jee"+ soba.getBrojac());
		int brojOcena=soba.getBrojac();
		System.out.println("Broj ocena je "+brojOcena+ " trenutna ocena je "+soba.getOcjena());
		double ukOcena = soba.getOcjena()*brojOcena;
		System.out.println("Pomnozena ocena "+ukOcena);
		ukOcena = ukOcena+ocena;
		System.out.println("Dodata ocena "+ukOcena);
		brojOcena++;
		ukOcena=(double)ukOcena/brojOcena;
		System.out.println("Podeljena ocena je "+ukOcena);
		
		soba.setBrojac(brojOcena);
		soba.setOcjena(ukOcena);
		
		soba.getOcenjeneRezervacije().add(rezervacija);
		hotel.getSobe().add(soba);

		servis.saveHotel(hotel);
				
		return hotel;
	
	}else {
		return null;
	}
	 
	}
			 
		@RequestMapping(value="/otkaziHotel/{idRez}", 
				method = RequestMethod.POST,
				produces = MediaType.APPLICATION_JSON_VALUE )
public Hotel otkaziHotel(@PathVariable String idRez){
	System.out.println("Usao u otkazi hotel");
	
	List<Hotel> sviHoteli = servis.findAll();
	Hotel hotel = null;	 
	
	for(Hotel H : sviHoteli) {
		for(Room R : H.getSobe()) {
			   for(RezervacijaHotel rez: R.getRezervacije()) {
				    String idRezervacije = rez.getId().toString();
				    	if(idRez.equals(idRezervacije)) {
				    		System.out.println("Pronadjena soba sa tom rezervacijom");
				    		hotel=H;
				    		break;
				    	}
			   }
		}
	}
	//treba promeniti ocenu u hotelu

	if(hotel!=null) {
				
				//potrebno je podesiti u rezervaciji da je hotel vec ocenjen
				ArrayList<Room> sveSobe = new ArrayList<Room>();
				for(Room rr : hotel.getSobe()) {
					sveSobe.add(rr);
				}
				
				for(Room room : sveSobe) {
						Set<RezervacijaHotel> rezSobe = room.getRezervacije();
						RezervacijaHotel rezIzmena = null;
						//prolazimo kroz rezervacije sobe da vidimo da li je
						//ta rezervacija koja treba da se izmeni vezana za sobu
							for(RezervacijaHotel rH : rezSobe) {
									String idrH = rH.getId().toString();
									if(idrH.equals(idRez)) {
										rezIzmena=rH;
										System.out.println("Pronadjena rez u sobi "+room.getId()+" a hotel je "+hotel.getNaziv());
										break;
									}
							}
							if(rezIzmena!=null) {
								hotel.getSobe().remove(room);
								room.getRezervacije().remove(rezIzmena);
								rezIzmena.setStatus(StatusRezervacije.OTKAZANA);
								room.getRezervacije().add(rezIzmena);
								hotel.getSobe().add(room);
							}
				}
				servis.saveHotel(hotel);
				
				return hotel;

	}else {
		return null;
	}
	 
}
			
}
