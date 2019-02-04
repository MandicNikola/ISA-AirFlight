package rs.ftn.isa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rs.ftn.isa.model.Room;
import rs.ftn.isa.model.User;
@Repository
public interface RoomRepository extends JpaRepository<Room, Long >{
		Room findOneById(Long id);
		@Query("select r " + 
				"from Room r  " + 
				"where r.hotel.id= ?1")
		List<Room> findRoomsByHotel(Long hotel);
		
		
}
