package rs.ftn.isa.model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ReservationTicket {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "datumRezervacije", nullable = false)
	private java.util.Date datumRezervacije;
	
	@Column(name = "prosla", nullable = false)
	private boolean zavrsena;
	
	@Column(name = "finalizirana", nullable = false)
	private boolean finalizirana;
	
	@Column(name = "rezervacijaAvion", nullable = true)
	private Long rezervacijaAvion;
	
	@Column(name = "rezervacijaHotel", nullable = true)
	private Long rezervacijaHotel;
	
	
	//imaju korisnika na kog se vezuju
	@ManyToOne(fetch = FetchType.EAGER)
	private User korisnik;

	public ReservationTicket()
	{
		
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public java.util.Date getDatumRezervacije() {
		return datumRezervacije;
	}


	public void setDatumRezervacije(java.util.Date datumRezervacije) {
		this.datumRezervacije = datumRezervacije;
	}


	public boolean isZavrsena() {
		return zavrsena;
	}


	public void setZavrsena(boolean zavrsena) {
		this.zavrsena = zavrsena;
	}


	public Long getRezervacijaAvion() {
		return rezervacijaAvion;
	}


	public void setRezervacijaAvion(Long rezervacijaAvion) {
		this.rezervacijaAvion = rezervacijaAvion;
	}


	public Long getRezervacijaHotel() {
		return rezervacijaHotel;
	}


	public void setRezervacijaHotel(Long rezervacijaHotel) {
		this.rezervacijaHotel = rezervacijaHotel;
	}


	public User getKorisnik() {
		return korisnik;
	}


	public void setKorisnik(User korisnik) {
		this.korisnik = korisnik;
	}
	
	
	
	
}
