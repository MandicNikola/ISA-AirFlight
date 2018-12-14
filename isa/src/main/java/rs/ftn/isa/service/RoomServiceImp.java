package rs.ftn.isa.service;
import java.util.List;

import org.springframework.stereotype.Service;

import rs.ftn.isa.model.Room;
import rs.ftn.isa.repository.RoomRepository;

@Service
public class RoomServiceImp implements RoomService{
	RoomRepository repozitorijum;
	@Override
	public Room findRoomById(Long id) {
		// TODO Auto-generated method stub
		return repozitorijum.findOneById(id);
	}

	@Override
	public List<Room> findAll() {
		// TODO Auto-generated method stub
		return repozitorijum.findAll();
	}

	@Override
	public Room saveRoom(Room soba) {
		// TODO Auto-generated method stub
		return repozitorijum.save(soba);
	}

	@Override
	public void removeRoom(Long id) {
		// TODO Auto-generated method stub
		repozitorijum.deleteById(id);
	}

}
