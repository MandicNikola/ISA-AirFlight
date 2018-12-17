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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
@Table(name = "cijenesoba")
public class CijenovnikSoba {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "datum", nullable = false)
	private Date datum_primene;
	@Column(name = "aktivan", nullable = false)
	private boolean aktivan;
	
	//jedan cijenovnik pripada jednoj sobi
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Room soba;
	
	//cijenovnik ima vise usluga,jedna usluga jedan cijenovnik
	@OneToMany(mappedBy = "cenesoba", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Usluga> usluge = new HashSet<Usluga>();
	
	public CijenovnikSoba() {
		super();
	}
	public CijenovnikSoba(Date datum_primene, boolean aktivan) {
		super();
		this.datum_primene = datum_primene;
		this.aktivan = aktivan;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDatum_primene() {
		return datum_primene;
	}
	public void setDatum_primene(Date datum_primene) {
		this.datum_primene = datum_primene;
	}
	public boolean isAktivan() {
		return aktivan;
	}
	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}
	public Room getSoba() {
		return soba;
	}
	public void setSoba(Room soba) {
		this.soba = soba;
	}
	public Set<Usluga> getUsluge() {
		return usluge;
	}
	public void setUsluge(Set<Usluga> usluge) {
		this.usluge = usluge;
	}
	
	
	
}
