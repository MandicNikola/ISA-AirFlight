package rs.ftn.isa.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
	public @ResponseBody ArrayList<ReservationHotelDTO> getHistoryHotel(@Context HttpServletRequest request){		
	System.out.println("Usao u getHistoryHotel");
		ArrayList<ReservationHotelDTO> rezervacije = new ArrayList<ReservationHotelDTO>();
		User korisnik = (User)request.getSession().getAttribute("ulogovan");		
			if(korisnik!=null) {
				System.out.println("Neko je ulogovan");
				Long idKor=korisnik.getId();
				String idKorS=idKor.toString();
				System.out.println("Id je "+idKor);
				List<RezervacijaHotel> pomRez=servis.findAll();
				
				System.out.println("Broj rez "+pomRez.size());
				
				for(RezervacijaHotel rezervacija : pomRez) {
					Long idRezKor=rezervacija.getUserHotel().getId();
					String idRezS=idRezKor.toString();
	
						if(idKorS.equals(idRezS)) {
							System.out.println("Ima rezervacija");
							String nazivHotela="";
							for(Room r:rezervacija.getSobe()) {
								 nazivHotela=r.getHotel().getNaziv();
								 break;
							}
							//treba proveriti da li je broj soba u rezervaciji jednak broju ocenjenih soba
							//ako nije jednak onda postoje sobe koje treba oceniti
							//imacemo indikator brojSoba, ako je nula tad nema soba za ocenjivanje, 1 ima soba
							ReservationHotelDTO newRez= new ReservationHotelDTO(nazivHotela, rezervacija.getId(),rezervacija.getDatumDolaska(), rezervacija.getDatumOdlaska(),rezervacija.getCijena(),rezervacija.isOcenjenHotel(),rezervacija.getStatus()); 
							if(rezervacija.getSobe().size() == rezervacija.getOcenjeneSobe().size()) {
									newRez.setBrojLjudi(0);
							}else {
									newRez.setBrojLjudi(1);
							}
							
							System.out.println(newRez);
							rezervacije.add(newRez);
						}
					}
				return rezervacije;
			}
		
		return rezervacije;
	}
			
	}
	
	

