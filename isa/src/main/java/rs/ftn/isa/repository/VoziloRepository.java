package rs.ftn.isa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rs.ftn.isa.model.Vehicle;

@Repository
public interface VoziloRepository extends JpaRepository<Vehicle,Long> {

	Vehicle findOneById(Long id);
	
<<<<<<< HEAD
=======
	
>>>>>>> 93bbe449cd2c85345e984ec259b842f19445c05f
}
