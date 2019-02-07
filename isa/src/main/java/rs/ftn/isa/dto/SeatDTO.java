package rs.ftn.isa.dto;

import java.util.Comparator;

import rs.ftn.isa.model.Seat;



public class SeatDTO implements Comparable<SeatDTO>{

	private Long idSedista;
	private Long idKarte;
	
	private boolean rezervisano;
	private int brojReda;
	private int brojKolone;
	
	private String konfiguracija;
	
	
	private String status;
	
	public SeatDTO()
	{
		
	}


	public SeatDTO(Seat s)
	{
		idSedista = s.getId();
		brojReda = s.getRed();
		brojKolone = s.getKolona();
		
	}
	
	public Long getIdSedista() {
		return idSedista;
	}


	public void setIdSedista(Long idSedista) {
		this.idSedista = idSedista;
	}


	public Long getIdKarte() {
		return idKarte;
	}


	public void setIdKarte(Long idKarte) {
		this.idKarte = idKarte;
	}


	public boolean isRezervisano() {
		return rezervisano;
	}


	public void setRezervisano(boolean rezervisano) {
		this.rezervisano = rezervisano;
	}


	public int getBrojReda() {
		return brojReda;
	}


	public void setBrojReda(int brojReda) {
		this.brojReda = brojReda;
	}


	public int getBrojKolone() {
		return brojKolone;
	}


	public void setBrojKolone(int brojKolone) {
		this.brojKolone = brojKolone;
	}


	public String getKonfiguracija() {
		return konfiguracija;
	}


	public void setKonfiguracija(String konfiguracija) {
		this.konfiguracija = konfiguracija;
	}
	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public int compareTo(SeatDTO o) {
		// TODO Auto-generated method stub
		return Comparator.comparing(SeatDTO::getBrojReda).thenComparing(SeatDTO::getBrojKolone).compare(this, o);
	}
	
	
	
	
}
