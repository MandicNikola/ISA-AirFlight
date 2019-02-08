package rs.ftn.isa.service;

import java.util.List;

import rs.ftn.isa.model.Room;


public interface RoomService {
	Room findRoomById(Long id);
	
	List<Room> findAll();
	
	Room saveRoom(Room soba) throws Exception;
	void removeRoom(Long id);
	List<Room> findRoomsByHotel(Long hotel);
}
