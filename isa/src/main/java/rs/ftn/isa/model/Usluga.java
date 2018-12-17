package rs.ftn.isa.model;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
@Table(name = "usluge")
public class Usluga {

//klasa za usluge
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="naziv", nullable = false)
	private String naziv;
	
	@Column(name="cena", nullable = false)
	private int cena;
	
	@Enumerated(EnumType.STRING)
	@Column(name="kategorija", nullable = true)
	private CategoryCar kategorija;
	
	@Column(name="konfiguracija", nullable= true)
	private String konfiguracija;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "lista_id")
	PricelistRentCar lista;
	
	//cijenovnik hotela
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	PricelistHotel cijene;
	
	//cijenovnik sobe
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	CijenovnikSoba cenesoba;


	public Usluga() {}

	
	public Usluga(String naziv, int cena) {
		super();
		this.naziv = naziv;
		this.cena= cena;
	}


	public Usluga(Long id, String naziv, int cena) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.cena= cena;
	}


	public Usluga(String naziv, int cena, String kategorija) {
		super();
		this.naziv = naziv;
		this.cena = cena;
		this.kategorija = CategoryCar.valueOf(kategorija);
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


	public int getCena() {
		return cena;
	}


	public void setCena(int cena) {
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
	
	
	
}
