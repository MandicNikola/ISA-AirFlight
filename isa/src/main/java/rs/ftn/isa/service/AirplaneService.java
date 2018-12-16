package rs.ftn.isa.service;

import rs.ftn.isa.model.AirplaneCompany;

public interface AirplaneService {
	AirplaneCompany findAirplaneCompanyByNaziv(String naziv);
	AirplaneCompany saveAirplaneCompany(AirplaneCompany company);
	
}
