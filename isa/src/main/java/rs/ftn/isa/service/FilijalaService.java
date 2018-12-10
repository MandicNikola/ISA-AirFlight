package rs.ftn.isa.service;

import java.util.List;

import rs.ftn.isa.model.Filijala;

public interface FilijalaService {

	Filijala findFilijalaById(Long id);
	
	List<Filijala> findAll();
	
	Filijala saveFilijala(Filijala filijala);
	void removeFilijala(Long id);

}
