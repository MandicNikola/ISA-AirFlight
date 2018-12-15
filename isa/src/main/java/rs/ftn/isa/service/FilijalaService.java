package rs.ftn.isa.service;

import java.util.List;

import rs.ftn.isa.model.Filijala;

public interface FilijalaService {

	Filijala findFilijalaById(Long id);
	Filijala findFilijalaByGrad(String grad);
	Filijala findFilijalaByUlica(String ulica);

	List<Filijala> findAll();
	List<Filijala> findAllByUlica(String ulica);
	
	Filijala saveFilijala(Filijala filijala);
	void removeFilijala(Long id);

}
