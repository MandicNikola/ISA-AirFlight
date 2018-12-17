package rs.ftn.isa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.model.PricelistHotel;
import rs.ftn.isa.service.CijenovnikHotelServiceImpl;

@RestController
@RequestMapping(value="api/cijenovnici")
public class CijenovnikHotelController {
	@Autowired
	private CijenovnikHotelServiceImpl servis;
	
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public List<PricelistHotel> getAllRooms(){		
		return  servis.findAll();
	}
}
