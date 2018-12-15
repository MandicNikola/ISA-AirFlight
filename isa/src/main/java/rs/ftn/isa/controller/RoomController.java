package rs.ftn.isa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.dto.HotelDTO;
import rs.ftn.isa.model.Hotel;
import rs.ftn.isa.model.Room;
import rs.ftn.isa.service.RoomServiceImp;

@RestController
@RequestMapping(value="api/rooms")
public class RoomController {
	@Autowired
	private RoomServiceImp servis;

	@RequestMapping(value="/all", method = RequestMethod.GET)
	public List<Room> getAllKorisnici(){		
		return  servis.findAll();
	}
	
	@RequestMapping(value="/newroom",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Room newRoom(@RequestBody Room room) {
			Room pomRoom = new Room();
			System.out.println("tip je " + room.getTip()+" kreveti su " + room.getKreveti()+" ima balkon " + room.getBalkon());
		if(room.getTip().equals("") || room.getTip() == null || room.getTip().equals("undefined")) {
			pomRoom.setTip("Tip");
			return pomRoom;
		}
		if(room.getKreveti()<1) {
			pomRoom.setTip("Kreveti");
			return pomRoom;
		}
		if(room.getSprat()<0) {
			pomRoom.setTip("Sprat");
			return pomRoom;
			
			
		}
		
		return room;
	}
	
		
	
}
