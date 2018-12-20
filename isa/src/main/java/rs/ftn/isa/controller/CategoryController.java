package rs.ftn.isa.controller;

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
	
	@RequestMapping(value="/kat", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Category newCat(@RequestBody Category kat){		
	//jedinstven po nazivu
		 Category pom = servis.findByNaziv(kat.getNaziv());
		 //provjera da li postoji vec takva kategorija
		 if(pom == null) {
			 pom = new Category("null");
		
		 }
		 System.out.println("dosao da provjeri kategoriju");
		  return pom;
		
	}
	
	@RequestMapping(value="/obrisiKat/{id}", method = RequestMethod.POST)
	public  void obrisiKonf(@PathVariable Long id){
		System.out.println("dobio sam id " + id);
		servis.removeById(id);
	
	}

}
