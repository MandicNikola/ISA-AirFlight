package rs.ftn.isa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import rs.ftn.isa.model.Usluga;
import rs.ftn.isa.repository.UslugaRepository;

public class UslugaServiceImpl implements UslugaService {
	
	@Autowired
	private UslugaRepository repozitorijum;
	
	
	@Override
	public Usluga findUslugaById(Long id) {
		// TODO Auto-generated method stub
		return repozitorijum.findOneById(id);
	}

	@Override
	public List<Usluga> findAll() {
		// TODO Auto-generated method stub
		return repozitorijum.findAll();
	}

	@Override
	public Usluga saveUsluga(Usluga usluga) {
		// TODO Auto-generated method stub
		return repozitorijum.save(usluga);
	}

	@Override
	public void removeUsluga(Long id) {
		// TODO Auto-generated method stub
		repozitorijum.deleteById(id);
	}

}
