package rs.ftn.isa.model;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class AirPlane {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "naziv", nullable = false)
	private String naziv;
	
	//aviokompanija kojoj pripada avion
	@ManyToOne(fetch = FetchType.EAGER)
	private AirplaneCompany airComp;
	
	@Column(name = "kofiguracija", nullable = false)
	private String konfiguracija;
	
	@Column(name = "ekonomska", nullable = false)
	private int ekonomska;
	@Column(name = "biznis", nullable = false)
	private int biznis;
	@Column(name = "prvaKlasa", nullable = false)
	private int prvaKlasa;
	
	//segmenti koje sadrzi avion koji su mi bitni
	@OneToMany(mappedBy = "plane",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private  Set<Flight> letovi = new HashSet<Flight>();
	
	
	@OneToMany(mappedBy = "plane",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private  Set<Segment> segmenti = new HashSet<Segment>();
	
	
	

	public AirPlane() {
		super();
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


	public AirplaneCompany getAirComp() {
		return airComp;
	}


	public void setAirComp(AirplaneCompany airComp) {
		this.airComp = airComp;
	}


	public Set<Flight> getLetovi() {
		return letovi;
	}


	public void setLetovi(Set<Flight> letovi) {
		this.letovi = letovi;
	}




	public String getKonfiguracija() {
		return konfiguracija;
	}


	public void setKonfiguracija(String konfiguracija) {
		this.konfiguracija = konfiguracija;
	}


	public int getEkonomska() {
		return ekonomska;
	}


	public void setEkonomska(int ekonomska) {
		this.ekonomska = ekonomska;
	}


	public int getBiznis() {
		return biznis;
	}


	public void setBiznis(int biznis) {
		this.biznis = biznis;
	}


	public int getPrvaKlasa() {
		return prvaKlasa;
	}


	public void setPrvaKlasa(int prvaKlasa) {
		this.prvaKlasa = prvaKlasa;
	}


	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AirPlane c = (AirPlane) o;
        if(c.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, c.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


	public Set<Segment> getSegmenti() {
		return segmenti;
	}


	public void setSegmenti(Set<Segment> segmenti) {
		this.segmenti = segmenti;
	}

	
	
	
	
	
}
