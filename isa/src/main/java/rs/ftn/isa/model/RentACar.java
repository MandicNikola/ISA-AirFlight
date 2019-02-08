package rs.ftn.isa.model;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class RentACar {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="naziv", nullable = false)
	private String naziv;
	
	@Column(name="grad",nullable = false)
	private String grad;
	
	@Column(name="adresa",nullable = false)
	private String adresa;
	
	@Column(name="opis",nullable = false)
	private String opis;

	@Column(name="ocena",nullable = true)
	private double ocena;

	@Column(name="brojac",nullable = true)
	private Integer brojac;
	
	
	@OneToMany(mappedBy = "servis", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Filijala> filijale = new HashSet<Filijala>();

	@OneToMany(mappedBy = "rentcar",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
     private Set<PricelistRentCar> cenovnici= new HashSet<PricelistRentCar>();

	
	
	public RentACar() {
		ocena=0;
		brojac=0;
	}
	
	
	public RentACar(Long id, String naziv, String adresa, String opis) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		ocena=0;
		brojac=0;
	}
	
	
	
	public RentACar(Long id, String naziv, String grad, String adresa, String opis) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.grad = grad;
		this.adresa = adresa;
		this.opis = opis;
		this.ocena = 0;
		this.brojac = 0;
	}
	
	public RentACar(String naziv, String grad, String adresa, String opis) {
		super();
		this.naziv = naziv;
		this.grad = grad;
		this.adresa = adresa;
		this.opis = opis;
		this.brojac=0;
		this.ocena=0;
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

	public Integer getBrojac() {
		return brojac;
	}
	public void setBrojac(Integer brojac) {
		this.brojac = brojac;
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
	public Set<PricelistRentCar> getCenovnici() {
		return cenovnici;
	}
	public void setCenovnici(Set<PricelistRentCar> cenovnici) {
		this.cenovnici = cenovnici;
	}
	
	

	public double getOcena() {
		return ocena;
	}
	public void setOcena(double ocena) {
		this.ocena = ocena;
	}


	public String getGrad() {
		return grad;
	}
	public void setGrad(String grad) {
		this.grad = grad;
	}



	public static Comparator<RentACar> RentNameComparator = new Comparator<RentACar>() {

			public int compare(RentACar u1, RentACar u2) {
			   String name1 = u1.getNaziv().toUpperCase();
			   String name2 = u2.getNaziv().toUpperCase();

			   //sortiranje od A-Z
			   return name1.compareTo(name2);

	}};
	
	 public static Comparator<RentACar> RentCityComparator = new Comparator<RentACar>() {

			public int compare(RentACar u1, RentACar u2) {
			   String city1 = u1.getGrad().toUpperCase();
			   String city2 = u2.getGrad().toUpperCase();

			   //sortiranje od A-Z
			   return city1.compareTo(city2);

	}};


}
