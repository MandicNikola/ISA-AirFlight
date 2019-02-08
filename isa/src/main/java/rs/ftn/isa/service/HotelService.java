package rs.ftn.isa.service;

import java.util.List;

import rs.ftn.isa.model.Hotel;
import rs.ftn.isa.model.Room;
import rs.ftn.isa.model.User;


public interface HotelService {
	List<Hotel> findAll();

	Hotel findHotelByNaziv(String naziv);
	Hotel findHotelById(Long id);
	Hotel saveHotel(Hotel hotel) throws Exception;
	void removeHotel(Long id);
	List<Hotel> findAllByGrad(String grad);
	
	 
}
