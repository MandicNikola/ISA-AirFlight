package rs.ftn.isa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Destination {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "naziv", nullable = false)
	private String naziv;

	@ManyToMany(fetch = FetchType.LAZY,mappedBy="destinacije")
	Set<AirplaneCompany> avioKomp = new HashSet<AirplaneCompany>();
	
	@ManyToMany(mappedBy="presedanja", fetch = FetchType.LAZY)
	Set<Flight> letovi = new HashSet<Flight>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "poletanje")
    private Set<Flight> poletanja = new HashSet<Flight>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sletanje")
    private Set<Flight> sletanja = new HashSet<Flight>();

	//trebam ubaciti odnose koji mi fale jos 
	
	public Destination()
	{
		super();
	}
	
	public Destination(Long id, String naziv) {
		super();
		this.id = id;
		this.naziv = naziv;
	}
	
	
	

	public Set<AirplaneCompany> getAvioKomp() {
		return avioKomp;
	}

	public void setAvioKomp(Set<AirplaneCompany> avioKomp) {
		this.avioKomp = avioKomp;
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


	public Set<Flight> getLetovi() {
		return letovi;
	}

	public void setLetovi(Set<Flight> letovi) {
		this.letovi = letovi;
	}

	public Set<Flight> getPoletanja() {
		return poletanja;
	}

	public void setPoletanja(Set<Flight> poletanja) {
		this.poletanja = poletanja;
	}

	public Set<Flight> getSletanja() {
		return sletanja;
	}

	public void setSletanja(Set<Flight> sletanja) {
		this.sletanja = sletanja;
	}
	
	
}
