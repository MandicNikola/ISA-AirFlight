package rs.ftn.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ftn.isa.model.PricelistHotel;
@Repository
public interface CijenovnikHotelRepository extends JpaRepository<PricelistHotel,Long> {
	PricelistHotel findOneById(Long id);

}
