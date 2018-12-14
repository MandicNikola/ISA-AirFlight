package rs.ftn.isa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ftn.isa.model.Usluga;
import rs.ftn.isa.repository.UslugaRepository;

@Service
public class UslugaServiceImpl implements UslugaService {
	
	@Autowired
	private UslugaRepository repozitorijum;
	
	 public UslugaServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Usluga findUslugaById(Long id) {
		// TODO Auto-generated method stub
		return repozitorijum.findOneById(id);
	}

	@Override
	public List<Usluga> findAll() {
		// TODO Auto-generated method stub
		return repozitorijum.findAll();
	}

	@Override
	public Usluga saveUsluga(Usluga usluga) {
		// TODO Auto-generated method stub
		System.out.println("Usao u saveUsluga" + usluga);
		
		if(repozitorijum==null) {
			System.out.println("Repozitorijum je null");
		}
		return repozitorijum.save(usluga);
	}

	@Override
	public void removeUsluga(Long id) {
		// TODO Auto-generated method stub
		repozitorijum.deleteById(id);
	}

}
