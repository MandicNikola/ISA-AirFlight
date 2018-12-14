package rs.ftn.isa.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	
	@Column(name = "kategorija", nullable = false)
	private String kategorija;
	
	@Column(name = "cena", nullable = false)
	private double cena;
	
	@Column(name = "ocena", nullable = false)
	private double ocena;
	

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "rent_id")
	private RentACar servisrent;
	

	
	public Vehicle() {}

	
	public Vehicle(String naziv, String marka, String model, int godiste, int sedista, String kategorija, double cena,
			double ocena) {
		super();
		this.naziv = naziv;
		this.marka = marka;
		this.model = model;
		this.godiste = godiste;
		this.sedista = sedista;
		this.kategorija = kategorija;
		this.cena = cena;
		this.ocena = ocena;
	}


	public Vehicle(String naziv, String marka, String model, int godiste, int sedista, String kategorija) {
		super();
		this.naziv = naziv;
		this.marka = marka;
		this.model = model;
		this.godiste = godiste;
		this.sedista = sedista;
		this.kategorija = kategorija;
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
		this.kategorija = kategorija;
		this.cena = cena;
		this.ocena = ocena;
	}


	public RentACar getServisrent() {
		return servisrent;
	}


	public void setServisrent(RentACar servisrent) {
		this.servisrent = servisrent;
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


	public String getKategorija() {
		return kategorija;
	}


	public void setKategorija(String kategorija) {
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
	
	
	
}
