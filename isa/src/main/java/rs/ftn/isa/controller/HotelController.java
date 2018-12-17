package rs.ftn.isa.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
			// Data datum = new Data();
			 //CijenovnikSoba cijenovik = new CijenovnikSoba();
			 	//postavi sobi hotel koji joj odg
			 	room.setHotel(pom);
			 	//dodaj hotelu sobu
			 	pom.getSobe().add(room);
			 	//update hotela
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
		
}
