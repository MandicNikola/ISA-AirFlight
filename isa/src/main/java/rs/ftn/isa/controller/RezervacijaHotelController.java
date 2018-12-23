package rs.ftn.isa.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
	
	
	@RequestMapping(value="/rezervisi/{info}/sobe/{nizSoba}/nizUsluga/{listaUsl}", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public @ResponseBody RezervacijaHotel vratiPonude(@PathVariable("info") String info,
            @PathVariable("nizSoba") String nizSoba,@PathVariable("listaUsl") String listaUsl){
		System.out.println("uusao u rezervisi u rezervaciji");
		System.out.println("info "+info);
		System.out.println("nizSoba "+nizSoba);
		System.out.println("listaUsl "+listaUsl);
		//info 2018-12-30*2019-01-01*2
		//nizSoba 4
		//listaUsl 3
		String[] infoPom = info.split("\\*");
		String checkIN = infoPom[0];
		String checkOUT = infoPom[1];
		String broj = infoPom[2];
		int brLjudi = Integer.parseInt(broj);
		String[] indexSoba = null;
		
		if(nizSoba.contains(",")) {
			indexSoba = nizSoba.split(",");
		}else {
			indexSoba[0]=nizSoba;
		}
		boolean imaUsluga = true;
		if(listaUsl.equals("nema")) {
			imaUsluga = false;
		}
		String[] indexUsluga = null;
		
		if(imaUsluga == true) {
			if(listaUsl.contains(",")) {
				indexUsluga = listaUsl.split(",");
			}else {
				indexUsluga[0]=listaUsl;
			}
		}
		
		String[] datIN=checkIN.split("-");
		
		int godina=Integer.parseInt(datIN[0]);
		//mjesec krece od 0
		int mjesec=Integer.parseInt(datIN[1])-1;
		int dan=Integer.parseInt(datIN[2]);
	
		Calendar calendar = Calendar.getInstance();
		calendar.set(godina, mjesec, dan);
		Date datumCheckIn = calendar.getTime();
			
		
		System.out.println("Daatum je "+datumCheckIn);
		String[] datOUT=checkOUT.split("-");
		
		 godina=Integer.parseInt(datOUT[0]);
		//mjesec krece od 0
		 mjesec=Integer.parseInt(datOUT[1])-1;
		 dan=Integer.parseInt(datOUT[2]);
		 calendar.set(godina, mjesec, dan);
		Date datumCheckOut = calendar.getTime();
		
		System.out.println("Daatum je "+datumCheckOut);
	
		return null;
	
	}
}
