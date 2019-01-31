package rs.ftn.isa.dto;

public class DestinationDTO {

	private Long id;
	private String naziv;
	
	public DestinationDTO()
	{
		
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}
