package rs.ftn.isa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ftn.isa.model.AirplaneCompany;
import rs.ftn.isa.repository.AirCompRepository;
import rs.ftn.isa.repository.AirplaneCompanyRepository;

@Service
public class AirplaneServiceCompanyImpl implements AirplaneServiceCompany{
	@Autowired
	private AirCompRepository repository;

	
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


	@Override
	public List<AirplaneCompany> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}


	@Override
	public void removeAirPlaneCompany(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
		
	}


	@Override
	public AirplaneCompany findAirplaneCompanyById(Long id) {
		// TODO Auto-generated method stub
		return repository.findOneById(id);
	}
	
	
	

}
