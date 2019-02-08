package rs.ftn.isa.model;


import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="kompanije")
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class AirplaneCompany {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "naziv", nullable = false)
	private String naziv;

	@Column(name = "adresa", nullable = false)
	private String adresa;
	
	@Column(name = "opis", nullable = false)
	private String opis;

	@Column(name="ocena", nullable = true)
	private double ocena;

	@Column(name="brojac", nullable = true)
	private Integer brojac;


	//avioni koji mu pripadaju aviokompaniji
	@OneToMany(mappedBy = "airComp",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private  Set<AirPlane> avioni = new HashSet<AirPlane>();
	
	//letovi koji mi trebaju
	@OneToMany(mappedBy = "avioKomp",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private  Set<Flight> letovi = new HashSet<Flight>();
	
	
	@ManyToMany(cascade =  CascadeType.ALL)
	@JoinTable(name="kompanija_destinacija",
			joinColumns = { @JoinColumn(name = "company_id",referencedColumnName="id") }, 
	        inverseJoinColumns = { @JoinColumn(name = "destination_id",referencedColumnName="id")})
	Set<Destination> destinacije = new HashSet<Destination>();
	
	
	
	
	public AirplaneCompany() {
		super();
	}
	public AirplaneCompany(Long id,String naziv, String adresa, String opis) {
		super();
		this.id=id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
	}
	public AirplaneCompany(String naziv, String adresa, String opis) {
		super();
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
	}

	
	
	
	
	public Set<Destination> getDestinacije() {
		return destinacije;
	}
	public void setDestinacije(Set<Destination> destinacije) {
		this.destinacije = destinacije;
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
	public Integer getBrojac() {
		return brojac;
	}
	public void setBrojac(Integer brojac) {
		this.brojac = brojac;
	}


	public static Comparator<AirplaneCompany> AirplaneNameComparator = new Comparator<AirplaneCompany>() {

			public int compare(AirplaneCompany A1, AirplaneCompany A2) {
			   String name1 = A1.getNaziv().toUpperCase();
			   String name2 = A2.getNaziv().toUpperCase();

			   //sortiranje od A-Z
			   return name1.compareTo(name2);

	}};
	
	 public static Comparator<AirplaneCompany> AirplaneCityComparator = new Comparator<AirplaneCompany>() {

			public int compare(AirplaneCompany A1, AirplaneCompany A2) {
			   String city1 = A1.getAdresa().toUpperCase();
			   String city2 = A2.getAdresa().toUpperCase();

			   //sortiranje od A-Z
			   return city1.compareTo(city2);

	}};




	public Set<AirPlane> getAvioni() {
		return avioni;
	}
	public void setAvioni(Set<AirPlane> avioni) {
		this.avioni = avioni;
	}
	public Set<Flight> getLetovi() {
		return letovi;
	}
	public void setLetovi(Set<Flight> letovi) {
		this.letovi = letovi;
	}


	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AirplaneCompany c = (AirplaneCompany) o;
        if(c.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, c.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

	
	
	
	
	
	
	
}
