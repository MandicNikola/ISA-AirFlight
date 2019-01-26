package rs.ftn.isa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import rs.ftn.isa.model.AirplaneCompany;

@Service
public interface AirplaneServiceCompany {
	AirplaneCompany findAirplaneCompanyByNaziv(String naziv);
	AirplaneCompany saveAirplaneCompany(AirplaneCompany company);
	List<AirplaneCompany> findAll();
	void removeAirPlaneCompany(Long id);
	AirplaneCompany findAirplaneCompanyById(Long id);
	
}
