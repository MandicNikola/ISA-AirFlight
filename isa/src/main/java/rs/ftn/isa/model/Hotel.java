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

public class Hotel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "naziv", nullable = false)
	private String naziv;
	
	@Column(name = "adresa", nullable = false)	
	private String adresa;
	

	@Column(name="grad", nullable = false)
	private String grad;
	
	@Column(name = "opis", nullable = false)	
	private String opis;
	
	@Column(name = "ocena", nullable = false)	
	private double ocena;
	
	@Column(name="brojac")
	private Integer brojac;
	
	@Version 
	private Long Version;
	
	
	@OneToMany(mappedBy = "hotelski",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private  Set<PricelistHotel> cijenovnici;
	//jedan hotel ima vise soba
	
	@OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Room> sobe = new HashSet<Room>();
	
    @OneToMany(mappedBy = "hotelKat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Category> kategorije = new HashSet<Category>();
	
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
		this.brojac=0;
	}
	public Hotel(Long id, String naziv, String adresa, String opis) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.brojac=0;
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
	
	public Set<PricelistHotel> getCijenovnici() {
		return cijenovnici;
	}
	public void setCijenovnici(Set<PricelistHotel> cijenovnici) {
		this.cijenovnici = cijenovnici;
	}
	public Set<Category> getKategorije() {
		return kategorije;
	}
	public void setKategorije(Set<Category> kategorije) {
		this.kategorije = kategorije;
	}
	
    public Integer getBrojac() {
		return brojac;
	}
	public void setBrojac(Integer brojac) {
		this.brojac = brojac;
	}

	/*	public Set<RezervacijaHotel> getRezervacije() {
		return rezervacije;
	}
	public void setRezervacije(Set<RezervacijaHotel> rezervacije) {
		this.rezervacije = rezervacije;
	}
	
	*/
	 public static Comparator<Hotel> HotelNameComparator = new Comparator<Hotel>() {

			public int compare(Hotel h1, Hotel h2) {
			   String name1 = h1.getNaziv().toUpperCase();
			   String name2 = h2.getNaziv().toUpperCase();

			   //sortiranje od A-Z
			   return name1.compareTo(name2);

	}};
	
	 public static Comparator<Hotel> HotelCityComparator = new Comparator<Hotel>() {

			public int compare(Hotel h1, Hotel h2) {
			   String city1 = h1.getGrad().toUpperCase();
			   String city2 = h2.getGrad().toUpperCase();

			   //sortiranje od A-Z
			   return city1.compareTo(city2);

	}};

	public String getGrad() {
		return grad;
	}
	public void setGrad(String grad) {
		this.grad = grad;
	}
	public Long getVersion() {
		return Version;
	}
	public void setVersion(Long version) {
		Version = version;
	}


}
