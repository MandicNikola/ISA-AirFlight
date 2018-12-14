package rs.ftn.isa;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import rs.ftn.isa.model.PricelistRentCar;
import rs.ftn.isa.model.RentACar;
import rs.ftn.isa.model.Usluga;
import rs.ftn.isa.model.Vehicle;
import rs.ftn.isa.repository.CenovnikRentRepository;
import rs.ftn.isa.repository.RentACarRepository;
import rs.ftn.isa.repository.UslugaRepository;
import rs.ftn.isa.service.CenovnikRentService;
import rs.ftn.isa.service.CenovnikRentServiceImpl;
import rs.ftn.isa.service.FilijalaService;
import rs.ftn.isa.service.RentACarService;
import rs.ftn.isa.service.UslugaService;
import rs.ftn.isa.service.UslugaServiceImpl;
import rs.ftn.isa.service.VoziloService;
import rs.ftn.isa.service.VoziloServiceImpl;
import rs.ftn.isa.model.Filijala;
import rs.ftn.isa.model.PricelistHotel;

@SpringBootApplication
public class IsaApplication {

	public static void main(String[] args) {
		
		ConfigurableApplicationContext c = SpringApplication.run(IsaApplication.class, args);
		UslugaService servis1 = (UslugaService) c.getBean("uslugaServiceImpl");
		CenovnikRentService servis2 = (CenovnikRentService) c.getBean("cenovnikRentServiceImpl");
		FilijalaService servis3=(FilijalaService)c.getBean("filijalaServiceImpl");
		RentACarService servis4 = (RentACarService)c.getBean("rentACarServiceImpl");
		VoziloService servis5 = (VoziloServiceImpl)c.getBean("voziloServiceImpl");
		
		
		
		Usluga usluga1 = new Usluga("WIFI",1200);
		Usluga usluga2 = new Usluga("Bazen", 6000);
		Usluga usluga3 = new Usluga("Limuzina",25000);
		Usluga usluga4 = new Usluga("Licni vozac", 16000);

		//usluga1.setId((long) 4566);
		System.out.println("u run");
		
		PricelistRentCar cenovnik = new PricelistRentCar();
		PricelistRentCar cenovnik2 = new PricelistRentCar();

		@SuppressWarnings("deprecation")
		Date datum = new Date(2017, 5, 5);

		@SuppressWarnings("deprecation")
		Date datum2 = new Date(2018, 5, 5);

		cenovnik.setDatum_primene(datum);
		cenovnik2.setDatum_primene(datum);

		usluga1.setLista(cenovnik);
		usluga2.setLista(cenovnik);
		usluga3.setLista(cenovnik2);
		usluga4.setLista(cenovnik);

		Set<Usluga> usluge = new HashSet<Usluga>();
		usluge.add(usluga1);
		usluge.add(usluga2);
		usluge.add(usluga4);
		cenovnik.setUsluge(usluge);
		
		Set<Usluga> usluge2 = new HashSet<Usluga>();
		usluge.add(usluga3);
		
		cenovnik2.setUsluge(usluge2);
		
		RentACar serviss= new RentACar("Bonero", "Temerinska","sjajna ponuda");

		//testiranje veze filijala
		Filijala fil1= new Filijala("Novi Sad", "Gunduliceva");
		Filijala fil2= new Filijala("Novi Sad", "Tekelijina");
		
		fil1.setServis(serviss);
		fil2.setServis(serviss);
		
		Set<Filijala> filijale = new HashSet<Filijala>();
		filijale.add(fil2);
		filijale.add(fil1);
		
		Vehicle vozilo1= new Vehicle("AMG", "Mercedes", "A klasa", 2017, 5, "A kategorija",7000, 5);
		Vehicle vozilo2= new Vehicle("Urban", "Mercedes", "A klasa", 2015, 5, "A kategorija",5000, 5);
		Vehicle vozilo3= new Vehicle("230", "Mercedes", "E klasa", 2017, 5, "E kategorija",6000, 5);
		
		vozilo1.setServisrent(serviss);
		vozilo2.setServisrent(serviss);
		vozilo3.setServisrent(serviss);
		
		Set<Vehicle> vozila = new HashSet<Vehicle>();
		vozila.add(vozilo1);
		vozila.add(vozilo2);
		vozila.add(vozilo3);
		
		serviss.setVozila(vozila);
		serviss.setFilijale(filijale);
		
		cenovnik.setRentcar(serviss);
		
		serviss.setCenovnik(cenovnik);

		servis4.saveRentACar(serviss);
		
		
	
		
		}

}
