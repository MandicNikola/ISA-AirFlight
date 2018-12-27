package rs.ftn.isa.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.dto.AirplaneDTO;
import rs.ftn.isa.model.AirplaneCompany;
import rs.ftn.isa.model.RentACar;
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
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public List<AirplaneCompany> getAllRents(){		
		return  service.findAll();
	}
	//vraca avio kompanije sortirane po nekom kriterijumu
	@RequestMapping(value="/sort/{uslov}", method = RequestMethod.GET)
	public List<AirplaneCompany> getSortedAirCompanies(@PathVariable String uslov){		
		System.out.println("Uslov je "+uslov);
		
		List<AirplaneCompany> svi = service.findAll();
		List<AirplaneCompany> sortiranaLista=new ArrayList<AirplaneCompany>();
		
		if(uslov.equals("NameA")) {
			//sortiraj po nazivu od A-Z
			System.out.println("Sortiraj po imenu rastuce");
			Collections.sort(svi, AirplaneCompany.AirplaneNameComparator);
			for(AirplaneCompany A : svi) {
				System.out.println(A.getNaziv());
				sortiranaLista.add(A);
			}
			
		}else if(uslov.equals("NameD")) {
			System.out.println("Sortiraj po imenu opadajuce");
			//sortiraj po nazivu od Z-A
			Collections.sort(svi, AirplaneCompany.AirplaneNameComparator);
			for(int i=svi.size()-1; i>=0; i--) {
				System.out.println(svi.get(i).getNaziv());
				sortiranaLista.add(svi.get(i));
			}
			
		}else if(uslov.equals("CityA")) {
			//sortiraj po gradu od A-Z
			System.out.println("Sortiraj po gradu rastuce");

			Collections.sort(svi, AirplaneCompany.AirplaneCityComparator);
			for(AirplaneCompany A : svi) {
				System.out.println(A.getAdresa());
				sortiranaLista.add(A);
			}
		}else {
			//sortiraj po gradu od Z-A
			System.out.println("Sortiraj po gradu rastuce");

			Collections.sort(svi, AirplaneCompany.AirplaneCityComparator);
			for(int i=svi.size()-1; i>=0; i--) {
				System.out.println(svi.get(i).getAdresa());
				sortiranaLista.add(svi.get(i));
			}
		}
		
		
		return sortiranaLista;
	}
	

}
