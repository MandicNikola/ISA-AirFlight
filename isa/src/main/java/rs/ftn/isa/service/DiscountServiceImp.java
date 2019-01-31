package rs.ftn.isa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ftn.isa.model.Discount;
import rs.ftn.isa.repository.DiscountRepository;

@Service
public class DiscountServiceImp implements DiscountService{
	@Autowired
	DiscountRepository repozitorijum;

	@Override
	public Discount findDiscountById(Long id) {
		// TODO Auto-generated method stub
		return repozitorijum.findOneById(id);
	}

	@Override
	public List<Discount> findAll() {
		// TODO Auto-generated method stub
		return repozitorijum.findAll();
	}

	@Override
	public Discount saveDiscount(Discount discount) {
		// TODO Auto-generated method stub
		return repozitorijum.save(discount);
	}

	@Override
	public void removeDiscount(Long id) {
		// TODO Auto-generated method stub
		repozitorijum.deleteById(id);	}
}
