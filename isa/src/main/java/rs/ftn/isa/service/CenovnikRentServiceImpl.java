package rs.ftn.isa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ftn.isa.model.PricelistRentCar;
import rs.ftn.isa.repository.CenovnikRentRepository;

@Service
public class CenovnikRentServiceImpl implements CenovnikRentService {
	
	@Autowired
	private CenovnikRentRepository repozitorijum;
	
	
	@Override
	public PricelistRentCar findPricelistRentCarById(Long id) {
		// TODO Auto-generated method stub
		return repozitorijum.findOneById(id);
	}

	@Override
	public List<PricelistRentCar> findAll() {
		// TODO Auto-generated method stub
		return repozitorijum.findAll();
	}

	@Override
	public PricelistRentCar savePricelistRentCar(PricelistRentCar pricelistrentcar) {
		// TODO Auto-generated method stub
		System.out.println("Usao u save pricelist");
		return repozitorijum.save(pricelistrentcar);
	}

	@Override
	public void removePricelistRentCar(Long id) {
		// TODO Auto-generated method stub
			repozitorijum.deleteById(id);
	}

}
