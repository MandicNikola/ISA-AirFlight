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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.dto.ChartDTO;
import rs.ftn.isa.dto.ReservationHotelDTO;
import rs.ftn.isa.dto.RoomDTO;
import rs.ftn.isa.model.RezervacijaHotel;
import rs.ftn.isa.model.RezervacijaRentCar;
import rs.ftn.isa.model.Room;
import rs.ftn.isa.model.User;
import rs.ftn.isa.model.Vehicle;
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
	
	@RequestMapping(value="/listaSoba/{idRez}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<RoomDTO> getRoomsRate(@PathVariable String idRez){		

		System.out.println("Usao u getRoomsRate");
		
		ArrayList<RoomDTO> sobeZaOcenjivanje = new ArrayList<RoomDTO>();
		
		Long id= Long.parseLong(idRez);
		RezervacijaHotel pomRez=servis.findReservationById(id);
				if(pomRez!=null) {
					System.out.println("Nasao je rez");
					//treba da vratimo sobe koje nisu ocenjene
					Set<Room> sobe = pomRez.getSobe();
					ArrayList<Room> ocenjeneSobe = new ArrayList<Room>();
					for(Room rr: pomRez.getOcenjeneSobe()){
						ocenjeneSobe.add(rr);
					}
					System.out.println("Broj soba u rezervaciji je "+sobe.size());
					
					for(Room R : sobe) {
						System.out.println("Soba je "+R.getId());
							if(!ocenjeneSobe.contains(R)) {
								//ako nije ocenjena ubacujemo u povratnu listu
								sobeZaOcenjivanje.add(new RoomDTO(R.getId(), R.getTip(),R.getOcjena(),R.getHotel().getNaziv(), id));
								System.out.println("Dodata soba "+R.getId());
							}
					}
					
				}else {
					return sobeZaOcenjivanje;
				}		
				System.out.println("Broj soba je "+sobeZaOcenjivanje.size());
				
				return sobeZaOcenjivanje;

	
	}
	
	@RequestMapping(value="/dnevnigrafik/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ChartDTO getDailyChart(@PathVariable String id){		
			System.out.println("Usao u getDaily chart");
			ChartDTO podaci = new ChartDTO();
			List<RezervacijaHotel> sveRez=servis.findAll();
	
			//treba da nadjemo sve rezervacije od hotela sa idRez
			/*for(RezervacijaHotel rezervacija:sveRez) {
				Long idHotela = 0L;
				for(Room sobe:rezervacija.getSobe()) {
					idHotela = sobe.getHotel().getId();
					break;
				}
				
				if(idHotela.toString().equals(id)) {
					//dodajemo u listu
					Date date  = rezervacija.getDatumDolaska();
					String datum  =date.toString();
					System.out.println("datuum je "+datum);
					datum  = datum.split(" ")[0];
					System.out.println("datum dolaska "+date);
					Date date2= rezervacija.getDatumOdlaska();
					System.out.println("datum odlaska "+date2);
					Calendar cal = Calendar.getInstance();
					cal.setTime();
					ChartDTO noviPodatak = null;
					for(ChartDTO chart: podaci) {
						String datumPoredjenje =chart.getDatum().toString();
				    	datumPoredjenje = datumPoredjenje.split(" ")[0];
						
						if(chart.getDatum().equals(datum)) {
							noviPodatak=chart;
							break;
						}
					}
					if(noviPodatak != null) {
						podaci.remove(noviPodatak);
						int dosadasnjiBroj = noviPodatak.getBroj();
						noviPodatak.setBroj(dosadasnjiBroj+1);
						podaci.add(noviPodatak);
					}else {
						//noviPodatak = new ChartDTO(datum, 1);
						podaci.add(noviPodatak);
					}
				}
			}*/
			return null;
	}


	@RequestMapping(value="/vratiPrihode/{id}/pocetak/{pocetak}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody double getPrihode(@PathVariable String id,@PathVariable String pocetak){		
			System.out.println("Usao u vrati prihode");
			List<RezervacijaHotel> sveRez=servis.findAll();
			
			double prihod = 0;
			
			String[] datIN=pocetak.split("-");
			int godina=Integer.parseInt(datIN[0]);
			//mjesec krece od 0
			int mjesec=Integer.parseInt(datIN[1])-1;
			int dan=Integer.parseInt(datIN[2]);
		
			Calendar calendar = Calendar.getInstance();
			calendar.set(godina, mjesec, dan);
			Date datumOd = calendar.getTime();
		
			//treba da nadjemo sve rezervacije od hotela sa idRez
			for(RezervacijaHotel rezervacija:sveRez) {
				Long idHotela = 0L;
				for(Room sobe:rezervacija.getSobe()) {
					idHotela = sobe.getHotel().getId();
					break;
				}
				
				if(idHotela.toString().equals(id)) {
					//dodajemo u listu
					Date datumRez  = rezervacija.getDatumDolaska();
					if(datumRez.compareTo(datumOd)>=0) {
						prihod += rezervacija.getCijena();
					}
				}
			}
			return prihod;
	}




}
	
	

