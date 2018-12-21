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
    @JoinColumn(name = "rent_id")
	private RentACar servisrent;
	
	public RezervacijaRentCar() {}

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

	public RentACar getServisrent() {
		return servisrent;
	}

	public void setServisrent(RentACar servisrent) {
		this.servisrent = servisrent;
	}

	@Override
	public String toString() {
		return "RezervacijaRentCar [id=" + id + ", mestoPreuzimanja=" + mestoPreuzimanja + ", mestoVracanja="
				+ mestoVracanja + ", datumPreuzimanja=" + datumPreuzimanja + ", datumVracanja=" + datumVracanja
				+ ", korisnik=" + korisnik + ", vozilo=" + vozilo + ", servisrent=" + servisrent + "]";
	}
	
	
}
