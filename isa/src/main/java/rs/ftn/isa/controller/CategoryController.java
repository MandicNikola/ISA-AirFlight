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

import rs.ftn.isa.model.Category;
import rs.ftn.isa.service.CategoryServiceImpl;
@RestController
@RequestMapping(value="api/kategorije")
public class CategoryController {
	@Autowired
	private CategoryServiceImpl servis;
	
	@RequestMapping(value="/kat/{id}", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Category newCat(@RequestBody Category kat,@PathVariable String id){		
	//jedinstven po nazivu
		 List<Category> pom = servis.findAll();
		 //provjera da li postoji vec takva kategorija
		 boolean flag = true;
		 for(Category cat:pom) {
			 if(cat.getNaziv().equals(kat.getNaziv())) {
				 if(cat.getHotelKat().getId().toString().equals(id)) {
					 flag = false;
					 break;
				 }
			 }
			 
		 }
		 Category povratna = new Category();
		 if(flag) {
			 povratna.setNaziv("null");
		
		 }
		 System.out.println("dosao da provjeri kategoriju");
		  return povratna;
		
	}
	
	@RequestMapping(value="/obrisiKat/{id}", method = RequestMethod.POST)
	public  void obrisiKonf(@PathVariable Long id){
		System.out.println("dobio sam id " + id);
		servis.removeById(id);
	
	}

}
