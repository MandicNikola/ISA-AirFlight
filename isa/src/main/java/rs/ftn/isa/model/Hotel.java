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
import javax.persistence.OneToMany;


@Entity
public class Hotel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "naziv", nullable = false)
	private String naziv;
	
	@Column(name = "adresa", nullable = false)	
	private String adresa;
	
	@Column(name = "opis", nullable = false)	
	private String opis;
	
	@Column(name = "ocena", nullable = false)	
	private double ocena;
	
	//nedostaje kofiguracija soba i cenovnik usluga
	//jedan hotel ima vise soba
		@OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
		private Set<Room> sobe = new HashSet<Room>();
	
	public Hotel() {
		super();
	}
	public Hotel(Long id, String naziv, String adresa, String opis,double ocena) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.ocena = ocena;
	}
	
	public Hotel(String naziv, String adresa, String opis,double ocena) {
		super();
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
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
	public double getOcena() {
		return ocena;
	}
	public void setOcena(double ocena) {
		this.ocena = ocena;
	}
	@Override
	public String toString() {
		return "Hotel [id=" + id + ", naziv=" + naziv + ", adresa=" + adresa + ", opis=" + opis + ", ocena=" + ocena
				+ "]";
	}
	
	public Set<Room> getSobe() {
		return sobe;
	}
	public void setSobe(Set<Room> sobe) {
		this.sobe = sobe;
	}
	
	
	
}
