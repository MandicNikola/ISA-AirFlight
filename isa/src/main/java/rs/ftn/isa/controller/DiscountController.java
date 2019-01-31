package rs.ftn.isa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.model.Discount;
import rs.ftn.isa.model.Room;
import rs.ftn.isa.service.DiscountServiceImp;
import rs.ftn.isa.service.RoomServiceImp;

@RestController
@RequestMapping(value="api/popusti")
public class DiscountController {
	@Autowired
	private DiscountServiceImp servis;

	@RequestMapping(value="/ukloniPopust/{id}", 
			method = RequestMethod.POST
			)
	public void ukloniPopust(@PathVariable("id") String id){
		String[] pom = id.split("\\.");
		Long idLong = Long.parseLong(pom[0]);
		servis.removeDiscount(idLong);
	}
	
}
