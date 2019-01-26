package rs.ftn.isa.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.dto.ReservationHotelDTO;
import rs.ftn.isa.model.RezervacijaHotel;
import rs.ftn.isa.model.RezervacijaRentCar;
import rs.ftn.isa.model.Room;
import rs.ftn.isa.model.User;
import rs.ftn.isa.service.RezervacijaHotelServiceImp;

@RestController
@RequestMapping(value="api/rezervacijehotel")
public class RezervacijaHotelController {
	@Autowired
	RezervacijaHotelServiceImp servis;
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public List<RezervacijaHotel> getAllRezervacije(){		
		return  servis.findAll();
	}
	
	@RequestMapping(value="/istorijaHotela",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<RezervacijaHotel> getHistoryHotel(@Context HttpServletRequest request){		
	System.out.println("Usao u getHistoryHotel");
		ArrayList<RezervacijaHotel> rezervacije = new ArrayList<RezervacijaHotel>();
		User korisnik = (User)request.getSession().getAttribute("ulogovan");		
			if(korisnik!=null) {
				System.out.println("Neko je ulogovan");
				Long idKor=korisnik.getId();
				String idKorS=idKor.toString();
				System.out.println("Id je "+idKor);
				ArrayList<RezervacijaHotel> pomRez=new ArrayList<RezervacijaHotel>();
				for(RezervacijaHotel rezervacija : servis.findAll()) {
							pomRez.add(rezervacija);
							System.out.println("Dodata reze");
				}
				
				System.out.println("Broj rez "+pomRez.size());
				
				for(RezervacijaHotel rezervacija : pomRez) {
					Long idRezKor=rezervacija.getUserHotel().getId();
					String idRezS=idRezKor.toString();
	
						if(idKorS.equals(idRezS)) {
							System.out.println("Ima rezervacija");
							System.out.println("Id je "+rezervacija.getId());
							System.out.println("Dat dolaska "+rezervacija.getDatumDolaska());
							System.out.println("Dat odlaska "+rezervacija.getDatumOdlaska());
							rezervacije.add(rezervacija);
						}
					}
				return rezervacije;
			}
		
		return rezervacije;
	}
			
	}
	
	

