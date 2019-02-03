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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.dto.ReservationHotelDTO;
import rs.ftn.isa.dto.RoomDTO;
import rs.ftn.isa.model.Discount;
import rs.ftn.isa.model.Filijala;
import rs.ftn.isa.model.Hotel;
import rs.ftn.isa.model.RezervacijaHotel;
import rs.ftn.isa.model.Room;
import rs.ftn.isa.model.User;
import rs.ftn.isa.service.RoomServiceImp;

@RestController
@RequestMapping(value="api/rooms")
public class RoomController {
	@Autowired
	private RoomServiceImp servis;

	@RequestMapping(value="/all", method = RequestMethod.GET)
	public List<Room> getAllRooms(){
		System.out.println("dosao po sobe");
		return  servis.findAll();
	}
	//validacija dodavanja nove sobe
	@RequestMapping(value="/newroom",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Room newRoom(@RequestBody Room room) {
			Room pomRoom = new Room();

			
		if(room.getTip().equals("") || room.getTip() == null || room.getTip().equals("undefined")) {
			pomRoom.setTip("Tip");
			return pomRoom;
		}
		if(room.getKapacitet()<1) {
			pomRoom.setTip("Kreveti");
			return pomRoom;
		}
		if(room.getSprat()<0) {
			pomRoom.setTip("Sprat");
			return pomRoom;
			
			
		}
		
		return room;
	}
	
	@RequestMapping(value="/obrisiSobu/{soba}", method = RequestMethod.POST)
	public  void obrisiSobu(@PathVariable String soba){
		Long idSoba = Long.parseLong(soba);
		servis.removeRoom(idSoba);
		
		
	}
	
	@RequestMapping(value="/vratiSobu/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody Room getRoomById(@PathVariable Long id){	
				Room soba = servis.findRoomById(id);
				return soba;

	}
	
	
	@RequestMapping(value="/izmjeniSobu/{id}", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Room izmjeniSobu(@RequestBody Room soba,@PathVariable Long id){		
		Room stara = servis.findRoomById(id);
		stara.setBalkon(soba.getBalkon());
		stara.setKapacitet(soba.getKapacitet());
		stara.setSprat(soba.getSprat());
		stara.setTip(soba.getTip());
		//automatski radi update po id sobe
		servis.saveRoom(stara);
		return stara;
	}


	@RequestMapping(value="/ukloniPopust/{slanje}", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public @ResponseBody Room ukloniPopust(@PathVariable("slanje") String slanje){
		System.out.println("Dosao da ukloni popust");
		String[] pom = slanje.split("\\.");
		String sobaId = pom[1];
		Long id =Long.parseLong(sobaId);
		Room room  = servis.findRoomById(id);
		System.out.println("Soba je "+room.getId());
		if(room.getPopusti() == null) {
			room.setImapopusta(false);
			
		}else {

			if(room.getPopusti().size()== 0) {
				room.setImapopusta(false);
			}
		}
		
		servis.saveRoom(room);
		return room;
	}

	
	@RequestMapping(value="/getFast/{id}/checkout/{checkout}/checkin/{checkin}", 
			method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE
			)
	public @ResponseBody ArrayList<RoomDTO> getFast(@PathVariable Long id,@PathVariable String checkout,@PathVariable String checkin,@Context HttpServletRequest request) throws ParseException{		
		User korisnik = (User)request.getSession().getAttribute("ulogovan");		
		int brojBodova = 0;
		if(korisnik != null) {
			 brojBodova  = korisnik.getBodovi();
		}else {
			System.out.println("korisnik null");
			return new ArrayList<RoomDTO>();
			
		}
		if(brojBodova ==0) {
			return new ArrayList<RoomDTO>();
		}
		System.out.println("dobio je hotel "+id+"a datum odlaska mu je "+checkout);
		ArrayList<RoomDTO> pronadjeneSobe = new ArrayList<RoomDTO>();
		System.out.println("USAO JE  U FUNKCIJU ****************************");
		List<Room> sobe = servis.findRoomsByHotel(id);
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		Date datumCheckIn = formater.parse(checkin);
		System.out.println("Daatum dolaska korisnika je "+datumCheckIn);
		Date datumCheckOut = formater.parse(checkout);
		System.out.println("Daatum odlaska korisnika je "+datumCheckOut);
		
		for(Room r:sobe) {
			//koji popust je definisan za njega
			int izabraniPopust = -1;
			int index = 0;
			ArrayList<Discount> popusti = new ArrayList<Discount>() ;
			for(Discount p:r.getPopusti()) {
				System.out.println("postoji popust");
				popusti.add(p);
			}
			if(popusti.isEmpty()) {
				return new ArrayList<RoomDTO>();
			}
			popusti.sort(Comparator.comparingInt(Discount :: getBodovi));
			for(Discount popust:popusti) {
				if(brojBodova >= popust.getBodovi()) {
					Date pocetakPopusta = popust.getDatumod();
					System.out.println(" datum pocetka popusta "+pocetakPopusta);
					Date krajPopusta = popust.getDatumdo();
					
					System.out.println(" datum kraja popusta "+krajPopusta);
					boolean moze = false;
					pocetakPopusta.setHours(0);
					pocetakPopusta.setMinutes(0);
					pocetakPopusta.setSeconds(0);
					
					krajPopusta.setHours(0);
					pocetakPopusta.setMinutes(0);
					pocetakPopusta.setSeconds(0);
					
					if(datumCheckIn.compareTo(pocetakPopusta)>=0 && datumCheckOut.compareTo(krajPopusta)<=0) {
						moze = true;
						izabraniPopust =index;

						System.out.println("Odgovara datum u popustu");
						
					}
				
				}
				index++;
			}
			//nema definisan popust za tako mali broj bodova
			if(izabraniPopust == -1) {
				return new ArrayList<RoomDTO>();
			}
			Discount odgovarajuciPopust = popusti.get(izabraniPopust);
			
			
			
			Room room = r;
			Set<RezervacijaHotel> rezervacije = room.getRezervacije(); 
			if(rezervacije.size() == 0) {
				RoomDTO sobaDTO = new RoomDTO(room.getId(),room.getTip(),room.getOcjena(),room.getSprat(),room.getKapacitet(),room.getCijena(),room.getBalkon());
				sobaDTO.setPopust(odgovarajuciPopust.getVrijednost());
				pronadjeneSobe.add(sobaDTO);
				break;
			}
			//moram provjeriti prvi slucaj: da li je check in > od krajeva svih rezervacija koje postoje za tu sobu
			boolean odobrenCheckIN = true;
			for(RezervacijaHotel pom:rezervacije) {	
				
				if(datumCheckIn.compareTo(pom.getDatumOdlaska())<=0) {
					System.out.println("nije odobren check in");
					odobrenCheckIN = false;
					break;
				}
			}
			//odobren check in,provjeravam check out ..da li je check out < od pocetaka svih rezervacija koje postoje za datu sobu
			boolean odobrenCheckOUT= true;
			
				for(RezervacijaHotel pom:rezervacije) {	
					
					if(datumCheckOut.compareTo(pom.getDatumDolaska())>=0) {
						System.out.println("nije odobren check out");
						odobrenCheckOUT = false;
						break;
					}
				}
				
			
			//odobrena je soba
			if(odobrenCheckIN == true || odobrenCheckOUT == true) {
				System.out.println("odobrena soba");
				RoomDTO sobaDTO = new RoomDTO(room.getId(),room.getTip(),room.getOcjena(),room.getSprat(),room.getKapacitet(),room.getCijena(),room.getBalkon());
				sobaDTO.setPopust(odgovarajuciPopust.getVrijednost());
				pronadjeneSobe.add(sobaDTO);
			}
			
		}

		if(pronadjeneSobe.size() == 0) {
			System.out.println("Nije pronasao nijednu sobu");
			return new ArrayList<RoomDTO>();
		}
		for(RoomDTO romdto:pronadjeneSobe) {
			System.out.println(" id "+romdto.getId()+" popust "+romdto.getPopust());
		}
		return pronadjeneSobe;
	}	

	
}
