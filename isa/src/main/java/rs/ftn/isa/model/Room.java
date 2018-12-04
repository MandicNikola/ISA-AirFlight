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
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "tip", nullable = false)
	private String tip;
	
	@Column(name = "ocena", nullable = false)	
	private double ocjena;
	
	@Column(name = "kreveti", nullable = false)	
	private int kreveti;
	
	@Column(name = "sprat", nullable = false)	
	private int sprat;
	//cijena za noc
	@Column(name = "cijena", nullable = false)	
	private double cijena;
	//jedna soba pripada jednog hotelu.
	 @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	 private Hotel hotel;
	 
	public Room() {
		super();
	} 
	public Room(Long id, String tip, double ocjena, int kreveti, int sprat, double cijena) {
		super();
		this.id = id;
		this.tip = tip;
		this.ocjena = ocjena;
		this.kreveti = kreveti;
		this.sprat = sprat;
		this.cijena = cijena;
	}
	
	public Room(String tip, double ocjena, int kreveti, int sprat, double cijena) {
		super();
		this.tip = tip;
		this.ocjena = ocjena;
		this.kreveti = kreveti;
		this.sprat = sprat;
		this.cijena = cijena;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public double getOcjena() {
		return ocjena;
	}

	public void setOcjena(double ocjena) {
		this.ocjena = ocjena;
	}

	public int getKreveti() {
		return kreveti;
	}

	public void setKreveti(int kreveti) {
		this.kreveti = kreveti;
	}

	public int getSprat() {
		return sprat;
	}

	public void setSprat(int sprat) {
		this.sprat = sprat;
	}

	public double getCijena() {
		return cijena;
	}

	public void setCijena(double cijena) {
		this.cijena = cijena;
	}
	


}
