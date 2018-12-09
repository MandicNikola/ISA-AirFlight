package rs.ftn.isa.service;

import java.util.List;

import rs.ftn.isa.model.Usluga;

public interface UslugaService {


	Usluga findUslugaById(Long id);
	
	List<Usluga> findAll();
	
	Usluga saveUsluga(Usluga usluga);
	void removeUsluga(Long id);

}
