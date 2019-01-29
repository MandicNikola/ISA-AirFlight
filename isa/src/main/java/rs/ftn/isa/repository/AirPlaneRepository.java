package rs.ftn.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ftn.isa.model.AirPlane;

public interface AirPlaneRepository extends JpaRepository<AirPlane, Long> {

	
	AirPlane findOneById(Long id);
	
	
}
