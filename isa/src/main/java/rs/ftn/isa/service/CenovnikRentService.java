package rs.ftn.isa.service;

import java.util.List;

import rs.ftn.isa.model.PricelistRentCar;


public interface CenovnikRentService {
	PricelistRentCar findPricelistRentCarById(Long id);
	
	List<PricelistRentCar> findAll();
	
	PricelistRentCar savePricelistRentCar(PricelistRentCar pricelistrentcar);
	void removePricelistRentCar(Long id);

}
