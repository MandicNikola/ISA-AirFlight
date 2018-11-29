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

	@Override
	public Korisnik findKorisnikByMail( String mail) {
		// TODO Auto-generated method stub
		return repozitorijum.findOneByMail(mail);
	}

	@Override
	public Korisnik findKorisnikById(Long id) {
		// TODO Auto-generated method stub
		return repozitorijum.findOneById(id);
	}

	@Override
	public Korisnik saveKorisnika(Korisnik korisnik) {
		// TODO Auto-generated method stub
		return repozitorijum.save(korisnik);
	}

	@Override
	public void removeKorisnika(Long id) {
		// TODO Auto-generated method stub
		repozitorijum.deleteById(id);
	}
	

}
