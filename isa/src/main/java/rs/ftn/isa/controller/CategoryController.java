package rs.ftn.isa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.service.CategoryServiceImpl;
@RestController
@RequestMapping(value="api/kategorije")
public class CategoryController {
	@Autowired
	private CategoryServiceImpl servis;
}
