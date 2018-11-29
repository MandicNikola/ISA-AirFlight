package rs.ftn.isa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ftn.isa.model.Hotel;
import rs.ftn.isa.repository.HotelRepository;
@Service
public class HotelServiceImpl implements HotelService {
	
	@Autowired
	private HotelRepository hotelRepozitorijum;
	
	
	@Override
	public List<Hotel> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
