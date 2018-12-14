package rs.ftn.isa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.service.FilijalaServiceImpl;

@RestController
@RequestMapping(value="api/filijale")
public class FilijalaController {

	@Autowired
	private FilijalaServiceImpl servis;
}
