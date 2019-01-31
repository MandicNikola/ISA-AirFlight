package rs.ftn.isa.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity
public class Discount {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "vrijednost", nullable = false)	
	private int vrijednost;
	
	@Column(name = "bodovi", nullable = false)	
	private int bodovi;
	
	@Column(name = "datumod", nullable = false)
	private Date datumod;
	
	@Column(name = "datumdo", nullable = false)
	private Date datumdo;
	
	//jedna popusti je def za jednu sobu
	@ManyToOne(fetch = FetchType.EAGER)
	private Room sobapopust;

	//jedna popusti je def za jednu sobu
	@ManyToOne(fetch = FetchType.EAGER)
	private Vehicle vozilopopust;

	public Discount() {
		super();
	}
	public Discount(int vrijednost, int bodovi, Date datumod, Date datumdo, Room sobapopust) {
		super();
		this.vrijednost = vrijednost;
		this.bodovi = bodovi;
		this.datumod = datumod;
		this.datumdo = datumdo;
		this.sobapopust = sobapopust;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVrijednost() {
		return vrijednost;
	}

	public void setVrijednost(int vrijednost) {
		this.vrijednost = vrijednost;
	}

	public int getBodovi() {
		return bodovi;
	}

	public void setBodovi(int bodovi) {
		this.bodovi = bodovi;
	}

	public Date getDatumod() {
		return datumod;
	}

	public void setDatumod(Date datumod) {
		this.datumod = datumod;
	}

	public Date getDatumdo() {
		return datumdo;
	}

	public void setDatumdo(Date datumdo) {
		this.datumdo = datumdo;
	}
	public Room getSobapopust() {
		return sobapopust;
	}
	public void setSobapopust(Room sobapopust) {
		this.sobapopust = sobapopust;
	}
	public Vehicle getVozilopopust() {
		return vozilopopust;
	}
	public void setVozilopopust(Vehicle vozilopopust) {
		this.vozilopopust = vozilopopust;
	}

	
	
}
