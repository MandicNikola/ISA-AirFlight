package rs.ftn.isa.service;

import org.springframework.stereotype.Service;

import rs.ftn.isa.model.Destination;

@Service
public interface DestinationService {

	Destination findDestinationById(Long id);
	Destination findDestinationByName(String naziv);
	void removeDestination(Long id);
	void saveDestination(Destination destination);
	
}
