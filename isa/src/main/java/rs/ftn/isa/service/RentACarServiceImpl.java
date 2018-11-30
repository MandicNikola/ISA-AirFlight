package rs.ftn.isa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ftn.isa.model.RentACar;
import rs.ftn.isa.repository.RentACarRepository;
@Service
public class RentACarServiceImpl implements RentACarService {
	@Autowired
	RentACarRepository repozitorijum;

	@Override
	public RentACar findOneByNaziv(String name) {
		// TODO Auto-generated method stub
		return repozitorijum.findOneByNaziv(name);
	}

	@Override
	public RentACar saveRentACar(RentACar rentACar) {
		
		return repozitorijum.save(rentACar);
	
	}
}