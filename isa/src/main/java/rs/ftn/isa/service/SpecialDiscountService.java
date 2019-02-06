package rs.ftn.isa.service;

import java.util.List;
import rs.ftn.isa.model.SpecialDiscount;

public interface SpecialDiscountService {

	SpecialDiscount saveSpecialDiscount(SpecialDiscount SpecialDiscount);
	void deleteSpecialDiscount(Long id);
	SpecialDiscount findOneById(Long id);
	List<SpecialDiscount> findAll();
	SpecialDiscount findOneByBodovi(int bodovi);
	 
	
}
