package rs.ftn.isa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ftn.isa.model.Hotel;
import rs.ftn.isa.model.Room;
import rs.ftn.isa.repository.HotelRepository;
@Service
public class HotelServiceImpl implements HotelService {
	
	@Autowired
	private HotelRepository hotelRepozitorijum;
	
	
	@Override
	public List<Hotel> findAll() {
		// TODO Auto-generated method stub
		return hotelRepozitorijum.findAll();
	}


	@Override
	public Hotel findHotelByNaziv(String naziv) {
		// TODO Auto-generated method stub
		return 	hotelRepozitorijum.findOneByNaziv(naziv);	
	}
	@Override
	public Hotel saveHotel(Hotel hotel) {
		// TODO Auto-generated method stub
		System.out.println(" sacuvaj hotel ");
			return hotelRepozitorijum.save(hotel);
	}


	@Override
	public Hotel findHotelById(Long id) {
		// TODO Auto-generated method stub
		return hotelRepozitorijum.findOneById(id);
	}


	@Override
	public void removeHotel(Long id) {
		// TODO Auto-generated method stub
		hotelRepozitorijum.deleteById(id);
		
	}


	
}
