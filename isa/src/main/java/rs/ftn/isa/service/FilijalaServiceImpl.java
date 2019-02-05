package rs.ftn.isa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ftn.isa.model.Filijala;
import rs.ftn.isa.repository.FilijalaRepository;

@Service
public class FilijalaServiceImpl implements FilijalaService{

	@Autowired
	FilijalaRepository repozitorijum;
	
	@Override
	public Filijala findFilijalaById(Long id) {
		// TODO Auto-generated method stub
		System.out.println("Usao u findFilijalu");
		return repozitorijum.findOneById(id);
	}

	@Override
	public List<Filijala> findAll() {
		// TODO Auto-generated method stub
		return repozitorijum.findAll();
	}

	@Override
	public Filijala saveFilijala(Filijala filijala) {
		// TODO Auto-generated method stub
		System.out.println("Usao u sacuvaj filijale");
		return repozitorijum.save(filijala);
	}

	@Override
	public void removeFilijala(Long id) {
		// TODO Auto-generated method stub
		System.out.println("Usao u remove filiijalu");
		repozitorijum.deleteById(id);
	}

	@Override
	public Filijala findFilijalaByGrad(String grad) {
		// TODO Auto-generated method stub
		return repozitorijum.findOneByGrad(grad);
	}

	@Override
	public Filijala findFilijalaByUlica(String ulica) {
		// TODO Auto-generated method stub
		return repozitorijum.findOneByUlica(ulica);
	}

	@Override
	public List<Filijala> findAllByUlica(String ulica) {
		// TODO Auto-generated method stub
		return repozitorijum.findAllByUlica(ulica);
	}

	@Override
	public List<Filijala> findAllByGrad(String grad) {
		// TODO Auto-generated method stub
		return repozitorijum.findAllByGrad(grad);
	}

}
