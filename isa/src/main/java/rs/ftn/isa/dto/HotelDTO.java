package rs.ftn.isa.dto;

public class HotelDTO {
	private String naziv;
	private String adresa;
	private String opis;
	
	public HotelDTO() {
		super();
		
	}
	public HotelDTO(String naziv, String adresa, String opis) {
		super();
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
	}
	@Override
	public String toString() {
		return "HotelDTO [naziv=" + naziv + ", adresa=" + adresa + ", opis=" + opis + "]";
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	
	
}