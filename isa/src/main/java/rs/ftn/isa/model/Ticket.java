package rs.ftn.isa.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

//fale mi jos dorade za tabele i sve ostalo
@Entity
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//sediste cena i popust
	@Column(name = "red",nullable = false)
	private int red;
	
	@Column(name = "mesto",nullable = false)
	private int mesto;
	
	@Column(name = "popust", nullable = true)
	private int popust;
	
	//da znamo kom letu pripada kada mi treba
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Flight let;
	
	
	
	
}
