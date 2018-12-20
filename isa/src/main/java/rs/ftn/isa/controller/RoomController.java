package rs.ftn.isa.controller;

import java.util.ArrayList;
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
	
	@RequestMapping(value="/obrisiSobu/{soba}", method = RequestMethod.POST)
	public  void obrisiSobu(@PathVariable String soba){
		System.out.println("brisanje sobe "+soba);
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
		System.out.println("dosao da izmjeni sobu " + soba.getId());
		stara.setBalkon(soba.getBalkon());
		stara.setCijena(soba.getCijena());
		stara.setKapacitet(soba.getKapacitet());
		stara.setKreveti(soba.getKreveti());
		stara.setSprat(soba.getSprat());
		stara.setTip(soba.getTip());
		//automatski radi update po id sobe
		servis.saveRoom(stara);
		System.out.println("izmjenio sobu");
		return stara;
	}	
}
