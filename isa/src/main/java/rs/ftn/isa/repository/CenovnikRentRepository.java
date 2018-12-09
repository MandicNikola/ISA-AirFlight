package rs.ftn.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ftn.isa.model.PricelistRentCar;

public interface CenovnikRentRepository extends JpaRepository<PricelistRentCar,Long>{
	PricelistRentCar findOneById(Long id);

}
