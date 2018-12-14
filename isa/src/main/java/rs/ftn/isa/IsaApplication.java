package rs.ftn.isa;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import rs.ftn.isa.model.PricelistHotel;
import rs.ftn.isa.model.PricelistRentCar;
import rs.ftn.isa.model.Usluga;

@SpringBootApplication
public class IsaApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsaApplication.class, args);
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
		
	}
}
