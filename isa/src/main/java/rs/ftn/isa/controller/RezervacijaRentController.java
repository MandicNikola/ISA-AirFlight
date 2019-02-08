package rs.ftn.isa.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
import rs.ftn.isa.model.RentACar;
import rs.ftn.isa.model.RezervacijaRentCar;
import rs.ftn.isa.model.StatusRezervacije;
import rs.ftn.isa.model.User;
import rs.ftn.isa.model.Vehicle;
import rs.ftn.isa.service.RezervacijaRentServiceImpl;

@RestController
@RequestMapping(value="api/rezervacijerent")
public class RezervacijaRentController {

	@Autowired
	RezervacijaRentServiceImpl servis;
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public List<RezervacijaRentCar> getAllRezervacije(){		
		return  servis.findAll();
	}
	
	@RequestMapping(value="/istorijaRent",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<RezervacijaRentCar> getHistoryRent(@Context HttpServletRequest request){		
		ArrayList<RezervacijaRentCar> rezervacije = new ArrayList<RezervacijaRentCar>();
		User korisnik = (User)request.getSession().getAttribute("ulogovan");		
		System.out.println("Usao u getHis rent");
		if(korisnik!=null) {
			System.out.println("Neko je ulogovan");
			Long idKor=korisnik.getId();
			String idKorS=idKor.toString();
			System.out.println("Id je "+idKor);
			
			List<RezervacijaRentCar> sveRez=servis.findAll();
			System.out.println("Ukupan broj rez "+sveRez.size());
			
			for(RezervacijaRentCar rezervacija:sveRez) {
				Long idRezKor=rezervacija.getKorisnik().getId();
				String idRezS=idRezKor.toString();
				System.out.println("Id rez korisnik je "+idRezKor);
				if(idKorS.equals(idRezS)) {
					System.out.println("Jednaki su");
						System.out.println("Ima rezervacija");
						System.out.println("Dodata rezervacija sa check in"+rezervacija.getDatumPreuzimanja());
						System.out.println("Id je "+rezervacija.getId());
						rezervacija.setMestoVracanja(rezervacija.getVozilo().getFilijala().getServis().getNaziv());
						rezervacije.add(rezervacija);
					}
				}
		}
		return rezervacije;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value="/dailychart/{podatak}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<ChartDTO> getDailyChart(@PathVariable String podatak){		
			System.out.println("Usao u getDaily chart");
			ArrayList<ChartDTO> podaci = new ArrayList<ChartDTO>();
			List<RezervacijaRentCar> sveRez=servis.findAll();
	        String[] niz=podatak.split("=");
	        String idRent=niz[0];
	        int godina =Integer.parseInt(niz[1]);
			int mesec = Integer.parseInt(niz[2]);
			Calendar kalendar = Calendar.getInstance();
			Date newDate  = new Date(godina-1900, mesec-1, 1);
			System.out.println(newDate.toString());
			podaci.add(new ChartDTO(newDate, 0));
			kalendar.setTime(newDate);
			int brojDana = kalendar.getActualMaximum(Calendar.DATE);
			while(newDate.getDate()+1 <= brojDana) {
				kalendar.add(Calendar.DATE, 1);
				newDate= kalendar.getTime();
				podaci.add(new ChartDTO(newDate, 0));
			}
		
			//treba da nadjemo sve rezervacije od rent-a-car sa idRez
			for(RezervacijaRentCar rezervacija:sveRez) {
					Vehicle vozilo = rezervacija.getVozilo();
					String idServis = vozilo.getFilijala().getServis().getId().toString();
					
					if(idServis.equals(idRent)) {
						Date date1= rezervacija.getDatumPreuzimanja();
						Date date2= rezervacija.getDatumVracanja();
						Calendar c = Calendar.getInstance(); 
						String datum2  =date2.toString();
						datum2  = datum2.split(" ")[0];

						SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
						
						while(poredi(date1,date2)) {

							String datum1 = "";
							datum1 = formater.format(date1);
							String[] nizS = datum1.split("-");
							int godRez=Integer.parseInt(nizS[1]);

							if((godina==Integer.parseInt(nizS[0])) && (mesec==godRez)) {
								int index=-1;
								for(int i=0;i < podaci.size(); i++) {
									
									String datumPoredjenje =podaci.get(i).getDatum().toString();
							    	System.out.println("datum "+datumPoredjenje);
									datumPoredjenje = formater.format(podaci.get(i).getDatum());
									System.out.println("datum2 "+datumPoredjenje);
							    
									if(datumPoredjenje.equals(datum1)) {
										index=i;
							    		break;
									}
								}
								int broj = podaci.get(index).getBroj()+1;
								podaci.get(index).setBroj(broj);
							}	
							
							c.setTime(date1); 
							c.add(Calendar.DATE, 1);
							date1 =c.getTime();
							
						}
					}
					
			}
			Collections.sort(podaci);
			for(int p=0;p<podaci.size();p++) {
				kalendar.setTime(podaci.get(p).getDatum()); 
				kalendar.add(Calendar.DATE, 1);
				Date datePom =kalendar.getTime();
				podaci.get(p).setDatum(datePom);
				System.out.println(podaci.get(p));
		    }
		
			System.out.println("Broj podataka u listi je "+podaci.size());
			return podaci;
	}
	@RequestMapping(value="/weeklychart/{podatak}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<ChartDTO> getWeeklyChart(@PathVariable String podatak){	
		System.out.println("Usao u getWekkly");
			String[] niz = podatak.split("=");
			String idRent = niz[0];
			int godina =Integer.parseInt(niz[1]);
			int mesec = Integer.parseInt(niz[2]);
			Calendar kalendar = Calendar.getInstance();
			ArrayList<ChartDTO> podaci = new ArrayList<ChartDTO>();
			Date newDate  = new Date(godina-1900, mesec-1, 1);
			System.out.println("Datum prvi je "+ newDate.toString());
			kalendar.setTime(newDate);
			int danNedelja = kalendar.get(Calendar.DAY_OF_WEEK);
			if(danNedelja==1) {
				danNedelja=7;
			}else {
				danNedelja-=1;
			}
			
			//dodajemo pocetke nedelja u listu
			podaci.add(new ChartDTO(newDate, 0));
			int dodajDan = 8-danNedelja; 
			kalendar.add(Calendar.DATE, dodajDan);
			newDate= kalendar.getTime();
			podaci.add(new ChartDTO(newDate, 0));
			int brojDana = kalendar.getActualMaximum(Calendar.DATE);
			while(newDate.getDate()+7 <= brojDana) {
						
				kalendar.add(Calendar.DATE, 7);
				newDate= kalendar.getTime();
				podaci.add(new ChartDTO(newDate, 0));
			}
			for(int p=0;p<podaci.size();p++) {
					System.out.println(podaci.get(p));
			}
			
			List<RezervacijaRentCar> sveRez=servis.findAll();
	        
			//treba da nadjemo sve rezervacije od rent-a-car sa idRez
			for(RezervacijaRentCar rezervacija:sveRez) {
					Vehicle vozilo = rezervacija.getVozilo();
					String idServis = vozilo.getFilijala().getServis().getId().toString();
					
					if(idServis.equals(idRent)) {
						//dodajemo u listu
						Date date1= rezervacija.getDatumPreuzimanja();
						Date date2= rezervacija.getDatumVracanja();
						
						Calendar c = Calendar.getInstance(); 
						String datum2  =date2.toString();
						datum2  = datum2.split(" ")[0];

						SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
				
						
						while(poredi(date1,date2)) {
							
							String datum1 = "";
							datum1 = formater.format(date1);
							//prvo proveravamo da li nam odgovara godina i mesec
							String[] nizS = datum1.split("-");
							int godRez=Integer.parseInt(nizS[1]);

							System.out.println("parsirana godina "+Integer.parseInt(nizS[0])+" parsiran mesec "+godRez);
							if((godina==Integer.parseInt(nizS[0])) && (mesec==godRez)) {
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
				kalendar.setTime(podaci.get(p).getDatum()); 
				kalendar.add(Calendar.DATE, 1);
				Date datePom =kalendar.getTime();
				
				podaci.get(p).setDatum(datePom);
				System.out.println(podaci.get(p));
		    }
		
			System.out.println("Broj podataka u listi je "+podaci.size());
			
			return podaci;
	}
	@RequestMapping(value="/monthlychart/{podatak}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<ChartDTO> getMonthlyChart(@PathVariable String podatak){	
		System.out.println("Usao u getMonthly");
			String[] niz = podatak.split("=");
			String idRent = niz[0];
			int godina =Integer.parseInt(niz[1]);
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
			Calendar kalendar = Calendar.getInstance();
			ArrayList<ChartDTO> podaci = new ArrayList<ChartDTO>();
			
			for(int i=0;i<12;i++) {
				Date newDate  = new Date(godina-1900, i, 1);
				podaci.add(new ChartDTO(newDate, 0));
			}
			
			for(int p=0;p<podaci.size();p++) {
					System.out.println(podaci.get(p));
			}
			
			List<RezervacijaRentCar> sveRez=servis.findAll();
	        
			//treba da nadjemo sve rezervacije od rent-a-car sa idRez
			for(RezervacijaRentCar rezervacija:sveRez) {
					Vehicle vozilo = rezervacija.getVozilo();
					String idServis = vozilo.getFilijala().getServis().getId().toString();
					
					if(idServis.equals(idRent)) {
						//dodajemo u listu
						Date date1= rezervacija.getDatumPreuzimanja();
						Date date2= rezervacija.getDatumVracanja();
						
						Calendar c = Calendar.getInstance(); 
						String datum2  =date2.toString();
						datum2  = datum2.split(" ")[0];
						
						while(poredi(date1,date2)) {
							System.out.println("Porede se "+date1.toString()+" i "+date2.toString());
							String datum1 = "";
							datum1 = formater.format(date1);
							//prvo proveravamo da li nam odgovara godina i mesec
							String[] nizS = datum1.split("-");
							int godRez=Integer.parseInt(nizS[1]);

							System.out.println("parsirana godina "+Integer.parseInt(nizS[0])+" parsiran mesec "+godRez);
							if(godina==Integer.parseInt(nizS[0])) {
								System.out.println("Poklapa se godina");
							          int index=Integer.parseInt(nizS[1])-1;
							          int broj = podaci.get(index).getBroj()+1;
									   podaci.get(index).setBroj(broj);
									   
									for(int p=0;p<podaci.size();p++) {
											System.out.println(podaci.get(p));
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
				kalendar.setTime(podaci.get(p).getDatum()); 
				kalendar.add(Calendar.DATE, 1);
				Date datePom =kalendar.getTime();
				
				podaci.get(p).setDatum(datePom);
			System.out.println(podaci.get(p));
		    }
		
			System.out.println("Broj podataka u listi je "+podaci.size());
			
			return podaci;
	}

	public boolean poredi(Date date1, Date date2) {
		if(date1.getYear()==date2.getYear() && date1.getMonth()==date2.getMonth() && date1.getDate()==date2.getDate()) {
			System.out.println("Godine suu "+date1.getYear() + " a drugi "+date2.getYear());
			System.out.println("Meseci suu "+date1.getMonth() + " a drugi "+date2.getMonth());
			System.out.println("Isti su");
			return false;
		}else {
			//System.out.println("Nisu isti");
			return true;
		}
		}
	@RequestMapping(value="/getIncome/{id}/start/{start}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody double getPrihode(@PathVariable String id,@PathVariable String start){		
	
		List<RezervacijaRentCar> sveRez=servis.findAll();
		System.out.println("Parametar je "+start);
		String[] niz=start.split("-");
		int year=Integer.parseInt(niz[0])-1900;
		int month=Integer.parseInt(niz[1])-1;
		int date=Integer.parseInt(niz[2]);
		Date datum = new Date(year, month, date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(datum);
		cal.add(Calendar.DATE,-1);
		datum = cal.getTime();
		System.out.println("Datum jee"+datum.toString());
		double suma = 0;
		//treba da nadjemo sve rezervacije od rent-a-car sa idRez
		for(RezervacijaRentCar rezervacija:sveRez) {
				Vehicle vozilo = rezervacija.getVozilo();
				String idServis = vozilo.getFilijala().getServis().getId().toString();
				
				if(idServis.equals(id)) {
					System.out.println("Datum za poredjenje"+rezervacija.getDatumPreuzimanja());
					Date datumRez= rezervacija.getDatumPreuzimanja();
					datumRez.setHours(0);
					datumRez.setMinutes(0);
					datumRez.setSeconds(0);

					if(datum.before(rezervacija.getDatumPreuzimanja())) {
						System.out.println("Dodajemo vrednost");
						suma+=rezervacija.getCena();
					}
				}
		}
		return suma;
	}
	@RequestMapping(value="/refreshResCar",
			method = RequestMethod.POST)
	public void refreshReservation(@Context HttpServletRequest request){		
	System.out.println("Usao u refreshRez Rent");
		User korisnik = (User)request.getSession().getAttribute("ulogovan");
		Calendar cal = Calendar.getInstance();
			if(korisnik!=null) {
				Long idKor=korisnik.getId();
				String idKorS=idKor.toString();
				List<RezervacijaRentCar> pomRez=servis.findAll();
			
				for(RezervacijaRentCar rezervacija : pomRez) {
					Long idRezKor=rezervacija.getKorisnik().getId();
					String idRezS=idRezKor.toString();
	
						if(idKorS.equals(idRezS)) {
							Date today = new Date();
							Date datZavrsetka= rezervacija.getDatumVracanja();
							//cal.setTime(today);
							//cal.add(Calendar.DATE,2);
							//today=cal.getTime();
							System.out.println("dat");
							System.out.println(today.toString());
							if(datZavrsetka.compareTo(today)<=0) {
								rezervacija.setStatus(StatusRezervacije.ZAVRSENA);
								servis.saveRezervacijaRentCar(rezervacija);
							}
						}
					}
			}
		
	}

}
