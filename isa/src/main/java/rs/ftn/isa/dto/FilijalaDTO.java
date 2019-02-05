package rs.ftn.isa.dto;

public class FilijalaDTO {

	private String naziv;
	private String adresa;
	private String opis;
	private String grad;
	private Long idServisa;
	
	
	public FilijalaDTO()
	{
		
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


	public Long getIdServisa() {
		return idServisa;
	}


	public void setIdServisa(Long idServisa) {
		this.idServisa = idServisa;
	}
	
	
	
}
