package rs.ftn.isa;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rs.ftn.isa.model.PricelistRentCar;
import rs.ftn.isa.model.Usluga;
import rs.ftn.isa.repository.CenovnikRentRepository;
import rs.ftn.isa.repository.RentACarRepository;
import rs.ftn.isa.repository.UslugaRepository;
import rs.ftn.isa.service.CenovnikRentServiceImpl;
import rs.ftn.isa.service.UslugaServiceImpl;
import rs.ftn.isa.model.PricelistHotel;

@SpringBootApplication
public class IsaApplication {
    @Autowired
    private UslugaRepository repozitorijum;

    @Autowired
    private CenovnikRentRepository repozitorijum2;

	public static void main(String[] args) {
		SpringApplication.run(IsaApplication.class, args);

		
	}
	  public void run(String... args) throws Exception {

			
		UslugaServiceImpl servis1 = new UslugaServiceImpl();
		CenovnikRentServiceImpl servis2 = new CenovnikRentServiceImpl();
		
		Usluga usluga1 = new Usluga("WIFI",1200);
		Usluga usluga2 = new Usluga("Bazen", 6000);

		usluga1.setId((long) 4566);
		
		
		PricelistRentCar cenovnik = new PricelistRentCar();

		@SuppressWarnings("deprecation")
		Date datum = new Date(2017, 5, 5);

		cenovnik.setDatum_primene(datum);

		usluga1.setLista(cenovnik);
		usluga2.setLista(cenovnik);

		repozitorijum.save(usluga1);
		repozitorijum.save(usluga2);
		
		Set<Usluga> usluge = new HashSet<Usluga>();
		usluge.add(usluga1);
		usluge.add(usluga2);

		cenovnik.setUsluge(usluge);
		
		repozitorijum2.save(cenovnik);
	    }
}
