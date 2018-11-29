package rs.ftn.isa.service;
import java.util.List;

import rs.ftn.isa.model.*;

public interface UserService {
//sve metode koje cemo koristiti u korisniku
		List<User> findAll();
		
		User findKorisnikByMail(String mail);
		User findKorisnikById(Long id);
		User saveKorisnika(User korisnik);
		void removeKorisnika(Long id);
				
}
