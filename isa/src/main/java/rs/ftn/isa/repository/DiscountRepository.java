package rs.ftn.isa.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ftn.isa.model.Discount;


@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long >{
	Discount findOneById(Long id);
}
