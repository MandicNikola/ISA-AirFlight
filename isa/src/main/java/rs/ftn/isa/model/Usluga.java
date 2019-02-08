package rs.ftn.isa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "usluge")
public class Usluga {

//klasa za usluge
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="naziv")
	private String naziv;
	
	@Column(name="cena", nullable = false)
	private double cena;
	
	//u zavisnosti od toga na koliko se dana uzima auto cena varira
	//ako je trajanje 5 dana , sve >=5 dana ima tu cenu
	@Column(name="od", nullable = true)
	private int prekoTrajanja;
	
	
	@Enumerated(EnumType.STRING)
	@Column(name="kategorija", nullable = true)
	private CategoryCar kategorija;
	
	@Column(name="konfiguracija", nullable= true)
	private String konfiguracija;
	
	@Column(name="popust", nullable= true)
	private int popust;
	@Version 
	private Long Version;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "lista_id")
	PricelistRentCar lista;
	
	//cijenovnik hotela
	@ManyToOne( fetch = FetchType.EAGER)
	PricelistHotel cijene;
	
	//dodatne usluge mogu da budu rezervisane vise puta u hotelu
	@ManyToMany(cascade = { CascadeType.ALL })
	 @JoinTable(
	        name = "Rezervacija_Usluga", 
	        joinColumns = { @JoinColumn(name = "usluga_id") }, 
	        inverseJoinColumns = { @JoinColumn(name = "rezervacijaHotel_id") }
	  )
	private Set<RezervacijaHotel> rezHotela = new HashSet<RezervacijaHotel>();
	
	//cijenovnik sobe
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	CijenovnikSoba cenesoba;

	

	public Usluga() {}

	
	public Usluga(String naziv, double cena) {
		super();
		this.naziv = naziv;
		this.cena= cena;
	}


	public Usluga(Long id, String naziv, double cena) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.cena= cena;
	}


	public Usluga(double cena, int prekoTrajanja, String kategorija) {
		super();
		this.cena = cena;
		this.prekoTrajanja = prekoTrajanja;
		this.kategorija = CategoryCar.valueOf(kategorija);
	}


	public Usluga(String naziv, double cena, String kategorija) {
		super();
		this.naziv = naziv;
		this.cena = cena;
		this.kategorija = CategoryCar.valueOf(kategorija);
	}

	


	public Usluga(String naziv, double cena, String konfiguracija, int popust) {
		super();
		this.naziv = naziv;
		this.cena = cena;
		this.konfiguracija = konfiguracija;
		this.popust = popust;
	}


	public double getPopust() {
		return popust;
	}


	public void setPopust(int popust) {
		this.popust = popust;
	}


	public CategoryCar getKategorija() {
		return kategorija;
	}


	public void setKategorija(CategoryCar kategorija) {
		this.kategorija = kategorija;
	}


	public String getKonfiguracija() {
		return konfiguracija;
	}


	public void setKonfiguracija(String konfiguracija) {
		this.konfiguracija = konfiguracija;
	}


	public PricelistRentCar getLista() {
		return lista;
	}


	public void setLista(PricelistRentCar lista) {
		this.lista = lista;
	}


	public PricelistHotel getCijene() {
		return cijene;
	}


	public void setCijene(PricelistHotel cijene) {
		this.cijene = cijene;
	}


	public double getCena() {
		return cena;
	}


	public void setCena(double cena) {
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


	@Override
	public String toString() {
		return "Service [id=" + id + ", naziv=" + naziv + "]";
	}


	public CijenovnikSoba getCenesoba() {
		return cenesoba;
	}


	public void setCenesoba(CijenovnikSoba cenesoba) {
		this.cenesoba = cenesoba;
	}




	public int getPrekoTrajanja() {
		return prekoTrajanja;
	}


	public void setPrekoTrajanja(int prekoTrajanja) {
		this.prekoTrajanja = prekoTrajanja;
	}


	public Set<RezervacijaHotel> getRezHotela() {
		return rezHotela;
	}


	public void setRezHotela(Set<RezervacijaHotel> rezHotela) {
		this.rezHotela = rezHotela;
	}


	public Long getVersion() {
		return Version;
	}


	public void setVersion(Long version) {
		Version = version;
	}
	
	
	
}
