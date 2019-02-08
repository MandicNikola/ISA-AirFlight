package rs.ftn.isa.service;

import java.util.List;

import rs.ftn.isa.model.ReservationTicket;
import rs.ftn.isa.model.RezervacijaRentCar;
import rs.ftn.isa.model.User;

public interface RezervacijaRentService {

	List<RezervacijaRentCar> findAll();
	RezervacijaRentCar findOneByKorisnik(User korisnik);
	RezervacijaRentCar findOneById(Long id);	
	RezervacijaRentCar saveRezervacijaRentCar(RezervacijaRentCar rezervacija);
	void removeRezervacijaRentCar(Long id);
	
	RezervacijaRentCar findByAvion(Long id);

}
