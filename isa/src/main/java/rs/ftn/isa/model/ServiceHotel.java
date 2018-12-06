package rs.ftn.isa.model;

public class ServiceHotel {
	private Long id;
	private String naziv;
	private int cena;
	public ServiceHotel() {
		
	}
	public ServiceHotel( String naziv, int cena) {
		super();
		this.naziv = naziv;
		this.cena = cena;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public int getCena() {
		return cena;
	}
	public void setCena(int cena) {
		this.cena = cena;
	}

	
	
	
}
