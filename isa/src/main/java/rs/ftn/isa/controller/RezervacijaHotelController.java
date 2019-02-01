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
	public @ResponseBody ArrayList<ChartDTO> getDailyChart(@PathVariable String id){		
			System.out.println("Usao u getDaily chart");
			List<RezervacijaHotel> sveRez=servis.findAll();
			ArrayList<ChartDTO> podaci = new ArrayList<ChartDTO>();
			
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
						
						System.out.println("************");
						System.out.println("Datumi su : 1--> "+datum1+" a 2->>> "+datum2);
						ChartDTO noviPodatak = null;
						for(ChartDTO chart: podaci) {
							
							String datumPoredjenje =chart.getDatum().toString();
						    System.out.println("Datum poredjenja je "+datumPoredjenje);
							datumPoredjenje = formater.format(chart.getDatum());
							System.out.println("Datum formatiran je "+datumPoredjenje);
							datumPoredjenje = datumPoredjenje.split(" ")[0];
							System.out.println("Datum poredjenja posle splitovanja je "+datumPoredjenje);
											
							if(datumPoredjenje.equals(datum1)) {
									noviPodatak=chart;
									System.out.println("Vec postoji taj datum");
									break;
							}
													
						}
						if(noviPodatak != null) {
							podaci.remove(noviPodatak);
							noviPodatak.setBroj(noviPodatak.getBroj()+1);
							podaci.add(noviPodatak);
							System.out.println("Postoji datum u listi");
						}else {
							podaci.add(new ChartDTO(date1, 1));
							System.out.println("Novi datum je");
						}

						
						c.setTime(date1); 
						c.add(Calendar.DATE, 1);
						date1 =c.getTime();

						
					}
				}
			}
				Collections.sort(podaci);
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
	@RequestMapping(value="/mjesecnigrafik/{id}/brojMjeseci/{brojMj}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<ChartDTO> mjesecnigrafik(@PathVariable String id,@PathVariable String brojMj){		
			System.out.println("Usao u getDaily chart");
			List<RezervacijaHotel> sveRez=servis.findAll();
			ArrayList<ChartDTO> podaci = new ArrayList<ChartDTO>();
			
			Calendar c = Calendar.getInstance();
			if(brojMj.length() == 1) {
				brojMj = "0"+brojMj;
			}
			String datum = "2019-"+brojMj+"-01";
			Date myDate = parseDate(datum);
			
			c.setTime(myDate);
			
			int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
			int godina = 2019;
			int mjesec = Integer.parseInt(brojMj);
			
			c.set(Calendar.YEAR, godina);
			c.set(Calendar.MONTH, mjesec-1);
			int numDays = c.getActualMaximum(Calendar.DATE);
			podaci.add(new ChartDTO(myDate,0));
			int dan = 8-dayOfWeek;
			System.out.println("drugi dan"+dan);
			 myDate.setDate(dan);
			
			//popunimo moguce vrijednosti u podacima
			System.out.println(" broj dana "+numDays);
		/*	for(RezervacijaHotel rezervacija:sveRez) {
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
						
						System.out.println("************");
						System.out.println("Datumi su : 1--> "+datum1+" a 2->>> "+datum2);
						ChartDTO noviPodatak = null;
						for(ChartDTO chart: podaci) {
							
							String datumPoredjenje =chart.getDatum().toString();
						    System.out.println("Datum poredjenja je "+datumPoredjenje);
							datumPoredjenje = formater.format(chart.getDatum());
							System.out.println("Datum formatiran je "+datumPoredjenje);
							datumPoredjenje = datumPoredjenje.split(" ")[0];
							System.out.println("Datum poredjenja posle splitovanja je "+datumPoredjenje);
											
							if(datumPoredjenje.equals(datum1)) {
									noviPodatak=chart;
									System.out.println("Vec postoji taj datum");
									break;
							}
													
						}
						if(noviPodatak != null) {
							podaci.remove(noviPodatak);
							noviPodatak.setBroj(noviPodatak.getBroj()+1);
							podaci.add(noviPodatak);
							System.out.println("Postoji datum u listi");
						}else {
							podaci.add(new ChartDTO(date1, 1));
							System.out.println("Novi datum je");
						}

						
						c.setTime(date1); 
						c.add(Calendar.DATE, 1);
						date1 =c.getTime();

						
					}
				}
			}*/
				Collections.sort(podaci);
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
	
	

