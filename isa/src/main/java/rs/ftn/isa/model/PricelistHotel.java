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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
//cijenovnik
@Entity
public class PricelistHotel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "datum", nullable = false)
	private Date datum_primene;
	
	
	@OneToOne(fetch = FetchType.LAZY,
		    cascade =  CascadeType.ALL,
		    mappedBy = "cijenovnik")
	  private Hotel hotelski;
	 
	//cijenovnik ima vise usluga,jedna usluga jedan cijenovnik
	@OneToMany(mappedBy = "cijene", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Usluga> usluge = new HashSet<Usluga>();
	
	public PricelistHotel(int id, Date datum_primene) {
		super();
		this.id = id;
		this.datum_primene = datum_primene;
	}


	public Date getDatum_primene() {
		return datum_primene;
	}


	public void setDatum_primene(Date datum_primene) {
		this.datum_primene = datum_primene;
	}


	public Set<Usluga> getUsluge() {
		return usluge;
	}

	public void setUsluge(Set<Usluga> usluge) {
		this.usluge = usluge;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

}
