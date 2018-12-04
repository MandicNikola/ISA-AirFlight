package rs.ftn.isa.service;

import java.util.List;

import rs.ftn.isa.model.Hotel;
import rs.ftn.isa.model.User;


public interface HotelService {
	List<Hotel> findAll();

	Hotel findHotelByNaziv(String naziv);
	Hotel saveHotel(Hotel hotel);
	 
}
