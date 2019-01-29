package rs.ftn.isa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ftn.isa.model.Destination;
import rs.ftn.isa.repository.DestinationRepository;
@Service
public class DestinationServiceImp implements DestinationService{

	@Autowired
	DestinationRepository repozitorijum;
	
	
	@Override
	public Destination findDestinationById(Long id) {
		// TODO Auto-generated method stub
		return repozitorijum.findOneById(id);
	}

	@Override
	public Destination findDestinationByName(String naziv) {
		// TODO Auto-generated method stub
		return repozitorijum.findOneByNaziv(naziv);
	}

	@Override
	public void removeDestination(Long id) {
		// TODO Auto-generated method stub
		repozitorijum.deleteById(id);
	}

	@Override
	public void saveDestination(Destination destination) {
		// TODO Auto-generated method stub
		repozitorijum.save(destination);
	}

	
}
