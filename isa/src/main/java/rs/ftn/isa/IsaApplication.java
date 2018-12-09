package rs.ftn.isa;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Calendar;
=======
>>>>>>> 38c83b72ce95b97e44c74561d7047bafe53f428d
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

<<<<<<< HEAD
import rs.ftn.isa.model.PricelistRentCar;
import rs.ftn.isa.model.Usluga;
import rs.ftn.isa.service.CenovnikRentServiceImpl;
import rs.ftn.isa.service.UslugaServiceImpl;
=======
import rs.ftn.isa.model.PricelistHotel;
import rs.ftn.isa.model.PricelistRentCar;
import rs.ftn.isa.model.Usluga;
>>>>>>> 38c83b72ce95b97e44c74561d7047bafe53f428d

@SpringBootApplication
public class IsaApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsaApplication.class, args);
<<<<<<< HEAD
			
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
    	 
    	 
=======
		Usluga usluga1 = new Usluga("WIFI",1200);
		Usluga usluga2 = new Usluga("Bazen", 6000);

		PricelistHotel cenovnik = new PricelistHotel();

		@SuppressWarnings("deprecation")
		Date datum = new Date(2017, 5, 5);

		cenovnik.setDatum_primene(datum);

		usluga1.setCijene(cenovnik);
		usluga2.setCijene(cenovnik);

		Set<Usluga> usluge = new HashSet<Usluga>();
		usluge.add(usluga1);
		usluge.add(usluga2);

		cenovnik.setUsluge(usluge);
>>>>>>> 38c83b72ce95b97e44c74561d7047bafe53f428d
	}
}
