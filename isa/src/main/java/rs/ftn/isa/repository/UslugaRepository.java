package rs.ftn.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.ftn.isa.model.Usluga;

@Repository
public interface UslugaRepository  extends JpaRepository<Usluga,Long>{

	Usluga findOneById(Long id);
	Usluga findOneByNaziv(String naziv);
	Usluga findOneByKategorija(String kategorija);

}
