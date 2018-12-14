package rs.ftn.isa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ftn.isa.model.Vehicle;
import rs.ftn.isa.repository.VoziloRepository;

@Service
public class VoziloServiceImpl implements VoziloService{

	@Autowired
	private VoziloRepository repozitorijum;

	@Override
	public Vehicle findVehicleById(Long id) {
		// TODO Auto-generated method stub
		
		return repozitorijum.findOneById(id);
	}

	@Override
	public Vehicle findVehicleByNaziv(String naziv) {
		// TODO Auto-generated method stub
		
		return repozitorijum.findOneByNaziv(naziv);
	}

	@Override
	public List<Vehicle> findAll() {
		// TODO Auto-generated method stub
		return repozitorijum.findAll();
	}

	@Override
	public Vehicle saveVehicle(Vehicle vehicle) {
		// TODO Auto-generated method stub
		System.out.println("Sacuvano vozilo");
		return repozitorijum.save(vehicle);
	}

	@Override
	public void removeVehicle(Long id) {
		// TODO Auto-generated method stub
		repozitorijum.deleteById(id);
	}

}
