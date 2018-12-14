package rs.ftn.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ftn.isa.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long >{
		Room findOneById(Long id);
}
