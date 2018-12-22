package rs.ftn.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ftn.isa.model.RezervacijaHotel;
import rs.ftn.isa.model.Room;

@Repository
public interface RezervacijaHotelRepository extends JpaRepository<RezervacijaHotel, Long >{
	RezervacijaHotel findOneById(Long id);
}
