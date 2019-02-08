package rs.ftn.isa.service;

import java.util.List;

import rs.ftn.isa.model.RezervacijaHotel;

public interface RezervacijaHotelService {

	RezervacijaHotel findReservationById(Long id);
	
	List<RezervacijaHotel> findAll();
	
	RezervacijaHotel saveRezervacijaHotel(RezervacijaHotel rezervacija);
	
	RezervacijaHotel findHotelByRezAvion(Long id);
	
	
	void removeRezervacijaHotel(Long id);
}
