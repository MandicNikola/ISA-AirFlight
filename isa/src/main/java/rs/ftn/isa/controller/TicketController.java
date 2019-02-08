package rs.ftn.isa.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.dto.DiscountDTO;
import rs.ftn.isa.model.DiscountTicket;
import rs.ftn.isa.model.SpecialDiscount;
import rs.ftn.isa.model.Ticket;
import rs.ftn.isa.service.DiscountTicketServiceImp;
import rs.ftn.isa.service.SpecialDiscountServiceImpl;
import rs.ftn.isa.service.TicketServiceImp;

@RestController
@RequestMapping(value="api/karte")
public class TicketController {

	
	@Autowired
	TicketServiceImp servis;
	
	@Autowired
	SpecialDiscountServiceImpl servisPopust;
	
	@Autowired
	DiscountTicketServiceImp servisPopustAvion;
	
	
	@RequestMapping(value="/addDiscount/{idKarte}/{idPopust}", method = RequestMethod.POST)
	public ResponseEntity<String> dodajPopust(@PathVariable("idKarte") Long id,@PathVariable("idPopust") Long idPopust){
			
			//specijalni popusti
			SpecialDiscount discount = servisPopust.findOneById(idPopust);
			Ticket karta = servis.findOneById(id);
			
			if(karta == null || discount == null)
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			
			boolean dodaj = true;
			for(DiscountTicket disc : karta.getPopustiKarte())
			{
				if(disc.getBodovi() == discount.getBodovi() && disc.getVrednost() == discount.getVrijednost())
				{
					dodaj = false;
					break;
				}
			}
			if(dodaj)
			{
				DiscountTicket dto = new DiscountTicket();
				dto.setBodovi(discount.getBodovi());
				dto.setVrednost(discount.getVrijednost());
				dto.setKartaPopust(karta);
				karta.getPopustiKarte().add(dto);
				servis.saveTicket(karta);
				return new ResponseEntity<String>("uspesno dodat popust", HttpStatus.OK);
			}
			
			
			return new ResponseEntity<String>("Popust vec postoji!", HttpStatus.OK);
	}
	
	@RequestMapping(value="/getDiscounts/{id}", method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DiscountDTO>>  getDiscounts(@PathVariable Long id){
			
		Ticket karta = servis.findOneById(id);
		if(karta == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		ArrayList<DiscountDTO> retDtos = new ArrayList<DiscountDTO>();
		for(DiscountTicket discount : karta.getPopustiKarte())
		{
			DiscountDTO dto = new DiscountDTO();
			dto.setBodovi(discount.getBodovi());
			dto.setPopust(discount.getVrednost());
			dto.setIdPopusta(discount.getId());
			retDtos.add(dto);
		}
	
		return new ResponseEntity<List<DiscountDTO>>(retDtos, HttpStatus.OK) ;		
	}
	
	
	
	@RequestMapping(value="/removeDiscount/{id}", method = RequestMethod.POST)
	public ResponseEntity<Long> removeDiscount(@PathVariable Long id){
			
			//specijalni popusti
			DiscountTicket discount = servisPopustAvion.findOneById(id);
			if(discount == null)
				return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
			
			Ticket karta = discount.getKartaPopust();
			discount.setKartaPopust(null);
			karta.getPopustiKarte().remove(discount);
			
			servisPopustAvion.removeDiscount(discount);
			servis.saveTicket(karta);
			
			return new ResponseEntity<Long>(karta.getId(), HttpStatus.OK);
	}
	
	
}
