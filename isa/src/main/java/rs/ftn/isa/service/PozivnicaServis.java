package rs.ftn.isa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import rs.ftn.isa.model.Pozivnica;

@Service
public interface PozivnicaServis {

	Pozivnica findOneById(Long id);
	List<Pozivnica> findAll();
	
	
}
