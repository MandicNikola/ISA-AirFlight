package rs.ftn.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ftn.isa.model.Usluga;

public interface UslugaRepository  extends JpaRepository<Usluga,Long>{

	Usluga findOneById(Long id);
}
