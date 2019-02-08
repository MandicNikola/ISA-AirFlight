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
import javax.persistence.Version;


import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "vozila")
public class Vehicle {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
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
	
	@Column(name = "cena", nullable = true)
	private double cena;

	//brojac ocena
	@Column(name = "brojac", nullable = true)
	private int brojac;
	
	@Column(name = "ocena", nullable = true)
	private double ocena;

	//broj rezervacija
	@Column(name = "broj", nullable = true)
	private int broj;

	//bice true ukoliko ima definisan ijedan popust
	@Column(name = "imapopusta", nullable = true)	
    private boolean imapopusta; 
	
	//jedna vozilo moze da ima vise popusta 
	@OneToMany(mappedBy = "vozilopopust", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Discount> popusti = new HashSet<Discount>();
		
	//svako vozilo pripada odredjenoj filijali
	@ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "filijala_id")
	private Filijala filijala;
	
	@OneToMany(mappedBy = "vozilo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
	private Set<RezervacijaRentCar> rezervacije = new HashSet<RezervacijaRentCar>();

	@Version
	Long Version;
	
	public Vehicle() {
		broj=0;
		ocena=0;
		brojac=0;
	}

	
	public Vehicle( String marka, String model, int godiste, int sedista, String kategorija, double cena,
			double ocena) {
		super();
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


	public Vehicle( String marka, String model, int godiste, int sedista, String kategorija) {
		super();
		this.marka = marka;
		this.model = model;
		this.godiste = godiste;
		this.sedista = sedista;
		this.kategorija =CategoryCar.valueOf(kategorija);
		broj=0;
		ocena=0;
		brojac=0;
	}


	public Vehicle(Long id, String marka, String model, int godiste, int sedista, String kategorija, double cena,
			double ocena) {
		super();
		this.id = id;
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
		return "Vehicle [id=" + id + ", marka=" + marka + ", model=" + model + ", godiste=" + godiste + ", sedista="
				+ sedista + ", kategorija=" + kategorija + ", cena=" + cena + ", brojac=" + brojac + ", ocena=" + ocena
				+ ", broj=" + broj + ", filijala=" + filijala + ", rezervacije=" + rezervacije + "]";
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


	public boolean isImapopusta() {
		return imapopusta;
	}


	public void setImapopusta(boolean imapopusta) {
		this.imapopusta = imapopusta;
	}


	public Set<Discount> getPopusti() {
		return popusti;
	}


	public void setPopusti(Set<Discount> popusti) {
		this.popusti = popusti;
	}
	
	
	
}
