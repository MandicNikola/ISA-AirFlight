package rs.ftn.isa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Service {

//klasa za usluge
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "naziv", nullable = false)
	private String naziv;
	
	public Service() {}

	
	public Service(String naziv) {
		super();
		this.naziv = naziv;
	}


	public Service(Long id, String naziv) {
		super();
		this.id = id;
		this.naziv = naziv;
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


	@Override
	public String toString() {
		return "Service [id=" + id + ", naziv=" + naziv + "]";
	}
	
	
	
}
