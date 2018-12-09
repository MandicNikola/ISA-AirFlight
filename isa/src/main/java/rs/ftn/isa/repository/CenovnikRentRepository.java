package rs.ftn.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ftn.isa.model.PricelistRentCar;

@Repository
public interface CenovnikRentRepository extends JpaRepository<PricelistRentCar,Long>{
	PricelistRentCar findOneById(Long id);

}
