package rs.ftn.isa.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.model.Filijala;
import rs.ftn.isa.model.RezervacijaHotel;
import rs.ftn.isa.model.Room;
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
	public  String obrisiSobu(@PathVariable String soba){
		System.out.println("brisanje sobe "+soba);
		Long idSoba = Long.parseLong(soba);
		Room room = servis.findRoomById(idSoba);
		if(room.getBrojRezervacija() == 0) {
			servis.removeRoom(idSoba);
			return "uspjesno";
		}
		return "neuspjesno";
		
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

	

}
