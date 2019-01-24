package rs.ftn.isa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ftn.isa.model.User;
import rs.ftn.isa.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repozitorijum;
	
	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		
		return repozitorijum.findAll();
	}

	@Override
	public User findUserByMail( String mail) {
		// TODO Auto-generated method stub
		System.out.println("Usao u findUserbyMail");
		return repozitorijum.findOneByMail(mail);
	}


	@Override
	public User saveUser(User korisnik) {
		// TODO Auto-generated method stub
		System.out.println("Usao u saveUser");
		return repozitorijum.save(korisnik);
	}

	@Override
	public void removeUser(Long id) {
		// TODO Auto-generated method stub
		repozitorijum.deleteById(id);
	}
/*
	@Override
	public void verifikujKorisnika(String verifikovan, String mail) {
		
		repozitorijum.updateUserInfoByMail(verifikovan, mail);
	}
*/

	@Override
	public User findOneById(Long id) {
		// TODO Auto-generated method stub
		return repozitorijum.findOneById(id);
	}

	@Override
	public List<User> findUserByImeAndPrz(String ime, String prezime) {
		
		
		return repozitorijum.findUsersByImeAndPrezime(ime, prezime);
	}
	
	

	
}
