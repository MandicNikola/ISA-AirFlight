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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Segment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "naziv", nullable = false)
	private String naziv;
	
	@Column(name = "redovi", nullable = false)
	private int redovi;
	
	
	//faktor segmenta kojim mnozim cenu karte za faktorom npr.ako je biznis klasa ili ostalo
	@Column(name = "faktor", nullable = false)
	private int faktor;
	
	//sedista koja mu pripadaju
	@OneToMany(mappedBy = "segment",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private  Set<Seat> seats = new HashSet<Seat>();
	
	@ManyToOne(fetch = FetchType.EAGER)
	private AirPlane plane;
	
	
	@OneToMany(mappedBy = "segment",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private  Set<UslugaAvion> usluge = new HashSet<UslugaAvion>();
	
	
	public Segment()
	{
		
	}
	
	
	
	
	
	public Segment(String naziv, int redovi, int faktor) {
		super();
		this.naziv = naziv;
		this.redovi = redovi;
		this.faktor = faktor;
	}





	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public Set<UslugaAvion> getUsluge() {
		return usluge;
	}


	public void setUsluge(Set<UslugaAvion> usluge) {
		this.usluge = usluge;
	}


	public String getNaziv() {
		return naziv;
	}





	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}





	public int getRedovi() {
		return redovi;
	}





	public void setRedovi(int redovi) {
		this.redovi = redovi;
	}





	public int getFaktor() {
		return faktor;
	}





	public void setFaktor(int faktor) {
		this.faktor = faktor;
	}





	public Set<Seat> getSeats() {
		return seats;
	}





	public void setSeats(Set<Seat> seats) {
		this.seats = seats;
	}





	public AirPlane getPlane() {
		return plane;
	}





	public void setPlane(AirPlane plane) {
		this.plane = plane;
	}





	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Segment c = (Segment) o;
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
