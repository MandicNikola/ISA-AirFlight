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
public class Relation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//da li je tip veze jedan ili drugi
	@Column(name = "type")
	private String tip;
	
	//onaj koji salje zahtev 
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User relating;
	
	//koji prima zazhtev
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User related;
	
	
}
