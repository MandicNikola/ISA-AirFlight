package rs.ftn.isa.model;

import java.util.Date;

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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class RezervacijaRentCar implements Comparable<RezervacijaRentCar>{

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private StatusRezervacije status;
	
	@Column(nullable = false)
	private boolean ocenjenRent;
	
	@Column(nullable = false)
	private boolean ocenjenVozilo;
	
	
	@Column(nullable = true)
	private String mestoPreuzimanja;
	
	@Column(nullable = true)
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
	
	
	@Column(nullable = false)
	private int cena;
	
	public RezervacijaRentCar() {
		this.status=StatusRezervacije.AKTIVNA;
		
	}

	
	public RezervacijaRentCar(String mestoPreuzimanja, String mestoVracanja, Date datumPreuzimanja, Date datumVracanja,
			User korisnik, Vehicle vozilo) {
		super();
		this.mestoPreuzimanja = mestoPreuzimanja;
		this.mestoVracanja = mestoVracanja;
		this.datumPreuzimanja = datumPreuzimanja;
		this.datumVracanja = datumVracanja;
		this.korisnik = korisnik;
		this.vozilo = vozilo;
		this.status=StatusRezervacije.AKTIVNA;
		this.ocenjenRent=false;
		this.ocenjenVozilo=false;
	}


	public RezervacijaRentCar(String mestoPreuzimanja, String mestoVracanja, Date datumPreuzimanja, Date datumVracanja,
			User korisnik, Vehicle vozilo, int cena) {
		super();
		this.mestoPreuzimanja = mestoPreuzimanja;
		this.mestoVracanja = mestoVracanja;
		this.datumPreuzimanja = datumPreuzimanja;
		this.datumVracanja = datumVracanja;
		this.korisnik = korisnik;
		this.vozilo = vozilo;
		this.cena = cena;
		this.status=StatusRezervacije.AKTIVNA;
		this.ocenjenRent=false;
		this.ocenjenVozilo=false;
	}


	public int getCena() {
		return cena;
	}


	public void setCena(int cena) {
		this.cena = cena;
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

	public StatusRezervacije getStatus() {
		return status;
	}


	public void setStatus(StatusRezervacije status) {
		this.status = status;
	}


	public boolean isOcenjenRent() {
		return ocenjenRent;
	}


	public void setOcenjenRent(boolean ocenjenRent) {
		this.ocenjenRent = ocenjenRent;
	}


	public boolean isOcenjenVozilo() {
		return ocenjenVozilo;
	}


	public void setOcenjenVozilo(boolean ocenjenVozilo) {
		this.ocenjenVozilo = ocenjenVozilo;
	}


	@Override
	public int compareTo(RezervacijaRentCar o) {
		// TODO Auto-generated method stub
	    return getDatumPreuzimanja().compareTo(o.getDatumPreuzimanja());
	}


	
}
