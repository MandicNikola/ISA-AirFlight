package rs.ftn.isa.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rs.ftn.isa.dto.FilijalaDTO;
import rs.ftn.isa.dto.RoomDTO;
import rs.ftn.isa.dto.VehicleDTO;
import rs.ftn.isa.model.CategoryCar;
import rs.ftn.isa.model.Filijala;
import rs.ftn.isa.model.Hotel;
import rs.ftn.isa.model.RentACar;
import rs.ftn.isa.model.Room;
import rs.ftn.isa.model.Vehicle;
import rs.ftn.isa.service.FilijalaServiceImpl;

@RestController
@RequestMapping(value="api/filijale")
public class FilijalaController {

	@Autowired
	private FilijalaServiceImpl servis;
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public List<Filijala> getAllFilijale(){		
		return  servis.findAll();
	}
	
	@RequestMapping(value="/poGradu/{grad}", method = RequestMethod.GET)
	public ResponseEntity<List<FilijalaDTO>> filijaleUGradu(@PathVariable String grad){		
		
		System.out.println(grad);
		List<Filijala> filijale = servis.findAllByGrad(grad);
		if(filijale == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		ArrayList<FilijalaDTO> retDto = new ArrayList<FilijalaDTO>();
		for(Filijala filijala : filijale)
		{
			System.out.println("filijala");
			FilijalaDTO dto = new FilijalaDTO();
			dto.setAdresa(filijala.getUlica());
			dto.setGrad(filijala.getGrad());
			dto.setIdServisa(filijala.getServis().getId());
			dto.setNaziv(filijala.getServis().getNaziv());
			retDto.add(dto);
		}
		
		return new ResponseEntity<List<FilijalaDTO>>(retDto, HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value="/vratiFilijalu/{id}",
					method = RequestMethod.GET,
					produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Filijala getFilijaluById(@PathVariable Long id){	
		Filijala povratna = servis.findFilijalaById(id);
		System.out.println(povratna.getGrad());
		return povratna;
	}
	
	@RequestMapping(value="/registrovanje", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
public @ResponseBody Filijala registrujFilijalu( @RequestBody Filijala nova){		
	
	System.out.println("Usao u registrujFilijalu");

	ArrayList<Filijala> lista = (ArrayList<Filijala>) servis.findAllByUlica(nova.getUlica());
	boolean postoji = false;
	
	for(Filijala F : lista) {
		//ako postoje filijale sa istim nazivom ulice proveravamo da li se nalaze u istom gradu
		if(F.getGrad().equals(nova.getGrad())) {
			postoji=true;
			break;
		}
	}
	if(postoji) {
		//ulica+grad mora biti jedinstven
		return null;
	}
	
	return nova;
}	

	@RequestMapping(value="/deleteFilijalu/{id}", method = RequestMethod.POST)
	public  void obrisiFilijalu(@PathVariable String id){
			System.out.println("Usao u Delete filijalu dobio je "+id);
			
			
			servis.removeFilijala(Long.parseLong(id));
			
	}
		

	@RequestMapping(value="/izmena", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Filijala izmeniFilijalu(@RequestBody Filijala nova){		
		
		System.out.println("Usao u izmeniFilijalu");
		Long id=nova.getId();
		
		Filijala stara = servis.findFilijalaById(id);
		if(stara.getGrad().equals(nova.getGrad())) {
			if(stara.getUlica().equals(nova.getUlica())) {
				System.out.println("Nista nije izmenjeno, vrati filijalu");
				Long rent=stara.getServis().getId();
				stara.setUlica(rent.toString());
				return stara;
			}
		}
		
		//provera da li postoji vec filijala sa unetom adresom i gradom
		ArrayList<Filijala> lista = (ArrayList<Filijala>) servis.findAllByUlica(nova.getUlica());
		boolean postoji = false;
		
		for(Filijala F : lista) {
			//ako postoje filijale sa istim nazivom ulice proveravamo da li se nalaze u istom gradu
			if(F.getGrad().equals(nova.getGrad())) {
				postoji=true;
				break;
			}
		}
		Filijala f= new Filijala();
		if(postoji) {
			//ulica+grad mora biti jedinstven
			f.setUlica("NE");
			return f;
		}
		
		System.out.println("Menjaj filijalu sve je ispunjeno");
		stara.setUlica(nova.getUlica());
		stara.setGrad(nova.getGrad());
		//izmenili podatke
		servis.saveFilijala(stara);
		

		Long rent=stara.getServis().getId();
		stara.setUlica(rent.toString());
		return stara;

	}	
	//sobe za def popusta za admina ssitema
			@RequestMapping(value="/getCarsForDiscount/{id}", method = RequestMethod.GET)
			public ArrayList<VehicleDTO> getAllCarsForDiscount(@PathVariable Long id){	
				System.out.println("dosao po vozila"+id.toString());
				
				ArrayList<Filijala> filijale = new ArrayList<Filijala>();
				List<Filijala> svefilijale = servis.findAll();
				
				for(Filijala fil:svefilijale) {
					if(fil.getServis().getId().toString().equals(id.toString())) {
						System.out.println("dodao filijalu");
						filijale.add(fil);
					}
				}
				
				ArrayList<VehicleDTO> vozila = new ArrayList<VehicleDTO>();
			//	Long id, String marka, String model, int godiste, int sedista, CategoryCar kategorija,
				//boolean imapopusta
				for(Filijala ff:filijale) {
					for(Vehicle vv:ff.getVozila()) {
						System.out.println("Dodaje vozilo");
						VehicleDTO voz = new VehicleDTO(vv.getId(),vv.getMarka(),vv.getModel(),vv.getGodiste(),vv.getSedista(),vv.getKategorija(),vv.isImapopusta());
						vozila.add(voz);
					}
				}
				System.out.println("broj vozila je "+vozila.size());
				return vozila;
			}
		
}
