package rs.ftn.isa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ftn.isa.model.UslugaAvion;
import rs.ftn.isa.repository.UslugaAvionRepository;

@Service
public class UslugaAvionServiceImp implements UslugaAvionService{

	@Autowired
	UslugaAvionRepository repozitorijum;
	
	@Override
	public UslugaAvion findOneById(Long id) {
		// TODO Auto-generated method stub
		return repozitorijum.findOneById(id);
	}

	@Override
	public void removeUslugaAvion(UslugaAvion usluga) {
		// TODO Auto-generated method stub
		repozitorijum.delete(usluga);
	}

}
