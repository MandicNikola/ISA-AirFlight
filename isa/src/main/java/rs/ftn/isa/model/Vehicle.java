package rs.ftn.isa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
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
	
	@Column(name = "godina", nullable = false)
	private int godina;
	
	@Column(name = "sedista", nullable = false)
	private int sedista;
	
	@Column(name = "tip", nullable = false)
	private String tip;
	
	@Column(name = "cena", nullable = false)
	private double cena;
	
	@Column(name = "ocena", nullable = false)
	private double ocena;
	/*
	@ManyToMany(mappedBy = "pripadafilijali")
	private Set<Filijala> filijale = new HashSet<Filijala>();
*/
	
	public Vehicle() {}

	
	public Vehicle(String naziv, String marka, String model, int godina, int sedista, String tip, double cena,
			double ocena) {
		super();
		this.naziv = naziv;
		this.marka = marka;
		this.model = model;
		this.godina = godina;
		this.sedista = sedista;
		this.tip = tip;
		this.cena = cena;
		this.ocena = ocena;
	}


	public Vehicle(Long id, String naziv, String marka, String model, int godina, int sedista, String tip, double cena,
			double ocena) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.marka = marka;
		this.model = model;
		this.godina = godina;
		this.sedista = sedista;
		this.tip = tip;
		this.cena = cena;
		this.ocena = ocena;
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


	public int getGodina() {
		return godina;
	}


	public void setGodina(int godina) {
		this.godina = godina;
	}


	public int getSedista() {
		return sedista;
	}


	public void setSedista(int sedista) {
		this.sedista = sedista;
	}


	public String getTip() {
		return tip;
	}


	public void setTip(String tip) {
		this.tip = tip;
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
		return "Vehicle [id=" + id + ", naziv=" + naziv + ", marka=" + marka + ", model=" + model + ", godina=" + godina
				+ ", sedista=" + sedista + ", tip=" + tip + ", cena=" + cena + ", ocena=" + ocena + "]";
	}
	
	
	
}
