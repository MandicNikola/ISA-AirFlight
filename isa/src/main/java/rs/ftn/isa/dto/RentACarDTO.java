package rs.ftn.isa.dto;

public class RentACarDTO {
	private String naziv;
	private String grad;
	private String adresa;
	private String opis;
	public RentACarDTO() {
		
	}
	public RentACarDTO(String naziv, String grad, String adresa, String opis) {
		super();
		this.naziv = naziv;
		this.grad=grad;
		this.adresa = adresa;
		this.opis = opis;
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
