package rs.ftn.isa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ftn.isa.model.Category;
import rs.ftn.isa.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository repozitorijum;

	@Override
	public List<Category> findAll() {
		// TODO Auto-generated method stub
		return repozitorijum.findAll();
	}

	@Override
	public Category findByNaziv(String name) {
		// TODO Auto-generated method stub
		return repozitorijum.findOneByNaziv(name);
	}

	@Override
	public void removeById(Long id) {
		// TODO Auto-generated method stub
		repozitorijum.deleteById(id);
	}
	
}
