package rs.ftn.isa.dto;

import rs.ftn.isa.model.Hotel;

public class HotelDTO {
	private String naziv;
	private String adresa;
	private String opis;
	private String grad;
	private Long id;
	
	public HotelDTO() {
		super();
		
	}
	public HotelDTO(String naziv, String adresa, String opis,String grad) {
		super();
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.grad = grad;
	}
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public HotelDTO(Hotel hotel) {
		this(hotel.getNaziv(), hotel.getAdresa(),hotel.getOpis(),hotel.getGrad());
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
	public String getGrad() {
		return grad;
	}
	public void setGrad(String grad) {
		this.grad = grad;
	}
	
	
}
