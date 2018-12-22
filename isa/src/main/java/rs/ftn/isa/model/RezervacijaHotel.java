package rs.ftn.isa.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class RezervacijaHotel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	
	@Column(nullable = false)
	private Date datumDolaska;
	
	@Column(nullable = false)
	private Date datumOdlaska;
	
	@Column
	private boolean zavrsena;
	
	//jedan rezervacija moze da sadrzi vise soba
	@OneToMany(mappedBy = "rezervacija", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Room> sobe = new HashSet<Room>();
	
	//jedna rezervicija pripada tacno jednom korisniku
	@ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "korisnik_id")
	private User userHotel;
	
	//jedna rezervicija pripada tacno jednom hotelu
	@ManyToOne( fetch = FetchType.EAGER)
	private Hotel rezHotel;
	
	
	//jedna rezervicija moze da sadrzi vise dodatnih usluga
	@ManyToMany( fetch = FetchType.EAGER)
	private Set<Usluga>  usluge = new HashSet<Usluga>();

	public RezervacijaHotel() {
		super();
	}

	public RezervacijaHotel(Date datumDolaska, Date datumOdlaska) {
		super();
		this.datumDolaska = datumDolaska;
		this.datumOdlaska = datumOdlaska;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Date getDatumDolaska() {
		return datumDolaska;
	}


	public void setDatumDolaska(Date datumDolaska) {
		this.datumDolaska = datumDolaska;
	}


	public Date getDatumOdlaska() {
		return datumOdlaska;
	}


	public void setDatumOdlaska(Date datumOdlaska) {
		this.datumOdlaska = datumOdlaska;
	}


	public boolean isZavrsena() {
		return zavrsena;
	}


	public void setZavrsena(boolean zavrsena) {
		this.zavrsena = zavrsena;
	}


	public Set<Room> getSobe() {
		return sobe;
	}


	public void setSobe(Set<Room> sobe) {
		this.sobe = sobe;
	}


	public User getUserHotel() {
		return userHotel;
	}


	public void setUserHotel(User userHotel) {
		this.userHotel = userHotel;
	}


	public Set<Usluga> getUsluge() {
		return usluge;
	}


	public void setUsluge(Set<Usluga> usluge) {
		this.usluge = usluge;
	}

	public Hotel getRezHotel() {
		return rezHotel;
	}

	public void setRezHotel(Hotel rezHotel) {
		this.rezHotel = rezHotel;
	}
	


}
