package rs.ftn.isa.service;
import java.util.List;

import org.springframework.stereotype.Service;

import rs.ftn.isa.model.*;

@Service
public interface UserService {
//sve metode koje cemo koristiti u korisniku
		List<User> findAll();
		//aaa
		User findUserByMail(String mail);
		User saveUser(User korisnik);
		void removeUser(Long id);
		User findOneById(Long id);
		
		List<User> findUserByImeAndPrz(String ime, String prezime);
		
}
