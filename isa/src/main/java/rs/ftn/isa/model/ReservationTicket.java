package rs.ftn.isa.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ReservationTicket {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "datumRezervacije", nullable = false)
	private java.util.Date datumRezervacije;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private StatusRezervacije status;
	
	@Column(name = "cena", nullable = true)
	private int cena;

	@Column(name = "ocenjenaKompanija", nullable = true)
	private boolean ocenjenaKompanija;
	
	@Column(name = "ocenjenLet", nullable = true)
	private boolean ocenjenLet;
	
	
	//imaju korisnika na kog se vezuju
	@ManyToOne(fetch = FetchType.EAGER)
	private User korisnik;
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "reservationTicket", cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
	private Set<Ticket> karte = new HashSet<Ticket>();
	
	

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

	

	public boolean isOcenjenaKompanija() {
		return ocenjenaKompanija;
	}

	public void setOcenjenaKompanija(boolean ocenjenaKompanija) {
		this.ocenjenaKompanija = ocenjenaKompanija;
	}

	public Set<Ticket> getKarte() {
		return karte;
	}

	public void setKarte(Set<Ticket> karte) {
		this.karte = karte;
	}

	public StatusRezervacije getStatus() {
		return status;
	}

	public void setStatus(StatusRezervacije status) {
		this.status = status;
	}

	public User getKorisnik() {
		return korisnik;
	}


	public void setKorisnik(User korisnik) {
		this.korisnik = korisnik;
	}

	public boolean isOcenjenLet() {
		return ocenjenLet;
	}

	public void setOcenjenLet(boolean ocenjenLet) {
		this.ocenjenLet = ocenjenLet;
	}

	public int getCena() {
		return cena;
	}

	public void setCena(int cena) {
		this.cena = cena;
	}
	
	public double getUkupno()
	{
		double suma = 0;
		for(Ticket karta : getKarte())
			suma += karta.getCena();
		
		return suma;
	}
	
	
}
