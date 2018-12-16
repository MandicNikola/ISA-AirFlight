package rs.ftn.isa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.dto.AirplaneDTO;
import rs.ftn.isa.model.AirplaneCompany;
import rs.ftn.isa.service.AirplaneServiceImpl;

@RestController
@RequestMapping(value="api/avioni")

public class AirplaneController {
	
	@Autowired
	private AirplaneServiceImpl service ;
	@RequestMapping(value="/novaAvioKompanija", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody AirplaneCompany newHotel(@RequestBody AirplaneDTO kompanija){		
	//jedinstven po nazivu
		 AirplaneCompany pom = service.findAirplaneCompanyByNaziv(kompanija.getNaziv());
		 if(pom == null) {
			AirplaneCompany avio = new AirplaneCompany(kompanija.getNaziv(),kompanija.getAdresa(),kompanija.getOpis());
			service.saveAirplaneCompany(avio);
			return avio; 
		 }else {
			 AirplaneCompany povratna = new AirplaneCompany(); 
			 return povratna;
			 
		 }
	}

}
