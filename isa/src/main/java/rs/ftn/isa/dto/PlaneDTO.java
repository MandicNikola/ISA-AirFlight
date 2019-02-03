package rs.ftn.isa.dto;

public class PlaneDTO {

	private int ekonomska;
	private int biznis;
	private int prvaKlasa;
	
	private String konfiguracija;
	private String naziv;
	
	public PlaneDTO()
	{
		
	}

	public int getEkonomska() {
		return ekonomska;
	}

	public void setEkonomska(int ekonomska) {
		this.ekonomska = ekonomska;
	}

	public int getBiznis() {
		return biznis;
	}

	public void setBiznis(int biznis) {
		this.biznis = biznis;
	}

	public int getPrvaKlasa() {
		return prvaKlasa;
	}

	public void setPrvaKlasa(int prvaKlasa) {
		this.prvaKlasa = prvaKlasa;
	}

	public String getKonfiguracija() {
		return konfiguracija;
	}

	public void setKonfiguracija(String konfiguracija) {
		this.konfiguracija = konfiguracija;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	
	
}
