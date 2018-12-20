package rs.ftn.isa.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.dto.UserDTO;
import rs.ftn.isa.model.RentACar;
import rs.ftn.isa.model.User;
import rs.ftn.isa.model.Vehicle;
import rs.ftn.isa.service.VoziloService;

@RestController
@RequestMapping(value="api/vozila")
public class VoziloController {

	@Autowired 
	VoziloService servis;
	
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public List<Vehicle> getAllVozila(){		
		return  servis.findAll();
	}	
	
			@RequestMapping(value="/registrovanje", 
					method = RequestMethod.POST,
					consumes = MediaType.APPLICATION_JSON_VALUE,
					produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody Vehicle registrujVozilo( @RequestBody Vehicle novo){		
		
		
		//validacija na serverskoj strani
		String naziv=novo.getNaziv();
		
		if(naziv.equals("")||naziv.equals("undefined")||naziv==null) {
			Vehicle povratni = new Vehicle();
			povratni.setGodiste(0);
			return povratni;
		}
		String marka=novo.getMarka();
		
		if(marka.equals("")||marka.equals("undefined")||marka==null) {
			Vehicle povratni = new Vehicle();
			povratni.setGodiste(1);
			return povratni;
		}
		
		String model=novo.getModel();
		
		if(model.equals("")||model.equals("undefined")||model==null) {
			Vehicle povratni = new Vehicle();
			povratni.setGodiste(2);
			return povratni;
		}
		
		int godiste=novo.getGodiste();
		
		if(godiste < 1990) {
			Vehicle povratni = new Vehicle();
			povratni.setGodiste(3);
			return povratni;
		}
		System.out.println("Usao u registrovanje vozila, naziv je "+ novo.getNaziv());

		Vehicle provera = servis.findVehicleByNaziv(novo.getNaziv());
			
		if(provera!=null) {
			//naziv mora biti jedinstven
			return null;
		}
		
		//servis.saveVehicle(novo);
		
		return novo;
		
		}	
		@RequestMapping(value="/deleteVozilo/{id}", method = RequestMethod.POST)
		public  void obrisiVozilo(@PathVariable String id){
				System.out.println("Usao u Delete vozilo dobio je "+id);
				
				
				servis.removeVehicle(Long.parseLong(id));
				
		}
			

	

}
