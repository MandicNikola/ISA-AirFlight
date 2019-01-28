package rs.ftn.isa.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Seat {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//red i kolona sedista
	@Column(name = "red", nullable = false)
	private int red;
	
	@Column(name = "kolona", nullable = false)
	private int kolona;
	
	//segment kome pripadaju sedista
	@ManyToOne(fetch = FetchType.EAGER)
	private Segment segment;
	
	
	
	
	
}
