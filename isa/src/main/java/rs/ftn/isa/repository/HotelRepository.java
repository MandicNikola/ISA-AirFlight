package rs.ftn.isa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ftn.isa.model.Hotel;
import rs.ftn.isa.model.Room;


@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long>{
		 Hotel findOneByNaziv(String name);
		 Hotel findOneById(Long id);
		 List<Hotel> findAllByGrad(String grad);
		
}
