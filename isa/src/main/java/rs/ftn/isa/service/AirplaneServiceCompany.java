package rs.ftn.isa.service;

import java.util.List;

import rs.ftn.isa.model.AirplaneCompany;


public interface AirplaneServiceCompany {
	AirplaneCompany findAirplaneCompanyByNaziv(String naziv);
	AirplaneCompany saveAirplaneCompany(AirplaneCompany company);
	List<AirplaneCompany> findAll();
	void removeAirPlaneCompany(Long id);
	AirplaneCompany findAirplaneCompanyById(Long id);
	
}
