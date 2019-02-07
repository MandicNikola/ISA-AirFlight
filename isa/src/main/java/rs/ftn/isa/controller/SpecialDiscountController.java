package rs.ftn.isa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rs.ftn.isa.model.SpecialDiscount;
import rs.ftn.isa.service.SpecialDiscountServiceImpl;

@RestController
@RequestMapping(value="api/special")

public class SpecialDiscountController {
	@Autowired
	private SpecialDiscountServiceImpl servis;

	@RequestMapping(value="/all", method = RequestMethod.GET)
	public List<SpecialDiscount> getAllSpecialDiscount(){
		System.out.println("dosao po specijalne popuste");
		return  servis.findAll();
	}	
	
	
	@RequestMapping(value="/getById/{id}", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody SpecialDiscount getSpecialDiscount(@PathVariable Long id){
		System.out.println("dosao po specijalni popust");
		return  servis.findOneById(id);
	}
	@RequestMapping(value="/obrisiSpecijalni/{id}", method = RequestMethod.POST)
	public  void obrisiSpecijalni(@PathVariable Long id){
		servis.deleteSpecialDiscount(id);		
	}

	@RequestMapping(value="/newspecial",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody SpecialDiscount newRoom(@RequestBody SpecialDiscount special) {
		System.out.println("dosao ovdje"+special.getVrijednost()+"a bod "+special.getBodovi());
		
		SpecialDiscount popust = servis.findOneByBodovi(special.getBodovi());
		if(popust == null) {
			popust = new SpecialDiscount(special.getVrijednost(),special.getBodovi());
			servis.saveSpecialDiscount(popust);
		}else {
			popust.setBodovi(0);
		}
		return popust;
	}
}
