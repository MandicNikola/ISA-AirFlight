package rs.ftn.isa.controller;

import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.websocket.server.PathParam;
import javax.xml.crypto.Data;

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
			for(CijenovnikSoba cs :cijenovnici){
				if(cs.isAktivan()) {
					cs.setAktivan(false);
				}
			}
			
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
		
		
		@RequestMapping(value="/dodajUs/{id}", 
				method = RequestMethod.POST,
				consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody Hotel dodajDodatnuUslugu(@RequestBody Usluga u,@PathVariable Long id){		
			 	Hotel pom = servis.findHotelById(id);	 
			 	if(pom.getCijenovnici()!= null) {
			 		PricelistHotel aktivni = new PricelistHotel();
			 		
			 		for(PricelistHotel cc:pom.getCijenovnici()) {
			 			if(cc.isAktivan()) {
			 				aktivni = cc;
			 				break;
			 			}
			 		}
			 		for(Usluga uu :aktivni.getUsluge()) {
			 			if(uu.getNaziv().equals(u.getNaziv())) {
			 				//postoji vec usluga sa datim nazivom
			 				pom.setOpis("Usluga");
			 				return pom;
			 			}
			 		}
			 		u.setCijene(aktivni);
			 		aktivni.getUsluge().add(u);
			 		
			 		//izmjeni cijenovnik kod hotela
			 		pom.getCijenovnici().remove(aktivni);
			 		pom.getCijenovnici().add(aktivni);
			 		servis.saveHotel(pom);
			 		return pom;
			 	}else {
			 		//ne postoje cijenovnici
			 		PricelistHotel aktivni = new PricelistHotel();
			 		Date datum = new Date();
			 		aktivni.setDatum_primene(datum);
			 		aktivni.setAktivan(true);
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
}
