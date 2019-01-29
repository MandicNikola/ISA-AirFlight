package rs.ftn.isa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ftn.isa.model.AirPlane;
import rs.ftn.isa.repository.AirPlaneRepository;

@Service
public class AirPlaneServiceImpl implements AirPlaneService{

	@Autowired
	AirPlaneRepository repozitorijum;
	
	
	@Override
	public void removeAirPlane(Long id) {
		// TODO Auto-generated method stub
		repozitorijum.deleteById(id);
		
	}

	@Override
	public AirPlane findAirPlaneById(Long id) {
		// TODO Auto-generated method stub
		return repozitorijum.findOneById(id);
	}

	@Override
	public void saveAirPlane(AirPlane plane) {
		// TODO Auto-generated method stub
		repozitorijum.save(plane);
		
	}

}
