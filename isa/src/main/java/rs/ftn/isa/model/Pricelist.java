package rs.ftn.isa.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

public class Pricelist {
	private int id;
	private Date datum_primene;
	
	//cijenovnik ima vise usluga
	@OneToMany(mappedBy = "Pricelist", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Service> usluge = new HashSet<Service>();

	public Pricelist(int id,Date datum_primene) {
		super();
		this.id = id;
		this.datum_primene=datum_primene;
	}

	
	public Date getDatum_primene() {
		return datum_primene;
	}


	public void setDatum_primene(Date datum_primene) {
		this.datum_primene = datum_primene;
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
