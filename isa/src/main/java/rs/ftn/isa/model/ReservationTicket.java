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
	
	//imaju korisnika na kog se vezuju
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User korisnik;
	
	
	
	
}
