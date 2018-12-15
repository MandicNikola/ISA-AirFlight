package rs.ftn.isa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.codehaus.jackson.annotate.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
public class RentACar {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="naziv", nullable = false)
	private String naziv;
	
	@Column(name="adresa",nullable = false)
	private String adresa;
	
	@Column(name="opis",nullable = false)
	private String opis;

	@OneToMany(mappedBy = "servis", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Filijala> filijale = new HashSet<Filijala>();

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "pricelistrentcar_id")    
	private PricelistRentCar cenovnik ;

	@OneToMany(mappedBy = "servisrent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
	private Set<Vehicle> vozila = new HashSet<Vehicle>();

	
	public RentACar() {
		
		
	}
	public RentACar(String naziv, String adresa, String opis) {
		super();
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
	}
	
	public RentACar(Long id, String naziv, String adresa, String opis) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
	}
	
	public Set<Vehicle> getVozila() {
		return vozila;
	}
	public void setVozila(Set<Vehicle> vozila) {
		this.vozila = vozila;
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
	public Set<Filijala> getFilijale() {
		return filijale;
	}
	public void setFilijale(Set<Filijala> filijale) {
		this.filijale = filijale;
	}
	public PricelistRentCar getCenovnik() {
		return cenovnik;
	}
	public void setCenovnik(PricelistRentCar cenovnik) {
		this.cenovnik = cenovnik;
	}
	
}
