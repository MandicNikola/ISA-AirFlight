package rs.ftn.isa.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")

public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "tip", nullable = false)
	private String tip; // tip je kategorija
	
	@Column(name = "ocena", nullable = false)	
	private double ocjena;
	
	@Column(name = "kreveti", nullable = true)	
	private int kreveti;
	
	@Column(name = "sprat", nullable = false)	
	private int sprat;
	//cijena za noc
	@Column(name = "cijena", nullable = false)	
	private double cijena;

	@Column(name = "kapacitet", nullable = false)	
	private int kapacitet; //da ako ima,ne nema
    
	@Column(name="brojac")
	private Integer brojac;
	
	@Column(name = "balkon", nullable = false)	
	private String balkon; //da ako ima,ne nema
	
	@Column(name = "rezervisana")	
	private boolean rezervisana; 
    //bice true ukoliko ima definisan ijedan popust
	@Column(name = "imapopusta")	
	private boolean imapopusta; 
    
	@Column(name = "broj_rezervacija")	
	private int brojRezervacija; 
   
	@Version 
	private Long Version;
	
    //jedna soba pripada jednog hotelu.
	@ManyToOne( fetch = FetchType.EAGER)
	 private Hotel hotel;
	
	//jedna soba moze da ima vise popusta 
	@OneToMany(mappedBy = "sobapopust", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Discount> popusti = new HashSet<Discount>();
		
	 //jedna soba moze prirpadati vise rezervacija
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(
	        name = "Rezervacija_Room", 
	        joinColumns = { @JoinColumn(name = "room_id") }, 
	        inverseJoinColumns = { @JoinColumn(name = "rezervacijaHotel_id") }
	  )
	private Set<RezervacijaHotel> rezervacije = new HashSet<RezervacijaHotel>();
	
	//ovde cuvamo rezervacije u kojima je soba ocenjena
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(
	        name = "Ocenjene_Rezervacije", 
	        joinColumns = { @JoinColumn(name = "room_id") }, 
	        inverseJoinColumns = { @JoinColumn(name = "rezervacijaHotel_id") }
	  )
	private Set<RezervacijaHotel> ocenjeneRezervacije = new HashSet<RezervacijaHotel>();
	
	
	//jedna soba ima vise cjenovnika
	@OneToMany(mappedBy = "soba", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<CijenovnikSoba> cijenovnici = new HashSet<CijenovnikSoba>();
	
	public Room() {
		super();
		brojRezervacija = 0;
		brojac = 0;
	} 
	
	public Room(Long id, String tip, double ocjena, int kreveti, int sprat, double cijena,String balkon) {
		super();
		this.id = id;
		this.tip = tip;
		this.ocjena = ocjena;
		this.kreveti = kreveti;
		this.sprat = sprat;
		this.cijena = cijena;
		this.balkon = balkon;
		brojac = 0;
	}
		
	public Room(String tip, int kreveti, int sprat,int kapacitet,String balkon) {
		super();
		
		this.tip = tip;
		this.ocjena = 0;
		this.kreveti = kreveti;
		this.sprat = sprat;
		this.cijena = 0;
		this.kapacitet = kapacitet;
		this.balkon = balkon;
	}
	
	public Room( String tip, double ocjena, int kreveti, int sprat, double cijena,String balkon) {
		super();
		this.tip = tip;
		this.ocjena = ocjena;
		this.kreveti = kreveti;
		this.sprat = sprat;
		this.cijena = cijena;
		this.balkon = balkon;
	}
	
	public Room(String tip, double ocjena, int kreveti, int sprat, double cijena) {
		super();
		this.tip = tip;
		this.ocjena = ocjena;
		this.kreveti = kreveti;
		this.sprat = sprat;
		this.cijena = cijena;
	}

	public Room(String tip,int kapacitet,  int kreveti, int sprat, double cijena,String balkon) {
		super();
		this.tip = tip;
		this.ocjena = 0;
		this.kapacitet = kapacitet;
		this.kreveti = kreveti;
		this.sprat = sprat;
		this.cijena = cijena;
		this.balkon = balkon;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public double getOcjena() {
		return ocjena;
	}

	public void setOcjena(double ocjena) {
		this.ocjena = ocjena;
	}

	public int getKreveti() {
		return kreveti;
	}

	public void setKreveti(int kreveti) {
		this.kreveti = kreveti;
	}

	public int getSprat() {
		return sprat;
	}

	public void setSprat(int sprat) {
		this.sprat = sprat;
	}

	public double getCijena() {
		return cijena;
	}

	public void setCijena(double cijena) {
		this.cijena = cijena;
	}
	public Hotel getHotel() {
		return hotel;
	}
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	public String getBalkon() {
		return balkon;
	}
	public void setBalkon(String balkon) {
		this.balkon = balkon;
	}
	public int getKapacitet() {
		return kapacitet;
	}
	public void setKapacitet(int kapacitet) {
		this.kapacitet = kapacitet;
	}
	public Set<CijenovnikSoba> getCijenovnici() {
		return cijenovnici;
	}
	public void setCijenovnici(Set<CijenovnikSoba> cijenovnici) {
		this.cijenovnici = cijenovnici;
	}
	public boolean isRezervisana() {
		return rezervisana;
	}
	public void setRezervisana(boolean rezervisana) {
		this.rezervisana = rezervisana;
	}
	public Set<RezervacijaHotel> getRezervacije() {
		return rezervacije;
	}
	public void setRezervacije(Set<RezervacijaHotel> rezervacije) {
		this.rezervacije = rezervacije;
	}
	public int getBrojRezervacija() {
		return brojRezervacija;
	}
	public void setBrojRezervacija(int brojRezervacija) {
		this.brojRezervacija = brojRezervacija;
	}

	public Integer getBrojac() {
		return brojac;
	}

	public void setBrojac(Integer brojac) {
		this.brojac = brojac;
	}

	public Set<RezervacijaHotel> getOcenjeneRezervacije() {
		return ocenjeneRezervacije;
	}

	public void setOcenjeneRezervacije(Set<RezervacijaHotel> ocenjeneRezervacije) {
		this.ocenjeneRezervacije = ocenjeneRezervacije;
	}

	public boolean isImapopusta() {
		return imapopusta;
	}

	public void setImapopusta(boolean imapopusta) {
		this.imapopusta = imapopusta;
	}

	public Set<Discount> getPopusti() {
		return popusti;
	}

	public void setPopusti(Set<Discount> popusti) {
		this.popusti = popusti;
	}

	public Long getVersion() {
		return Version;
	}

	public void setVersion(Long version) {
		Version = version;
	}


	

}
