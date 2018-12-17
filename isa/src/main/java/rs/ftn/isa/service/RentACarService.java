package rs.ftn.isa.service;

import java.util.List;

import rs.ftn.isa.model.PricelistRentCar;
import rs.ftn.isa.model.RentACar;

public interface RentACarService{

	List<RentACar> findAll();
	RentACar findOneByNaziv(String name);
	RentACar findOneById(Long id);	
	RentACar saveRentACar(RentACar rentACar);
	void removeRentACar(Long id);
	PricelistRentCar findAktivanCenovnik(Long id);

}
