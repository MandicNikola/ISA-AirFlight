package rs.ftn.isa.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class RezervacijaRentCar {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private boolean zavrsena;
	
	
	@Column(nullable = false)
	private String mestoPreuzimanja;
	
	@Column(nullable = false)
	private String mestoVracanja;
	
	
	@Column(nullable = false)
	private Date datumPreuzimanja;
	
	@Column(nullable = false)
	private Date datumVracanja;
	
	@ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "korisnik_id")
	private User korisnik;
	
	@ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "vozilo_id")
	private Vehicle vozilo;
	
	@ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "usluga_id")
	private Usluga usluga;
	
	
	public RezervacijaRentCar() {}

	
	public RezervacijaRentCar(String mestoPreuzimanja, String mestoVracanja, Date datumPreuzimanja, Date datumVracanja,
			User korisnik, Vehicle vozilo, Usluga usluga) {
		super();
		this.mestoPreuzimanja = mestoPreuzimanja;
		this.mestoVracanja = mestoVracanja;
		this.datumPreuzimanja = datumPreuzimanja;
		this.datumVracanja = datumVracanja;
		this.korisnik = korisnik;
		this.vozilo = vozilo;
		this.usluga = usluga;
		this.zavrsena=false;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMestoPreuzimanja() {
		return mestoPreuzimanja;
	}

	public void setMestoPreuzimanja(String mestoPreuzimanja) {
		this.mestoPreuzimanja = mestoPreuzimanja;
	}

	public String getMestoVracanja() {
		return mestoVracanja;
	}

	public void setMestoVracanja(String mestoVracanja) {
		this.mestoVracanja = mestoVracanja;
	}

	public Date getDatumPreuzimanja() {
		return datumPreuzimanja;
	}

	public void setDatumPreuzimanja(Date datumPreuzimanja) {
		this.datumPreuzimanja = datumPreuzimanja;
	}

	public Date getDatumVracanja() {
		return datumVracanja;
	}

	public void setDatumVracanja(Date datumVracanja) {
		this.datumVracanja = datumVracanja;
	}

	public User getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(User korisnik) {
		this.korisnik = korisnik;
	}

	public Vehicle getVozilo() {
		return vozilo;
	}

	public void setVozilo(Vehicle vozilo) {
		this.vozilo = vozilo;
	}


	@Override
	public String toString() {
		return "RezervacijaRentCar [id=" + id + ", mestoPreuzimanja=" + mestoPreuzimanja + ", mestoVracanja="
				+ mestoVracanja + ", datumPreuzimanja=" + datumPreuzimanja + ", datumVracanja=" + datumVracanja
				+ ", korisnik=" + korisnik + ", vozilo=" + vozilo +"]";
	}

	public boolean isZavrsena() {
		return zavrsena;
	}

	public void setZavrsena(boolean zavrsena) {
		this.zavrsena = zavrsena;
	}

	public Usluga getUsluga() {
		return usluga;
	}

	public void setUsluga(Usluga usluga) {
		this.usluga = usluga;
	}
	
	
}
