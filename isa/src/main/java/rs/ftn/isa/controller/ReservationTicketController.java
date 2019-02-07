package rs.ftn.isa.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.dto.ChartDTO;
import rs.ftn.isa.model.ReservationTicket;
import rs.ftn.isa.model.RezervacijaHotel;
import rs.ftn.isa.model.Room;
import rs.ftn.isa.model.Ticket;
import rs.ftn.isa.service.ReservationTicketServiceImp;
import rs.ftn.isa.service.RezervacijaHotelServiceImp;

@RestController
@RequestMapping(value="api/reservationTickets")
public class ReservationTicketController {

	@Autowired
	RezervacijaHotelServiceImp servis;
	
	@Autowired
	ReservationTicketServiceImp servisKarata;
	
	
	@RequestMapping(value="/dailychart/{podatak}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<ChartDTO> getDailyChart(@PathVariable String podatak){		
			String[] niz=podatak.split("=");
	        String id=niz[0];
	        int god =Integer.parseInt(niz[1]);
			int mesec = Integer.parseInt(niz[2]);
	
			System.out.println("Usao u getDaily chart");
			List<ReservationTicket> sveRez=servisKarata.findAll();
			ArrayList<ChartDTO> podaci = new ArrayList<ChartDTO>();
			
			Calendar calendar = Calendar.getInstance();
			
			Date newDate  = new Date(god-1900, mesec-1, 1);
			System.out.println("Datum prvi je "+ newDate.toString());
			
			calendar.setTime(newDate);
			podaci.add(new ChartDTO(newDate, 0));
			int brojDanaMj = calendar.getActualMaximum(Calendar.DATE);
			while(newDate.getDate()+1 <= brojDanaMj) {
				
				calendar.add(Calendar.DATE, 1);
				newDate= calendar.getTime();
				podaci.add(new ChartDTO(newDate, 0));
			
			}
			
			
			
			for(ReservationTicket rezervacija:sveRez) {
				Long idKompanije = 0L;
				for(Ticket karta:rezervacija.getKarte()) {
					idKompanije = karta.getLet().getAvioKomp().getId();
					break;
				}
				//rezervacija od odbaranog hotela
				if(idKompanije.toString().equals(id)) {
					
					Date date1= rezervacija.getDatumRezervacije();
				//	Date date2= rezervacija.getDatumOdlaska();
					System.out.println("Pocetak rez "+date1.toString());
				//	System.out.println("Kraj rez "+date2.toString());
					Calendar c = Calendar.getInstance(); 
				
					//String datum2  =date2.toString();
					//datum2  = datum2.split(" ")[0];

					SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
						
						String datum1 = "";
						datum1 = formater.format(date1);
						String[] nizS = datum1.split("-");
						
						if((god==Integer.parseInt(nizS[0])) && (mesec==Integer.parseInt(nizS[1]))) {
						for(int i = 0;i<podaci.size();i++) {
							
							String datumPoredjenje =podaci.get(i).getDatum().toString();
					    	System.out.println("datum "+datumPoredjenje);
							datumPoredjenje = formater.format(podaci.get(i).getDatum());
							System.out.println("datum2 "+datumPoredjenje);
							
							if(datumPoredjenje.equals(datum1)) {
								
									int broj = podaci.get(i).getBroj()+rezervacija.getKarte().size();
									podaci.get(i).setBroj(broj);
									break;
								}
													
						}
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
					
					if(datumOd.before(datumRez)) {
						prihod += rezervacija.getCijena();
					}
				}
			}
			return prihod;
	}
	
	@RequestMapping(value="/monthlychart/{podatak}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<ChartDTO> mjesecnigrafik(@PathVariable String podatak){		
			String[] niz = podatak.split("=");
			String id = niz[0];
			int god =Integer.parseInt(niz[1]);
				
			List<ReservationTicket> sveRez=servisKarata.findAll();
			ArrayList<ChartDTO> podaci = new ArrayList<ChartDTO>();
			
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
			
			for(int i=0;i<12;i++) {
				Date newDate  = new Date(god-1900, i, 1);
				podaci.add(new ChartDTO(newDate, 0));
			}

			for(ReservationTicket rezervacija:sveRez) {
				Long idKompanije = 0L;
				for(Ticket sobe:rezervacija.getKarte()) {
					idKompanije = sobe.getLet().getAvioKomp().getId();
					break;
				}
				//rezervacija od odbaranog hotela
				if(idKompanije.toString().equals(id)) {
					Date date1= rezervacija.getDatumRezervacije();
					//Date date2= rezervacija.getDatumOdlaska();
				 
						String datum1 = "";
						datum1 = formater.format(date1);
						String[] pom = datum1.split("-");
						
						if(Integer.parseInt(pom[0]) == god) {
							System.out.println("Poklapa se godina");
						    int mjesec=Integer.parseInt(pom[1])-1;    
						    podaci.get(mjesec).setBroj(podaci.get(mjesec).getBroj()+rezervacija.getKarte().size());
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
	
	
	@RequestMapping(value="/weeklychart/{podatak}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<ChartDTO> sedmicnigrafik(@PathVariable String podatak){		
			String[] niz = podatak.split("=");
			String id = niz[0];
			int god =Integer.parseInt(niz[1]);
			int mesec = Integer.parseInt(niz[2]);
		
			
			List<ReservationTicket> sveRez=servisKarata.findAll();
			ArrayList<ChartDTO> podaci = new ArrayList<ChartDTO>();
			
			Calendar calendar = Calendar.getInstance();
			
			Date newDate  = new Date(god-1900, mesec-1, 1);
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
			

			for(ReservationTicket rezervacija:sveRez) {
				Long idKompanije = 0L;
				for(Ticket sobe:rezervacija.getKarte()) {
					idKompanije = sobe.getLet().getAvioKomp().getId();
					break;
				}
				//rezervacija od odbaranog hotela
				if(idKompanije.toString().equals(id)) {
					
					Date date1= rezervacija.getDatumRezervacije();
					//Date date2= rezervacija.getDatumOdlaska();

					SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
					
						
						String datum1 = "";
						datum1 = formater.format(date1);
						String[] nizS = datum1.split("-");
						
						
						if((god==Integer.parseInt(nizS[0])) && (mesec==Integer.parseInt(nizS[1]))) {
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
								int broj = podaci.get(index).getBroj()+rezervacija.getKarte().size();
								podaci.get(index).setBroj(broj);
							}else {
								
								int broj=podaci.get(podaci.size()-1).getBroj()+rezervacija.getKarte().size();
								podaci.get(podaci.size()-1).setBroj(broj);
							}
					
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

	
}
