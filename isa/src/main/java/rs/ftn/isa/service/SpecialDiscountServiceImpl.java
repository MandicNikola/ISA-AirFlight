package rs.ftn.isa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ftn.isa.model.SpecialDiscount;
import rs.ftn.isa.repository.SpecialDiscountRepository;

@Service
public class SpecialDiscountServiceImpl implements SpecialDiscountService{
	@Autowired
	SpecialDiscountRepository repozitorijum;
	
	@Override
	public SpecialDiscount saveSpecialDiscount(SpecialDiscount specialdiscount) {
		// TODO Auto-generated method stub
		return repozitorijum.save(specialdiscount);
	}

	@Override
	public void deleteSpecialDiscount(Long id) {
		// TODO Auto-generated method stub
		repozitorijum.deleteById(id);
	}

	@Override
	public SpecialDiscount findOneById(Long id) {
		// TODO Auto-generated method stub
		return repozitorijum.findOneById(id);
	}

	@Override
	public List<SpecialDiscount> findAll() {
		// TODO Auto-generated method stub
		return repozitorijum.findAll();
	}

	@Override
	public SpecialDiscount findOneByBodovi(int bodovi) {
		// TODO Auto-generated method stub
		return repozitorijum.findOneByBodovi(bodovi);
	}

}
