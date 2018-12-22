package rs.ftn.isa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
	
	@Column(name = "kreveti", nullable = false)	
	private int kreveti;
	
	@Column(name = "sprat", nullable = false)	
	private int sprat;
	//cijena za noc
	@Column(name = "cijena", nullable = false)	
	private int cijena;

	@Column(name = "kapacitet", nullable = false)	
	private int kapacitet; //da ako ima,ne nema
    
	@Column(name = "balkon", nullable = false)	
	private String balkon; //da ako ima,ne nema
	
	@Column(name = "rezervisana")	
	private boolean rezervisana; 
    
    //jedna soba pripada jednog hotelu.
	@ManyToOne( fetch = FetchType.EAGER)
	 private Hotel hotel;
	
	 //jedna soba moze prirpadati vise rezervacija
	@ManyToMany(mappedBy = "sobe")
		private Set<RezervacijaHotel> rezervacije = new HashSet<RezervacijaHotel>();
	
	//jedna soba ima vise cjenovnika
	@OneToMany(mappedBy = "soba", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<CijenovnikSoba> cijenovnici = new HashSet<CijenovnikSoba>();
	
	public Room() {
		super();
	} 
	public Room(Long id, String tip, double ocjena, int kreveti, int sprat, int cijena,String balkon) {
		super();
		this.id = id;
		this.tip = tip;
		this.ocjena = ocjena;
		this.kreveti = kreveti;
		this.sprat = sprat;
		this.cijena = cijena;
		this.balkon = balkon;
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
	
	public Room( String tip, double ocjena, int kreveti, int sprat, int cijena,String balkon) {
		super();
		this.tip = tip;
		this.ocjena = ocjena;
		this.kreveti = kreveti;
		this.sprat = sprat;
		this.cijena = cijena;
		this.balkon = balkon;
	}
	
	public Room(String tip, double ocjena, int kreveti, int sprat, int cijena) {
		super();
		this.tip = tip;
		this.ocjena = ocjena;
		this.kreveti = kreveti;
		this.sprat = sprat;
		this.cijena = cijena;
	}

	public Room(String tip,int kapacitet,  int kreveti, int sprat, int cijena,String balkon) {
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

	public int getCijena() {
		return cijena;
	}

	public void setCijena(int cijena) {
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
	
	

}
