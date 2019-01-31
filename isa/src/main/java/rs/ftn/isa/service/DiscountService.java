package rs.ftn.isa.service;

import java.util.List;

import rs.ftn.isa.model.Discount;

public interface DiscountService {
	Discount findDiscountById(Long id);	
	List<Discount> findAll();
	Discount saveDiscount(Discount discount);
	void removeDiscount(Long id);
	
}
