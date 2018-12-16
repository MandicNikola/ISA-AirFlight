package rs.ftn.isa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ftn.isa.model.AirplaneCompany;
import rs.ftn.isa.repository.AirplaneRepository;
@Service
public class AirplaneServiceImpl implements AirplaneService{
	@Autowired
	private AirplaneRepository repository;

	
	@Override
	public AirplaneCompany findAirplaneCompanyByNaziv(String naziv) {
		// TODO Auto-generated method stub
		 
		return repository.findOneByNaziv(naziv);
	}


	@Override
	public AirplaneCompany saveAirplaneCompany(AirplaneCompany company) {
		// TODO Auto-generated method stub
		return repository.save(company);
	}

}
