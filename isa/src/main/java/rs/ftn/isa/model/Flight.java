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
//moram jos doraditi klase koje mi trebaju
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
public class Flight {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "vremePoletanja", nullable = false)
	private Date vremePoletanja;
	
	@Column(name = "vremeSletanja", nullable = false)
	private Date vremeSletanja;
	
	
	@Column(name = "vreme", nullable = false)
	private int vreme;
	
	@Column(name = "duzina", nullable = false)
	private int duzina;
	
	@Column(name = "cena", nullable = false)
	private int cena;
	
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private AirplaneCompany avioKomp;
	
	//karte vezane za let koje su rezervisane
	@OneToMany(mappedBy = "let",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private  Set<Ticket> karte = new HashSet<Ticket>();
	
}
