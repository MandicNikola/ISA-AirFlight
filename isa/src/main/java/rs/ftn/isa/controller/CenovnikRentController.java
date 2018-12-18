package rs.ftn.isa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.model.PricelistRentCar;
import rs.ftn.isa.service.CenovnikRentServiceImpl;

@RestController
@RequestMapping(value="api/cenovnici")
public class CenovnikRentController {

	@Autowired 
	private  CenovnikRentServiceImpl servis;
	
	
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public List<PricelistRentCar> getAllPricelists(){		
		return  servis.findAll();
	}
	
}
