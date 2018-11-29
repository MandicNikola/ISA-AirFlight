package rs.ftn.isa.service;
import java.util.List;

import rs.ftn.isa.model.*;

public interface UserService {
//sve metode koje cemo koristiti u korisniku
		List<User> findAll();
		//aaa
		User findUserByMail(String mail);
		User saveUser(User korisnik);
		void removeUser(Long id);
				
}
