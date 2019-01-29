package rs.ftn.isa.service;

import org.springframework.stereotype.Service;

import rs.ftn.isa.model.AirPlane;

@Service
public interface AirPlaneService {

	void removeAirPlane(Long id);
	AirPlane findAirPlaneById(Long id);
	void saveAirPlane(AirPlane plane);
	
	
}
