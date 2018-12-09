package rs.ftn.isa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import rs.ftn.isa.model.PricelistRentCar;
import rs.ftn.isa.model.Usluga;
import rs.ftn.isa.service.CenovnikRentServiceImpl;
import rs.ftn.isa.service.UslugaServiceImpl;

@SpringBootApplication
public class IsaApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsaApplication.class, args);
			
		UslugaServiceImpl servis1 = new UslugaServiceImpl();
		CenovnikRentServiceImpl servis2 = new CenovnikRentServiceImpl();
		
		Usluga usluga1 = new Usluga("Pranje kola",1200);
		//usluga1.setId((long) 456);
		System.out.println(usluga1);
		
		Usluga usluga2 = new Usluga("Privatni vozac", 6000);
	//	usluga2.setId((long) 999);
		
		PricelistRentCar cenovnik = new PricelistRentCar();
		
        @SuppressWarnings("deprecation")
		Date datum = new Date(2017, 5, 5);
        
        cenovnik.setDatum_primene(datum);
        //cenovnik.setId(9999);
        
        usluga1.setLista(cenovnik);
        usluga2.setLista(cenovnik);
        
    	 Set<Usluga> usluge = new HashSet<Usluga>();
    	
    	 usluge.add(usluga1);
    	 usluge.add(usluga2);
    	 
    	 cenovnik.setUsluge(usluge);
    	
    	 servis1.saveUsluga(usluga1);
    	 servis1.saveUsluga(usluga2);
    	 
    	 servis2.savePricelistRentCar(cenovnik);
    	 
    	 
	}
}
