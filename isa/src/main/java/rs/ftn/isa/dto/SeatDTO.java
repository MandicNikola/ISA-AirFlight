package rs.ftn.isa.dto;

public class SeatDTO {

	private Long idSedista;
	private Long idKarte;
	
	private boolean rezervisano;
	private int brojReda;
	private int brojKolone;
	
	private String konfiguracija;
	
	
	public SeatDTO()
	{
		
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
	
	
	
	
}
