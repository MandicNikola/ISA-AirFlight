package rs.ftn.isa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.model.Hotel;
import rs.ftn.isa.service.HotelServis;

@RestController
@RequestMapping(value="api/hoteli")
public class HotelKontroler {
		@Autowired
		private HotelServis servis;
		
		@RequestMapping(value="/all", method = RequestMethod.GET)
		public List<Hotel> getAllHoteli(){		
			return  servis.findAll();
		}
		
		@RequestMapping(value="/test")
		public String vrati() {
			
			return "Uspesno";
		}		
		
}
