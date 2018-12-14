package rs.ftn.isa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.dto.RentACarDTO;
import rs.ftn.isa.model.RentACar;
import rs.ftn.isa.model.User;
import rs.ftn.isa.model.Vehicle;
import rs.ftn.isa.service.RentACarServiceImpl;

@RestController
@RequestMapping(value="api/rents")
public class RentACarController {
	@Autowired 
	private RentACarServiceImpl servis;
	
	
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public List<RentACar> getAllKorisnici(){		
		return  servis.findAll();
	}
	
	@RequestMapping(value="/newrentacar",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody RentACar newRentACar(@RequestBody RentACarDTO newRent) {
		RentACar rent = servis.findOneByNaziv(newRent.getNaziv());
		if(rent == null) {
			RentACar novi = new RentACar(newRent.getNaziv(),newRent.getAdresa(),newRent.getOpis());
			servis.saveRentACar(novi);
			return novi;
		}else {
			RentACar pom = new RentACar();
			return pom;
		}
	}	
		
	@RequestMapping(value="/vratiRentId/{id}",
			method = RequestMethod.GET)
	public @ResponseBody RentACar getRentId(@PathVariable Long id){
	
		RentACar rentServis = servis.findOneById(id);
		System.out.println("Usao je u getRentId "+ rentServis.getNaziv());
		return rentServis;
	}

	@RequestMapping(value="/postavivozilo/{id}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE
			)
	public @ResponseBody RentACar postaviVozilo(@PathVariable Long id, @RequestBody Vehicle vozilo){
	
		RentACar rentServis = servis.findOneById(id);		
		System.out.println("Pronasao rent servis "+ rentServis.getNaziv());
		
		Vehicle novo = vozilo;
		System.out.println(novo.getNaziv() + " " + novo.getCena());
		System.out.println(novo.toString());
		novo.setServisrent(rentServis);
		
		Set<Vehicle> vozilaLista=rentServis.getVozila(); 
		vozilaLista.add(novo);
		rentServis.setVozila(vozilaLista);
		
	    servis.removeRentACar(id);
	    
	    //baza prilikom cuvanja izmeni id
		RentACar povratna = servis.saveRentACar(rentServis);
		
		System.out.println("NOVI id od vozila je "+povratna.getId());
		//treba mi za povratak na profil od ovog rent-a-car-a
		String br=povratna.getId().toString();
		povratna.setAdresa(br);
		return povratna;
	}

	
}
