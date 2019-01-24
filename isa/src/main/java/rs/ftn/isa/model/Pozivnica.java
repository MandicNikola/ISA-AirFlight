package rs.ftn.isa.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Pozivnica {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "rezervisana", nullable = false)
	private boolean rezervisano;
	
	@Column(name = "datum", nullable = false)
	private Date datum;
	
	//postavljamo jos kartu na koju se odnosi pozivnica 
	//postavljamo usera na kog se odnosi
	
	 @OneToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "ticket_id")
	 private Ticket ticket;
	 
	 @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	 private User korisnik;
	 
	
	
}
