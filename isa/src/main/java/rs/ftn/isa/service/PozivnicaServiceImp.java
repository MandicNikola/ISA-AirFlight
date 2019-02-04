package rs.ftn.isa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ftn.isa.model.Pozivnica;
import rs.ftn.isa.repository.PozivnicaRepository;

@Service
public class PozivnicaServiceImp implements PozivnicaServis{

	@Autowired
	PozivnicaRepository repozitorijum;

	@Override
	public Pozivnica findOneById(Long id) {
		// TODO Auto-generated method stub
		return repozitorijum.findOneById(id);
	}

	@Override
	public List<Pozivnica> findAll() {
		// TODO Auto-generated method stub
		return repozitorijum.findAll();
	}
	
	
	
	
}
