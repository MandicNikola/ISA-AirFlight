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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class AirPlane {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "naziv", nullable = false)
	private String naziv;
	
	//aviokompanija kojoj pripada avion
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private AirplaneCompany airComp;
	
	//segmenti koje sadrzi avion koji su mi bitni
	@OneToMany(mappedBy = "plane",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private  Set<Flight> letovi = new HashSet<Flight>();
	
	
	
	
	
	
}
