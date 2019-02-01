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
			
			System.out.println("godina je "+godina+" mesec je "+mesec);
			ArrayList<ChartDTO> podaci = new ArrayList<ChartDTO>();
			
		List<RezervacijaRentCar> sveRez=servis.findAll();
	        
			//treba da nadjemo sve rezervacije od rent-a-car sa idRez
			for(RezervacijaRentCar rezervacija:sveRez) {
					Vehicle vozilo = rezervacija.getVozilo();
					String idServis = vozilo.getFilijala().getServis().getId().toString();
					
					if(idServis.equals(idRent)) {
						//dodajemo u listu
						Date date1= rezervacija.getDatumPreuzimanja();
						Date date2= rezervacija.getDatumVracanja();
						
						System.out.println("Pocetak rezervacije "+date1.toString());
						System.out.println("Kraj rezervacije "+date2.toString());
						Calendar c = Calendar.getInstance(); 
						String datum2  =date2.toString();
						datum2  = datum2.split(" ")[0];

						SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
						ArrayList<Integer> brojNedelja = new ArrayList<Integer>();
						while(poredi(date1,date2)) {
							c.setTime(date1); 
							
							String datum1 = "";
							datum1 = formater.format(date1);
							//prvo proveravamo da li nam odgovara godina i mesec
							String[] nizS = datum1.split("-");
							System.out.println("************");
							System.out.println("Datumi su : 1--> "+datum1+" a 2->>> "+datum2);
							int godRez=Integer.parseInt(nizS[1]);

							System.out.println("parsirana godina "+Integer.parseInt(nizS[0])+" parsiran mesec "+godRez);
							if((godina==Integer.parseInt(nizS[0])) && (mesec==godRez)) {
										ChartDTO noviPodatak = null;
										System.out.println("Nedelja u mesecu je"+c.get(Calendar.WEEK_OF_MONTH));
										if(brojNedelja.size() == 0) {
											brojNedelja.add(c.get(Calendar.WEEK_OF_MONTH));
										}
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
										if(noviPodatak!=null) {
											int broj = noviPodatak.getBroj()+1;
											podaci.remove(noviPodatak);
											noviPodatak.setBroj(broj);
											podaci.add(noviPodatak);
											for(int k=0;k<podaci.size();k++) {
													System.out.println(podaci.get(k));
											}
											System.out.println("Postoji datum u listi");
										}else {
											podaci.add(new ChartDTO(date1, 1));
											System.out.println("Novi datum je");
										}
								
							}
						
							c.add(Calendar.DATE, 1);
							date1 =c.getTime();
							
						}
					}
					
			}
			Collections.sort(podaci);
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
			
}
