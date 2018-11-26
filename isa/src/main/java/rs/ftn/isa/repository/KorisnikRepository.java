package rs.ftn.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ftn.isa.model.Korisnik;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik,Long> {
//bice sve metode koje su napravljene vec za nas
	
}
