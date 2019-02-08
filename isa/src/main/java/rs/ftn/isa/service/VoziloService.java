package rs.ftn.isa.service;

import java.util.List;

import rs.ftn.isa.model.Vehicle;

public interface VoziloService {

	Vehicle findVehicleById(Long id);
	
	List<Vehicle> findAll();
	Vehicle saveVehicle(Vehicle Vehicle) throws Exception;
	void removeVehicle(Long id);
}
