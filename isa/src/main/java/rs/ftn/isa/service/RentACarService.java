package rs.ftn.isa.service;

import rs.ftn.isa.model.RentACar;

public interface RentACarService{

	RentACar findOneByNaziv(String name);
	RentACar saveRentACar(RentACar rentACar);
}
