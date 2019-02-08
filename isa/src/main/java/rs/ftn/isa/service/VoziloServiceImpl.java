package rs.ftn.isa.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
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
	public List<Vehicle> findAll() {
		// TODO Auto-generated method stub
		return repozitorijum.findAll();
	}

	@Transactional
	@Override
	public Vehicle saveVehicle(Vehicle vehicle) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Sacuvano vozilo");
		return repozitorijum.save(vehicle);
	}

	@Override
	public void removeVehicle(Long id) {
		// TODO Auto-generated method stub
		System.out.println("Usao u removeVehicle");
		repozitorijum.deleteById(id);
	}



}
