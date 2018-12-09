package rs.ftn.isa.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity
public class ServiceHotel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "naziv", nullable = false)
	private String naziv;
	@Column(name = "cena", nullable = false)
	private int cena;
	//
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
			PricelistHotel cijene;
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
