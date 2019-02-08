package rs.ftn.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.ftn.isa.model.RentACar;
import rs.ftn.isa.model.RezervacijaRentCar;
import rs.ftn.isa.model.User;

@Repository
public interface RezervacijaRentRepository extends JpaRepository<RezervacijaRentCar,Long> {

	 RezervacijaRentCar findOneById(Long id);
	 RezervacijaRentCar findOneByKorisnik(User korisnik);
	 
	 @Query("select u " + 
				"from RezervacijaRentCar u  " + 
				"where u.glavnRez = :id")
	 RezervacijaRentCar findByAvion(@Param("id")Long id);
}
