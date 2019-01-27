package rs.ftn.isa.service;

import java.util.List;

import rs.ftn.isa.model.RezervacijaHotel;

public interface RezervacijaHotelService {

	RezervacijaHotel findReservationById(Long id);
	
	List<RezervacijaHotel> findAll();
	
	RezervacijaHotel saveRezervacijaHotel(RezervacijaHotel rezervacija);
	void removeRezervacijaHotel(Long id);
}
