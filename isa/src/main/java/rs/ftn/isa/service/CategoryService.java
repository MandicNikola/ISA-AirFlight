package rs.ftn.isa.service;

import java.util.List;

import rs.ftn.isa.model.Category;


public interface CategoryService {
	List<Category> findAll();
	Category findByNaziv(String name);
	void removeById(Long id);
	
}
