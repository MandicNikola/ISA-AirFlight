package rs.ftn.isa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ftn.isa.model.SpecialDiscount;
@Repository
public interface SpecialDiscountRepository extends JpaRepository<SpecialDiscount,Long>{
	 SpecialDiscount findOneById(Long id);
	 SpecialDiscount findOneByBodovi(int bodovi);
	 
}
