package rs.ftn.isa.service;
import java.util.List;

import rs.ftn.isa.model.*;

public interface KorisnikServis {
//sve metode koje cemo koristiti u korisniku
		List<Korisnik> findAll();
		
		Korisnik findKorisnikByMail(String mail);
		Korisnik findKorisnikById(Long id);
		Korisnik saveKorisnika(Korisnik korisnik);
		void removeKorisnika(Long id);
				
}
