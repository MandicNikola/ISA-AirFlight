package rs.ftn.isa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.service.CijenovnikHotelServiceImpl;
import rs.ftn.isa.service.HotelService;

@RestController
@RequestMapping(value="api/cijenovnici")
public class CijenovnikHotelController {
	@Autowired
	private CijenovnikHotelServiceImpl servis;

}
