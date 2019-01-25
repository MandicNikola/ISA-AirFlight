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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonManagedReference;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "vozila")
public class Vehicle {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "naziv", nullable = false)
	private String naziv;
	
	@Column(name = "marka", nullable = false)
	private String marka;
	
	@Column(name = "model", nullable = false)
	private String model;
	
	@Column(name = "godiste", nullable = false)
	private int godiste;
	
	@Column(name = "sedista", nullable = false)
	private int sedista;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "kategorija", nullable = false)
	private CategoryCar kategorija;
	
	@Column(name = "cena", nullable = false)
	private double cena;

	//brojac ocena
	@Column(name = "brojac", nullable = false)
	private int brojac;
	
	@Column(name = "ocena", nullable = false)
	private double ocena;

	//broj rezervacija
	@Column(name = "broj", nullable = false)
	private int broj;

	
	//svako vozilo pripada odredjenoj filijali
	@ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "filijala_id")
	private Filijala filijala;
	
	@OneToMany(mappedBy = "vozilo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
	private Set<RezervacijaRentCar> rezervacije = new HashSet<RezervacijaRentCar>();

	
	public Vehicle() {
		broj=0;
		ocena=0;
		brojac=0;
	}

	
	public Vehicle(String naziv, String marka, String model, int godiste, int sedista, String kategorija, double cena,
			double ocena) {
		super();
		this.naziv = naziv;
		this.marka = marka;
		this.model = model;
		this.godiste = godiste;
		this.sedista = sedista;
		this.kategorija = CategoryCar.valueOf(kategorija);
		this.cena = cena;
		this.ocena = ocena;
		broj=0;
		brojac=0;
	}


	public Vehicle(String naziv, String marka, String model, int godiste, int sedista, String kategorija) {
		super();
		this.naziv = naziv;
		this.marka = marka;
		this.model = model;
		this.godiste = godiste;
		this.sedista = sedista;
		this.kategorija =CategoryCar.valueOf(kategorija);
		broj=0;
		ocena=0;
		brojac=0;
	}


	public Vehicle(Long id, String naziv, String marka, String model, int godiste, int sedista, String kategorija, double cena,
			double ocena) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.marka = marka;
		this.model = model;
		this.godiste = godiste;
		this.sedista = sedista;
		this.kategorija = CategoryCar.valueOf(kategorija);
		this.cena = cena;
		this.ocena = ocena;
		broj=0;
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


	public Filijala getFilijala() {
		return filijala;
	}


	public void setFilijala(Filijala filijala) {
		this.filijala = filijala;
	}


	public Set<RezervacijaRentCar> getRezervacije() {
		return rezervacije;
	}


	public void setRezervacije(Set<RezervacijaRentCar> rezervacije) {
		this.rezervacije = rezervacije;
	}


	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}


	public String getMarka() {
		return marka;
	}


	public void setMarka(String marka) {
		this.marka = marka;
	}


	public String getModel() {
		return model;
	}


	public void setModel(String model) {
		this.model = model;
	}


	public int getGodiste() {
		return godiste;
	}


	public void setGodiste(int godiste) {
		this.godiste = godiste;
	}


	public int getSedista() {
		return sedista;
	}


	public void setSedista(int sedista) {
		this.sedista = sedista;
	}




	public CategoryCar getKategorija() {
		return kategorija;
	}


	public void setKategorija(CategoryCar kategorija) {
		this.kategorija = kategorija;
	}


	public double getCena() {
		return cena;
	}


	public void setCena(double cena) {
		this.cena = cena;
	}


	public double getOcena() {
		return ocena;
	}


	public void setOcena(double ocena) {
		this.ocena = ocena;
	}


	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", naziv=" + naziv + ", marka=" + marka + ", model=" + model + ", godiste=" + godiste
				+ ", sedista=" + sedista + ", kategorija=" + kategorija + ", cena=" + cena + ", ocena=" + ocena + "]";
	}


	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}


	public int getBroj() {
		return broj;
	}


	public void setBroj(int broj) {
		this.broj = broj;
	}


	public int getBrojac() {
		return brojac;
	}


	public void setBrojac(int brojac) {
		this.brojac = brojac;
	}
	
	
	
}
