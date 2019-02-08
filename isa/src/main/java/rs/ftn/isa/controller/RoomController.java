package rs.ftn.isa.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

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
import rs.ftn.isa.dto.RoomDTO;
import rs.ftn.isa.model.Discount;
import rs.ftn.isa.model.Hotel;
import rs.ftn.isa.model.PricelistHotel;
import rs.ftn.isa.model.RezervacijaHotel;
import rs.ftn.isa.model.Room;
import rs.ftn.isa.model.StatusRezervacije;
import rs.ftn.isa.model.User;
import rs.ftn.isa.model.Usluga;
import rs.ftn.isa.service.RoomServiceImp;

@RestController
@RequestMapping(value="api/rooms")
public class RoomController {
	@Autowired
	private RoomServiceImp servis;

	@RequestMapping(value="/all", method = RequestMethod.GET)
	public List<Room> getAllRooms(){
		System.out.println("dosao po sobe");
		return  servis.findAll();
	}
	//validacija dodavanja nove sobe
	@RequestMapping(value="/newroom",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Room newRoom(@RequestBody Room room) {
			Room pomRoom = new Room();

			
		if(room.getTip().equals("") || room.getTip() == null || room.getTip().equals("undefined")) {
			pomRoom.setTip("Tip");
			return pomRoom;
		}
		if(room.getKapacitet()<1) {
			pomRoom.setTip("Kreveti");
			return pomRoom;
		}
		if(room.getSprat()<0) {
			pomRoom.setTip("Sprat");
			return pomRoom;
			
			
		}
		
		return room;
	}
	
	@RequestMapping(value="/obrisiSobu/{soba}", method = RequestMethod.POST)
	public  void obrisiSobu(@PathVariable String soba){
		Long idSoba = Long.parseLong(soba);
		servis.removeRoom(idSoba);
		
		
	}
	
	@RequestMapping(value="/vratiSobu/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody Room getRoomById(@PathVariable Long id){	
				Room soba = servis.findRoomById(id);
				return soba;

	}
	
	
	@RequestMapping(value="/izmjeniSobu/{id}", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Room> izmjeniSobu(@RequestBody Room soba,@PathVariable Long id){		
		Room stara = servis.findRoomById(id);
		stara.setBalkon(soba.getBalkon());
		stara.setKapacitet(soba.getKapacitet());
		stara.setSprat(soba.getSprat());
		stara.setTip(soba.getTip());
		//automatski radi update po id sobe
		try {
			servis.saveRoom(stara);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return  new ResponseEntity<>(HttpStatus.CONFLICT);	
		}
		 return new ResponseEntity<>(stara,HttpStatus.OK);
			
	}


	@RequestMapping(value="/ukloniPopust/{slanje}", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Room> ukloniPopust(@PathVariable("slanje") String slanje){
		System.out.println("Dosao da ukloni popust");
		String[] pom = slanje.split("\\.");
		String sobaId = pom[1];
		Long id =Long.parseLong(sobaId);
		Room room  = servis.findRoomById(id);
		System.out.println("Soba je "+room.getId());
		if(room.getPopusti() == null) {
			room.setImapopusta(false);
			
		}else {

			if(room.getPopusti().size()== 0) {
				room.setImapopusta(false);
			}
		}
		
		try {
			servis.saveRoom(room);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return  new ResponseEntity<>(HttpStatus.CONFLICT);	
			
		}
		 return new ResponseEntity<>(room,HttpStatus.OK);
			
	}

	
	@RequestMapping(value="/getFast/{id}/checkout/{checkout}/checkin/{checkin}", 
			method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE
			)
	public @ResponseBody ArrayList<RoomDTO> getFast(@PathVariable Long id,@PathVariable String checkout,@PathVariable String checkin,@Context HttpServletRequest request) throws ParseException{		
		User korisnik = (User)request.getSession().getAttribute("ulogovan");		
		int brojBodova = 0;
		System.out.println(" id hotela "+id+" checkout "+checkout+" checkin"+checkin);
		if(korisnik != null) {
			 brojBodova  = korisnik.getBodovi();
		}else {
			System.out.println("korisnik null");
			return new ArrayList<RoomDTO>();
			
		}
		if(brojBodova ==0) {
			return new ArrayList<RoomDTO>();
		}
		System.out.println("dobio je hotel "+id+"a datum odlaska mu je "+checkout);
		ArrayList<RoomDTO> pronadjeneSobe = new ArrayList<RoomDTO>();
		System.out.println("USAO JE  U FUNKCIJU ****************************");
		List<Room> sobe = servis.findRoomsByHotel(id);
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		Date datumCheckIn = formater.parse(checkin);
		System.out.println("Daatum dolaska korisnika je "+datumCheckIn);
		Date datumCheckOut = formater.parse(checkout);
		System.out.println("Daatum odlaska korisnika je "+datumCheckOut);
		
		for(Room r:sobe) {
			//koji popust je definisan za njega
			int izabraniPopust = -1;
			int index = 0;
			ArrayList<Discount> popusti = new ArrayList<Discount>() ;
			for(Discount p:r.getPopusti()) {
				System.out.println("postoji popust");
				popusti.add(p);
			}
			if(popusti.isEmpty()) {
				return new ArrayList<RoomDTO>();
			}
			popusti.sort(Comparator.comparingInt(Discount :: getBodovi));
			for(Discount popust:popusti) {
				if(brojBodova >= popust.getBodovi()) {
					Date pocetakPopusta = popust.getDatumod();
					System.out.println(" datum pocetka popusta "+pocetakPopusta);
					Date krajPopusta = popust.getDatumdo();
					
					System.out.println(" datum kraja popusta "+krajPopusta);
					boolean moze = false;
					pocetakPopusta.setHours(0);
					pocetakPopusta.setMinutes(0);
					pocetakPopusta.setSeconds(0);
					
					krajPopusta.setHours(0);
					pocetakPopusta.setMinutes(0);
					pocetakPopusta.setSeconds(0);
					
					if(datumCheckIn.compareTo(pocetakPopusta)>=0 && datumCheckOut.compareTo(krajPopusta)<=0) {
						moze = true;
						izabraniPopust =index;
						System.out.println("Odgovara datum u popustu");	
					}
				
				}
				index++;
			}
			//nema definisan popust za tako mali broj bodova
			if(izabraniPopust == -1) {

				System.out.println("nema izabran popust za mali broj bod");
				return new ArrayList<RoomDTO>();
			}
			Discount odgovarajuciPopust = popusti.get(izabraniPopust);
			
			
			
			Room room = r;
			Set<RezervacijaHotel> rezervacije = room.getRezervacije(); 
			if(rezervacije.size() == 0) {
				RoomDTO sobaDTO = new RoomDTO(room.getId(),room.getTip(),room.getOcjena(),room.getSprat(),room.getKapacitet(),room.getCijena(),room.getBalkon());
				sobaDTO.setBodoviPopusta(odgovarajuciPopust.getBodovi());
				
				sobaDTO.setVrijednostPopusta(odgovarajuciPopust.getVrijednost());
				ArrayList<String> dodatne = vratiNazive(odgovarajuciPopust,r.getHotel());
				
				if(dodatne.size()!=0) {
					sobaDTO.setImaNazive(true);
					sobaDTO.setNazivUsluga(dodatne);
					
					System.out.println("ima nazive");
				}
				
				pronadjeneSobe.add(sobaDTO);
				
				break;
			}
			boolean nijeOdobrena= false;
			
			for(RezervacijaHotel pom:rezervacije) {	
				if(pom.getStatus()==StatusRezervacije.AKTIVNA) {
					
					if(datumCheckIn.compareTo(pom.getDatumOdlaska())<0) {
						
						if(datumCheckOut.compareTo(pom.getDatumDolaska())>0) {
							System.out.println("nije odobren check out i check out");
							nijeOdobrena= true;
							break;
						}
					}
				}
			}
			//odobren check in,provjeravam check out ..da li je check out < od pocetaka svih rezervacija koje postoje za datu sobu
		
			
			//odobrena je soba
			if(!nijeOdobrena) {
				System.out.println("odobrena soba");
				RoomDTO sobaDTO = new RoomDTO(room.getId(),room.getTip(),room.getOcjena(),room.getSprat(),room.getKapacitet(),room.getCijena(),room.getBalkon());
				sobaDTO.setBodoviPopusta(odgovarajuciPopust.getBodovi());
				sobaDTO.setVrijednostPopusta(odgovarajuciPopust.getVrijednost());
				ArrayList<String> dodatne = vratiNazive(odgovarajuciPopust,r.getHotel());
				if(dodatne.size()!=0) {
					sobaDTO.setImaNazive(true);
					sobaDTO.setNazivUsluga(dodatne);
					
					System.out.println("ima nazive");
				}
				
				pronadjeneSobe.add(sobaDTO);
			}
			
		}

		if(pronadjeneSobe.size() == 0) {
			System.out.println("Nije pronasao nijednu sobu");
			return new ArrayList<RoomDTO>();
		}
		for(RoomDTO romdto:pronadjeneSobe) {
			System.out.println(" id "+romdto.getId()+" popust "+romdto.getVrijednostPopusta());
		}
		return pronadjeneSobe;
	}	

	public ArrayList<String> vratiNazive(Discount popust,Hotel hotel){
		ArrayList<String> naziviPopusta = new ArrayList<String>();
		
		PricelistHotel aktivni = null;
		for(PricelistHotel ph:hotel.getCijenovnici()) {
			if(ph.isAktivan()) {
				aktivni = ph;
				break;
			}
			
		}
		ArrayList<Usluga> postojeceusluge = new ArrayList<Usluga>();
		if(aktivni == null) {
			return naziviPopusta;
		}
		if(aktivni.getUsluge()!= null) {
			if(aktivni.getUsluge().size() != 0) {
				for(Usluga u:aktivni.getUsluge()) {
					postojeceusluge.add(u);
				}
			}
		}
		if(postojeceusluge.size() != 0) {
			if(popust.getVrijednost()<5) {
				naziviPopusta.add(postojeceusluge.get(0).getNaziv());
			
			}
			if(popust.getVrijednost()>=5 && popust.getVrijednost() <10) {
				naziviPopusta.add(postojeceusluge.get(0).getNaziv());
				if(postojeceusluge.size()>=2) {
					naziviPopusta.add(postojeceusluge.get(1).getNaziv());
						
				}
			}
			
			if(popust.getVrijednost()>=10 && popust.getVrijednost() <15) {
				naziviPopusta.add(postojeceusluge.get(0).getNaziv());
				if(postojeceusluge.size()>=3) {
					naziviPopusta.add(postojeceusluge.get(2).getNaziv());		
				}
			}
			if(popust.getVrijednost()>=20 && popust.getVrijednost() <25) {
				naziviPopusta.add(postojeceusluge.get(0).getNaziv());
				if(postojeceusluge.size()>=2) {
					naziviPopusta.add(postojeceusluge.get(1).getNaziv());
						
				}
				if(postojeceusluge.size()>=3) {
					naziviPopusta.add(postojeceusluge.get(2).getNaziv());
						
				}
			}
			if(popust.getVrijednost()>=25 && popust.getVrijednost() <30) {
				naziviPopusta.add(postojeceusluge.get(0).getNaziv());
			}
		}					
		
		return naziviPopusta;
	}	
	//metoda koja formira rezervaciju
	//url : "/api/rooms/rezervisiFast/"+info+"/sobapopust/"+param,
	
			@RequestMapping(value="/rezervisiFast/{info}/sobapopust/{sobapopust}/idhotel/{idhotel}/idRez/{idRez}", 
					method = RequestMethod.POST,
					produces = MediaType.APPLICATION_JSON_VALUE
					)
			public ResponseEntity<RezervacijaHotel> brzaRez(@PathVariable("info") String info,
		            @PathVariable("sobapopust") String sobapopust,@PathVariable("idhotel") Long idhotel,@PathVariable("idRez") Long idRez,@Context HttpServletRequest request){
				RezervacijaHotel povratna = new RezervacijaHotel();
				User korisnik = (User)request.getSession().getAttribute("ulogovan");	
				if(korisnik == null) {
					return null;
				}
				List<Room> sobe = servis.findRoomsByHotel(idhotel);
				
				String[] infoPom = info.split("\\*");
				String checkIN = infoPom[0];
				String checkOUT = infoPom[1];
				
				String[] pom = sobapopust.split("\\.");
				String sobaID = pom[0];
				String popustVrijednost = pom[2];
				int popustVr = Integer.parseInt(popustVrijednost);
				Room izabranaSoba = null;
				String[] datIN=checkIN.split("-");
				
				int godina=Integer.parseInt(datIN[0]);
				int mjesec=Integer.parseInt(datIN[1])-1;
				int dan=Integer.parseInt(datIN[2]);	
				Calendar calendar = Calendar.getInstance();
				calendar.set(godina, mjesec, dan);
				Date datumCheckIn = calendar.getTime();
				datumCheckIn.setHours(0);
				datumCheckIn.setMinutes(0);
				datumCheckIn.setSeconds(0);
				
				String[] datOUT=checkOUT.split("-");
				
				 godina=Integer.parseInt(datOUT[0]);
				//mjesec krece od 0
				 mjesec=Integer.parseInt(datOUT[1])-1;
				 dan=Integer.parseInt(datOUT[2]);
				 calendar.set(godina, mjesec, dan);
				 Date datumCheckOut = calendar.getTime();
				
				povratna.setDatumDolaska(datumCheckIn);
				povratna.setDatumOdlaska(datumCheckOut);
				
				
				
				boolean nijeOkRez = false;
				
				for(Room soba:sobe) {
					if(soba.getId().toString().equals(sobaID)) {
						izabranaSoba = soba;
						
						for(RezervacijaHotel rez:izabranaSoba.getRezervacije()) {	
							if(rez.getStatus()==StatusRezervacije.AKTIVNA) {
								
							if(datumCheckIn.compareTo(rez.getDatumOdlaska())<0) {
								System.out.println("nije odobren check in");
								if(datumCheckOut.compareTo(rez.getDatumDolaska())>0) {
									System.out.println("nije odobren check out");
									//odobrenCheckOUT = false;
									nijeOkRez = true;
									break;
								}
							}
							}
						}
						break;
					}
				}
				
				if(nijeOkRez) {
					 return null;
				}

				
				for(Room soba:sobe) {
					if(soba.getId().toString().equals(sobaID)) {
						izabranaSoba = soba;
						break;
					}
				}
				
				int dani = daysBetween(datumCheckIn, datumCheckOut);
				double cijena = dani*izabranaSoba.getCijena();
				//uracunajpopust
				povratna.setCijena((double)cijena*((double)(100-popustVr)/100));
				
				povratna.setUserHotel(korisnik);
				korisnik.getRezHotela().add(povratna);
				povratna.setStatus(StatusRezervacije.AKTIVNA);
				povratna.setRezavion(idRez);
				Set<RezervacijaHotel> rezSobe = izabranaSoba.getRezervacije();
				rezSobe.add(povratna);				
				izabranaSoba.setRezervacije(rezSobe);
				izabranaSoba.setRezervisana(true);
				int broj = izabranaSoba.getBrojRezervacija();
				izabranaSoba.setBrojRezervacija(broj+1);
				try {
					servis.saveRoom(izabranaSoba);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					return  new ResponseEntity<>(HttpStatus.CONFLICT);	
					
				}
			
				 return new ResponseEntity<>(povratna,HttpStatus.OK);
					
			
			}
			
			 public int daysBetween(Date d1, Date d2){
		         return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
			 }
}
