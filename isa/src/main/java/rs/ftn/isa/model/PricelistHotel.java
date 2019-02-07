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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
//cijenovnik
@Entity
@Table(name = "cijenovnik")
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class PricelistHotel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "datum", nullable = false)
	private Date datum_primene;
	@Column(name = "aktivan", nullable = false)
	private boolean aktivan;
	
	@ManyToOne(fetch = FetchType.EAGER)
	  private Hotel hotelski;
	 
	//cijenovnik ima vise usluga,jedna usluga jedan cijenovnik
	@OneToMany(mappedBy = "cijene", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Usluga> usluge = new HashSet<Usluga>();
	public PricelistHotel() {}

	public PricelistHotel(Date datum_primene,boolean aktivan) {
		super();
		this.datum_primene = datum_primene;
		this.aktivan = aktivan;
	}

	public boolean isAktivan() {
		return aktivan;
	}
	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}
	public Hotel getHotelski() {
		return hotelski;
	}
	public void setHotelski(Hotel hotelski) {
		this.hotelski = hotelski;
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
