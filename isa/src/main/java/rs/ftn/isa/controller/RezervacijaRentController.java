package rs.ftn.isa.controller;

import java.text.SimpleDateFormat;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.assertj.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.dto.ChartDTO;
import rs.ftn.isa.model.RentACar;
import rs.ftn.isa.model.RezervacijaRentCar;
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
						rezervacije.add(rezervacija);
					}
				}
		}
		return rezervacije;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value="/dailychart/{idRent}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ArrayList<ChartDTO> getDailyChart(@PathVariable String idRent){		
			System.out.println("Usao u getDaily chart");
			ArrayList<ChartDTO> podaci = new ArrayList<ChartDTO>();
			List<RezervacijaRentCar> sveRez=servis.findAll();
	        
			//treba da nadjemo sve rezervacije od rent-a-car sa idRez
			for(RezervacijaRentCar rezervacija:sveRez) {
					Vehicle vozilo = rezervacija.getVozilo();
					String idServis = vozilo.getFilijala().getServis().getId().toString();
					
					if(idServis.equals(idRent)) {
						//System.out.println("Pripada rent-a-car");
						//dodajemo u listu
						Date date1= rezervacija.getDatumPreuzimanja();
					
						Date date2= rezervacija.getDatumVracanja();
						//System.out.println("Pocetak rez "+date1.toString());
						//System.out.println("Kraj rez "+date2.toString());
						Calendar c = Calendar.getInstance(); 
						String datum2  =date2.toString();
						datum2  = datum2.split(" ")[0];

						SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
						
						while(poredi(date1,date2)) {

							String datum1 = "";
							datum1 = formater.format(date1);
							
						//	System.out.println("************");
							//System.out.println("Datumi su : 1--> "+datum1+" a 2->>> "+datum2);
							ChartDTO noviPodatak = null;
							for(ChartDTO chart: podaci) {

								String datumPoredjenje =chart.getDatum().toString();
								//System.out.println("Datum poredjenja je "+datumPoredjenje);
						    	datumPoredjenje = formater.format(chart.getDatum());
								//System.out.println("Datum formatiran je "+datumPoredjenje);

						    	datumPoredjenje = datumPoredjenje.split(" ")[0];

								//System.out.println("Datum poredjenja posle splitovanja je "+datumPoredjenje);
										
										if(datumPoredjenje.equals(datum1)) {
											noviPodatak=chart;
									//		System.out.println("Vec postoji taj datum");
											break;
										}
							
							}
							if(noviPodatak!=null) {
								int broj = noviPodatak.getBroj()+1;
								podaci.remove(noviPodatak);
								noviPodatak.setBroj(broj);
								podaci.add(noviPodatak);
								for(int k=0;k<podaci.size();k++) {
										System.out.println(podaci.get(k));
								}
								//System.out.println("Postoji datum u listi");
							}else {
								podaci.add(new ChartDTO(date1, 1));
								//System.out.println("Novi datum je");
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
			while(newDate.getDate()+7 < brojDana) {
						
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
		System.out.println("Datum jee"+datum.toString());
		double suma = 0;
		//treba da nadjemo sve rezervacije od rent-a-car sa idRez
		for(RezervacijaRentCar rezervacija:sveRez) {
				Vehicle vozilo = rezervacija.getVozilo();
				String idServis = vozilo.getFilijala().getServis().getId().toString();
				
				if(idServis.equals(id)) {
					System.out.println("Datum za poredjenje"+rezervacija.getDatumPreuzimanja());
					if(rezervacija.getDatumPreuzimanja().after(datum)) {
						System.out.println("Dodajemo vrednost");
						suma+=rezervacija.getCena();
					}
				}
		}
		return suma;
	}
}
