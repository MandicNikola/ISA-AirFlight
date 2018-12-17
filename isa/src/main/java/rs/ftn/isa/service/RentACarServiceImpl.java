package rs.ftn.isa.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ftn.isa.model.PricelistRentCar;
import rs.ftn.isa.model.RentACar;
import rs.ftn.isa.repository.RentACarRepository;
@Service
public class RentACarServiceImpl implements RentACarService {
	@Autowired
	RentACarRepository repozitorijum;

	@Override
	public RentACar findOneByNaziv(String name) {
		// TODO Auto-generated method stub
		return repozitorijum.findOneByNaziv(name);
	}

	@Override
	public RentACar saveRentACar(RentACar rentACar) {
	System.out.println("Usao u sacuvaj rentACar");	
		return repozitorijum.save(rentACar);
	
	}

	@Override
	public List<RentACar> findAll() {
		// TODO Auto-generated method stub
		return repozitorijum.findAll();
	}

	@Override
	public RentACar findOneById(Long id) {
		// TODO Auto-generated method stub
		System.out.println("Usao u findById");
		return repozitorijum.findOneById(id);
	}

	@Override
	public void removeRentACar(Long id) {
		// TODO Auto-generated method stub
		System.out.println("Izbrisan servis rent");
		repozitorijum.deleteById(id);
		
	}

	@Override
	public PricelistRentCar findAktivanCenovnik(Long id) {
		// TODO Auto-generated method stub
		RentACar rent = repozitorijum.findOneById(id);
		
		Set<PricelistRentCar> cenovnici =  rent.getCenovnici();
		if(rent.getCenovnici().size()==0) {
				return null; //nema aktivnih cenovnika
		}else {
			
			for(PricelistRentCar P : cenovnici) {
					if(P.isAktivan()) {
						return P;
					}
			}
		}
		return null;
		
	}
}