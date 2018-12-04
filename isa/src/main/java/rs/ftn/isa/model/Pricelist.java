package rs.ftn.isa.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

public class Pricelist {
	private Date datum_pocetka;
	private Date datum_isteka;
	
	//cijenovnik ima vise usluga
	@OneToMany(mappedBy = "Pricelist", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Service> usluge = new HashSet<Service>();

	
}
