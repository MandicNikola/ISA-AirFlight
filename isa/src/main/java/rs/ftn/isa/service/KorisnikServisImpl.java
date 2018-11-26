package rs.ftn.isa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ftn.isa.model.Korisnik;
import rs.ftn.isa.repository.KorisnikRepository;


@Service
public class KorisnikServisImpl implements KorisnikServis {

	@Autowired
	private KorisnikRepository repozitorijum;
	
	@Override
	public List<Korisnik> findAll() {
		// TODO Auto-generated method stub
		
		return repozitorijum.findAll();
	}
	

}
