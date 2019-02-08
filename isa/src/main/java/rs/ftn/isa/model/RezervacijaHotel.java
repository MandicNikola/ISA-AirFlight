package rs.ftn.isa.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
	
	@Column(nullable = false)
	private boolean ocenjenHotel;
	
	
	@Column(nullable = false)
	private double cijena;
	
	@Column(name ="rezavion")
	private Long rezavion;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private StatusRezervacije status;
	
	
	//jedan rezervacija moze da sadrzi vise soba
	@ManyToMany(mappedBy = "rezervacije")
	private Set<Room> sobe = new HashSet<Room>();
	
	//ovde cuvamo sve sobe koje su ocenjene
	@ManyToMany(mappedBy = "ocenjeneRezervacije")
	private Set<Room> ocenjeneSobe = new HashSet<Room>();
	
	
	//jedna rezervicija pripada tacno jednom korisniku
	@ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "korisnik_id")
	private User userHotel;

	
	//jedna rezervicija moze da sadrzi vise dodatnih usluga
	@ManyToMany(mappedBy = "rezHotela") 
	private Set<Usluga>  usluge = new HashSet<Usluga>();

	@Version 
	private Long Version;
	
	public RezervacijaHotel() {
		super();
		this.status=StatusRezervacije.AKTIVNA;
		this.ocenjenHotel=false;
		
	}

	public RezervacijaHotel(Date datumDolaska, Date datumOdlaska) {
		super();
		this.datumDolaska = datumDolaska;
		this.datumOdlaska = datumOdlaska;
		this.status=StatusRezervacije.AKTIVNA;
		this.ocenjenHotel=false;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public double getCijena() {
		return cijena;
	}

	public void setCijena(double cijena) {
		this.cijena = cijena;
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

	public StatusRezervacije getStatus() {
		return status;
	}

	public void setStatus(StatusRezervacije status) {
		this.status = status;
	}

	public boolean isOcenjenHotel() {
		return ocenjenHotel;
	}

	public void setOcenjenHotel(boolean ocenjenHotel) {
		this.ocenjenHotel = ocenjenHotel;
	}

	public Set<Room> getOcenjeneSobe() {
		return ocenjeneSobe;
	}

	public void setOcenjeneSobe(Set<Room> ocenjeneSobe) {
		this.ocenjeneSobe = ocenjeneSobe;
	}

	public Long getRezavion() {
		return rezavion;
	}

	public void setRezavion(Long rezavion) {
		this.rezavion = rezavion;
	}

	public Long getVersion() {
		return Version;
	}

	public void setVersion(Long version) {
		Version = version;
	}


}
