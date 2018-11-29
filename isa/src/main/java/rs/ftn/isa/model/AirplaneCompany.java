package rs.ftn.isa.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AirplaneCompany {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "naziv", nullable = false)
	private String naziv;

	@Column(name = "adresa", nullable = false)
	private String adresa;
	
	@Column(name = "opis", nullable = false)
	private String opis;

	
	public AirplaneCompany() {
		super();
	}
	public AirplaneCompany(Long id,String naziv, String adresa, String opis) {
		super();
		this.id=id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
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
