package rs.ftn.isa.model;

import java.sql.Date;

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

@Entity
public class ReservationTicket {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "datumRezervacije", nullable = false)
	private java.util.Date datumRezervacije;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private StatusRezervacije status;
	
	
	//imaju korisnika na kog se vezuju
	@ManyToOne(fetch = FetchType.EAGER)
	private User korisnik;
	
	@ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "ticket_id")
	private Ticket ticket;
	

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
	
	
	
	
}
