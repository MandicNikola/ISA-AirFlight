package rs.ftn.isa.service;

import org.springframework.stereotype.Service;

import rs.ftn.isa.model.UslugaAvion;

@Service
public interface UslugaAvionService {

	UslugaAvion findOneById(Long id);
	void removeUslugaAvion(UslugaAvion usluga);
	
}
