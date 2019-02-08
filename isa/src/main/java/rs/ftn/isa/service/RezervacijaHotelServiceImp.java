package rs.ftn.isa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ftn.isa.model.RezervacijaHotel;
import rs.ftn.isa.repository.RezervacijaHotelRepository;
@Service
public class RezervacijaHotelServiceImp implements RezervacijaHotelService {
    @Autowired
    RezervacijaHotelRepository repozitorijum;
	@Override
	public RezervacijaHotel findReservationById(Long id) {
		// TODO Auto-generated method stub
		return repozitorijum.findOneById(id);
	}

	@Override
	public List<RezervacijaHotel> findAll() {
		// TODO Auto-generated method stub
		return repozitorijum.findAll();
	}

	@Override
	public RezervacijaHotel saveRezervacijaHotel(RezervacijaHotel rezervacija) {
		// TODO Auto-generated method stub
		return repozitorijum.save(rezervacija);
	}

	@Override
	public void removeRezervacijaHotel(Long id) {
		// TODO Auto-generated method stub
		repozitorijum.deleteById(id);
	}

	@Override
	public RezervacijaHotel findHotelByRezAvion(Long id) {
		// TODO Auto-generated method stub
		return repozitorijum.findPoAvionu(id);
	}

}
