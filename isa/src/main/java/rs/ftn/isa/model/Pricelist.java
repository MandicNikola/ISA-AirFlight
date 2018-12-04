package rs.ftn.isa.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

public class Pricelist {
	private int id;
	private Date datum_pocetka;
	private Date datum_isteka;
	
	//cijenovnik ima vise usluga
	@OneToMany(mappedBy = "Pricelist", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Service> usluge = new HashSet<Service>();

	public Pricelist(int id,Date datum_pocetka, Date datum_isteka) {
		super();
		this.id = id;
		this.datum_pocetka = datum_pocetka;
		this.datum_isteka = datum_isteka;
	}

	public Date getDatum_pocetka() {
		return datum_pocetka;
	}

	public void setDatum_pocetka(Date datum_pocetka) {
		this.datum_pocetka = datum_pocetka;
	}

	public Date getDatum_isteka() {
		return datum_isteka;
	}

	public void setDatum_isteka(Date datum_isteka) {
		this.datum_isteka = datum_isteka;
	}

	public Set<Service> getUsluge() {
		return usluge;
	}

	public void setUsluge(Set<Service> usluge) {
		this.usluge = usluge;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
