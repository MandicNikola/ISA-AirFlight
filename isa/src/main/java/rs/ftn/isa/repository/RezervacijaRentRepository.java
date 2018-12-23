package rs.ftn.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ftn.isa.model.RezervacijaRentCar;
import rs.ftn.isa.model.User;

@Repository
public interface RezervacijaRentRepository extends JpaRepository<RezervacijaRentCar,Long> {

	 RezervacijaRentCar findOneById(Long id);
	 RezervacijaRentCar findOneByKorisnik(User korisnik);

}
