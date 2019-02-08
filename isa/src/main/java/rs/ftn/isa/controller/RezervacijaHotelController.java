package rs.ftn.isa.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
import rs.ftn.isa.model.StatusRezervacije;
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
	
	@RequestMapping(value="/dnevnigrafik/{id}/brojMjeseci/{brojMj}/godina/{godina}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<ChartDTO> getDailyChart(@PathVariable String id,@PathVariable String brojMj,@PathVariable String godina){		
		int god =Integer.parseInt(godina);
		int mjesec = Integer.parseInt(brojMj);
	
			System.out.println("Usao u getDaily chart");
			List<RezervacijaHotel> sveRez=servis.findAll();
			ArrayList<ChartDTO> podaci = new ArrayList<ChartDTO>();
			
			Calendar calendar = Calendar.getInstance();
			
			Date newDate  = new Date(god-1900, mjesec-1, 1);
			System.out.println("Datum prvi je "+ newDate.toString());
			
			calendar.setTime(newDate);
			podaci.add(new ChartDTO(newDate, 0));
			int brojDanaMj = calendar.getActualMaximum(Calendar.DATE);
			while(newDate.getDate()+1 <= brojDanaMj) {
				
				calendar.add(Calendar.DATE, 1);
				newDate= calendar.getTime();
				podaci.add(new ChartDTO(newDate, 0));
			
			}
			
			
			
			for(RezervacijaHotel rezervacija:sveRez) {
				Long idHotela = 0L;
				for(Room sobe:rezervacija.getSobe()) {
					idHotela = sobe.getHotel().getId();
					break;
				}
				//rezervacija od odbaranog hotela
				if(idHotela.toString().equals(id)) {
					
					Date date1= rezervacija.getDatumDolaska();
					Date date2= rezervacija.getDatumOdlaska();
					System.out.println("Pocetak rez "+date1.toString());
					System.out.println("Kraj rez "+date2.toString());
					Calendar c = Calendar.getInstance(); 
				
					String datum2  =date2.toString();
					datum2  = datum2.split(" ")[0];

					SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
					while(poredi(date1,date2)) {
						
						String datum1 = "";
						datum1 = formater.format(date1);
						String[] nizS = datum1.split("-");
						
						if((god==Integer.parseInt(nizS[0])) && (mjesec==Integer.parseInt(nizS[1]))) {
							for(int i = 0;i<podaci.size();i++) {
								
								String datumPoredjenje =podaci.get(i).getDatum().toString();
						    	System.out.println("datum "+datumPoredjenje);
								datumPoredjenje = formater.format(podaci.get(i).getDatum());
								System.out.println("datum2 "+datumPoredjenje);
								
								if(datumPoredjenje.equals(datum1)) {
									
										int broj = podaci.get(i).getBroj()+1;
										podaci.get(i).setBroj(broj);
										break;
									}
														
							}
						}
						c.setTime(date1); 
						c.add(Calendar.DATE, 1);
						date1 =c.getTime();
						System.out.println("datum prvi je "+date1);
				
						
					}
				}
			}
				Collections.sort(podaci);
				for(int p=0;p<podaci.size();p++) {
					calendar.setTime(podaci.get(p).getDatum()); 
					calendar.add(Calendar.DATE, 1);
					Date datePom =calendar.getTime();
					
					podaci.get(p).setDatum(datePom);
					System.out.println(podaci.get(p));
			    }

				System.out.println("Broj podataka u listi je "+podaci.size());
				return podaci;
				
	}
	 public static Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat("yyyy-MM-dd").parse(date);
	     } catch (Exception e) {
	         return null;
	     }
	  }
	@RequestMapping(value="/sedmicnigrafik/{id}/brojMjeseci/{brojMj}/godina/{godina}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<ChartDTO> sedmicnigrafik(@PathVariable String id,@PathVariable String brojMj,@PathVariable String godina){		
			int god =Integer.parseInt(godina);
			int mjesec = Integer.parseInt(brojMj);
		
			
			List<RezervacijaHotel> sveRez=servis.findAll();
			ArrayList<ChartDTO> podaci = new ArrayList<ChartDTO>();
			
			Calendar calendar = Calendar.getInstance();
			
			Date newDate  = new Date(god-1900, mjesec-1, 1);
			System.out.println("Datum prvi je "+ newDate.toString());
			
			calendar.setTime(newDate);
			int danNedelja = calendar.get(Calendar.DAY_OF_WEEK);
			if(danNedelja!=1) {
				danNedelja=danNedelja-1;
			}else {
				danNedelja=7;
			}
			System.out.println("Dan u nedelji je" + danNedelja);
			
			//dodajemo pocetke nedelja u listu
			podaci.add(new ChartDTO(newDate, 0));
			//int noviDan = 8-danNedelja; 
			calendar.add(Calendar.DATE, 8-danNedelja);
			newDate= calendar.getTime();
			System.out.println("Drugi dan je "+newDate.toString());
			
			podaci.add(new ChartDTO(newDate, 0));
			
			int brojDanaMj = calendar.getActualMaximum(Calendar.DATE);
			while(newDate.getDate()+7 <= brojDanaMj) {
						
				calendar.add(Calendar.DATE, 7);
				newDate= calendar.getTime();
				podaci.add(new ChartDTO(newDate, 0));
			
			}
			for(int p=0;p<podaci.size();p++) {
					System.out.println(podaci.get(p));
			}
			

			for(RezervacijaHotel rezervacija:sveRez) {
				Long idHotela = 0L;
				for(Room sobe:rezervacija.getSobe()) {
					idHotela = sobe.getHotel().getId();
					break;
				}
				//rezervacija od odbaranog hotela
				if(idHotela.toString().equals(id)) {
					
					Date date1= rezervacija.getDatumDolaska();
					Date date2= rezervacija.getDatumOdlaska();
					
					
					Calendar c = Calendar.getInstance(); 
				
					String datum2  =date2.toString();
					datum2  = datum2.split(" ")[0];

					SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
					while(poredi(date1,date2)) {
						
						String datum1 = "";
						datum1 = formater.format(date1);
						String[] nizS = datum1.split("-");
						
						System.out.println("Datumi su : 1--> "+datum1+" a 2->>> "+datum2);
						
						if((god==Integer.parseInt(nizS[0])) && (mjesec==Integer.parseInt(nizS[1]))) {
							int index=-1;
							//prolazimo kroz listu u kojoj se nalaze poceci nedelja
							for(int i=1;i<podaci.size();i++) {	
								Date datumPoredjenje =podaci.get(i).getDatum();
								if(date1.before(datumPoredjenje)) {
									index=i-1;	
									break;
								}
							}
							
							if(index != -1) {
								int broj = podaci.get(index).getBroj()+1;
								podaci.get(index).setBroj(broj);
							}else {
								
								int broj=podaci.get(podaci.size()-1).getBroj()+1;
								podaci.get(podaci.size()-1).setBroj(broj);
							}
					
						}

						
						c.setTime(date1); 
						c.add(Calendar.DATE, 1);
						date1 =c.getTime();

						
					}
				}
			}
				Collections.sort(podaci);
				
				for(int p=0;p<podaci.size();p++) {
					calendar.setTime(podaci.get(p).getDatum()); 
					calendar.add(Calendar.DATE, 1);
					Date datePom =calendar.getTime();
					
					podaci.get(p).setDatum(datePom);
					System.out.println(podaci.get(p));
			    }
			
				System.out.println("Broj podataka u listi je "+podaci.size());
				return podaci;

	}

	
	public boolean poredi(Date date1, Date date2) {
		if(date1.getYear()==date2.getYear() && date1.getMonth()==date2.getMonth() && date1.getDate()==date2.getDate()) {
			System.out.println("Isti su");
			return false;
		}else {
			System.out.println("Nisu isti");
			System.out.println("Godine suu "+date1.getYear() + " a drugi "+date2.getYear());
			System.out.println("Meseci suu "+date1.getMonth() + " a drugi "+date2.getMonth());
			System.out.println("Dani  suu "+date1.getDate() + " a drugi "+date2.getDate());
			
			return true;
		}
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
			calendar.add(Calendar.DATE,-1);
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
					datumRez.setHours(0);
					datumRez.setMinutes(0);
					datumRez.setSeconds(0);
					
					if(datumOd.compareTo(datumRez)<=0) {
						prihod += rezervacija.getCijena();
					}
				}
			}
			return prihod;
	}


	@RequestMapping(value="/mjesecnigrafik/{id}/godina/{godina}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<ChartDTO> mjesecnigrafik(@PathVariable String id,@PathVariable String godina){		
			int god =Integer.parseInt(godina);
				
			List<RezervacijaHotel> sveRez=servis.findAll();
			ArrayList<ChartDTO> podaci = new ArrayList<ChartDTO>();
			
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
			
			for(int i=0;i<12;i++) {
				Date newDate  = new Date(god-1900, i, 1);
				podaci.add(new ChartDTO(newDate, 0));
			}

			for(RezervacijaHotel rezervacija:sveRez) {
				Long idHotela = 0L;
				for(Room sobe:rezervacija.getSobe()) {
					idHotela = sobe.getHotel().getId();
					break;
				}
				//rezervacija od odbaranog hotela
				if(idHotela.toString().equals(id)) {
					Date date1= rezervacija.getDatumDolaska();
					Date date2= rezervacija.getDatumOdlaska();
					Calendar c = Calendar.getInstance(); 
				 while(poredi(date1,date2)) {
						
						String datum1 = "";
						datum1 = formater.format(date1);
						String[] pom = datum1.split("-");
						
						if(Integer.parseInt(pom[0]) == god) {
							System.out.println("Poklapa se godina");
						    int mjesec=Integer.parseInt(pom[1])-1;    
						    podaci.get(mjesec).setBroj(podaci.get(mjesec).getBroj()+1);
						}
						c.setTime(date1); 
						c.add(Calendar.DATE, 1);
						date1 =c.getTime();
						}
				}
			}
				Collections.sort(podaci);
				
				for(int p=0;p<podaci.size();p++) {
					calendar.setTime(podaci.get(p).getDatum()); 
					calendar.add(Calendar.DATE, 1);
					Date datePom =calendar.getTime();
					
					podaci.get(p).setDatum(datePom);
					System.out.println(podaci.get(p));
			    }
			
				System.out.println("Broj podataka u listi je "+podaci.size());
				return podaci;

	}

	@RequestMapping(value="/refreshResHotel",
			method = RequestMethod.POST)
	public void refreshReservation(@Context HttpServletRequest request){		
	System.out.println("Usao u refreshRez Hotel");
		User korisnik = (User)request.getSession().getAttribute("ulogovan");
		Calendar cal = Calendar.getInstance();
			if(korisnik!=null) {
				Long idKor=korisnik.getId();
				String idKorS=idKor.toString();
				List<RezervacijaHotel> pomRez=servis.findAll();
			
				for(RezervacijaHotel rezervacija : pomRez) {
					Long idRezKor=rezervacija.getUserHotel().getId();
					String idRezS=idRezKor.toString();
	
						if(idKorS.equals(idRezS)) {
							Date today = new Date();
							Date datZavrsetka= rezervacija.getDatumOdlaska();
							//cal.setTime(today);
							//cal.add(Calendar.DATE,2);
							//today=cal.getTime();
							System.out.println("dat");
							System.out.println(today.toString());
							if(datZavrsetka.compareTo(today)<=0) {
								rezervacija.setStatus(StatusRezervacije.ZAVRSENA);
								servis.saveRezervacijaHotel(rezervacija);
							}
						}
					}
			}
		
	}



}
	
	

