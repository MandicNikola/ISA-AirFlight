package rs.ftn.isa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ftn.isa.model.RezervacijaRentCar;
import rs.ftn.isa.model.User;
import rs.ftn.isa.repository.RezervacijaRentRepository;

@Service
public class RezervacijaRentServiceImpl implements RezervacijaRentService {

	@Autowired
	RezervacijaRentRepository repozitorijum;
	
	@Override
	public List<RezervacijaRentCar> findAll() {
		// TODO Auto-generated method stub
		return repozitorijum.findAll();
	}

	@Override
	public RezervacijaRentCar findOneByKorisnik(User korisnik) {
		// TODO Auto-generated method stub
		return repozitorijum.findOneByKorisnik(korisnik);
	}

	@Override
	public RezervacijaRentCar findOneById(Long id) {
		// TODO Auto-generated method stub
		return repozitorijum.findOneById(id);
	}

	@Override
	public RezervacijaRentCar saveRezervacijaRentCar(RezervacijaRentCar rezervacija) {
		// TODO Auto-generated method stub
		return repozitorijum.save(rezervacija);
	}

	@Override
	public void removeRezervacijaRentCar(Long id) {
		// TODO Auto-generated method stub
		repozitorijum.deleteById(id);
	}

	@Override
	public RezervacijaRentCar findByAvion(Long id) {
		return repozitorijum.findByAvion(id);
	}

}
