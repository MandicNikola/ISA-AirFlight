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
public class Segment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "redovi", nullable = false)
	private int redovi;
	
	@Column(name = "kolone", nullable = false)
	private int kolone;
	
	//faktor segmenta kojim mnozim cenu karte za faktorom npr.ako je biznis klasa ili ostalo
	@Column(name = "faktor", nullable = false)
	private int faktor;
	
	//sedista koja mu pripadaju
	@OneToMany(mappedBy = "segment",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private  Set<Seat> seats = new HashSet<Seat>();
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private AirPlane plane;
	
	
}
