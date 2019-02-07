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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import rs.ftn.isa.dto.SeatDTO;

@Entity
public class Seat implements Comparable<Seat>{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//red i kolona sedista
	@Column(name = "red", nullable = false)
	private int red;
	
	@Column(name = "kolona", nullable = false)
	private int kolona;
	
	@Column(name = "klasa", nullable = false)
	private String klasa;
	
	@Column(name = "status", nullable = false)
	String status;
	
	//segment kome pripadaju sedista
	@ManyToOne(fetch = FetchType.EAGER)
	private Segment segment;
	
	
	@OneToMany(mappedBy = "sediste",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private  Set<Ticket> karte = new HashSet<Ticket>();
	
	
	
	public Seat()
	{
		
	}
	
	
	public Seat(int red, int kolona, String klasa) {
		super();
		this.red = red;
		this.kolona = kolona;
		this.klasa = klasa;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Seat c = (Seat) o;
        if(c.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, c.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public int getKolona() {
		return kolona;
	}

	public void setKolona(int kolona) {
		this.kolona = kolona;
	}

	public String getKlasa() {
		return klasa;
	}

	public void setKlasa(String klasa) {
		this.klasa = klasa;
	}

	public Segment getSegment() {
		return segment;
	}

	public void setSegment(Segment segment) {
		this.segment = segment;
	}

	public Set<Ticket> getKarte() {
		return karte;
	}

	public void setKarte(Set<Ticket> karte) {
		this.karte = karte;
	}

	
	

	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public int compareTo(Seat o) {
		// TODO Auto-generated method stub
		return Comparator.comparing(Seat::getRed).thenComparing(Seat::getKolona).compare(this, o);
	}
	
	
}
