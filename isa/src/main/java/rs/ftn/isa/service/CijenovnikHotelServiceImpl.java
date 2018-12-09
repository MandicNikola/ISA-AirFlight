package rs.ftn.isa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ftn.isa.model.PricelistHotel;
import rs.ftn.isa.repository.CijenovnikHotelRepository;
@Service
public class CijenovnikHotelServiceImpl implements CijenovnikHotelService {
	
	@Autowired
	private CijenovnikHotelRepository cijenovnikRepozitorijum;

	@Override
	public PricelistHotel findCijenovnikById(Long id) {
		// TODO Auto-generated method stub
		return cijenovnikRepozitorijum.findOneById(id);
	}

	@Override
	public List<PricelistHotel> findAll() {
		// TODO Auto-generated method stub
		return cijenovnikRepozitorijum.findAll();
	}

	@Override
	public PricelistHotel sacuvajCijenovnik(PricelistHotel cijenovnik) {
		// TODO Auto-generated method stub
		return cijenovnikRepozitorijum.save(cijenovnik);
	}

	@Override
	public void removeCijenovnik(Long id) {
		// TODO Auto-generated method stub
		
	}
	
	
}
