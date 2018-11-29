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
	public User findKorisnikByMail( String mail) {
		// TODO Auto-generated method stub
		return repozitorijum.findOneByMail(mail);
	}


	@Override
	public User saveKorisnika(User korisnik) {
		// TODO Auto-generated method stub
		return repozitorijum.save(korisnik);
	}

	@Override
	public void removeKorisnika(Long id) {
		// TODO Auto-generated method stub
		repozitorijum.deleteById(id);
	}

}
