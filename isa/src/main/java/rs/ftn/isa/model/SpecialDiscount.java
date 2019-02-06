package rs.ftn.isa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SpecialDiscount {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "vrijednost", nullable = false)	
	private int vrijednost;
	
	@Column(name = "bodovi", nullable = false)	
	private int bodovi;

	public SpecialDiscount() {
		super();
	}
	
	public SpecialDiscount(int vrijednost, int bodovi) {
		super();
		this.vrijednost = vrijednost;
		this.bodovi = bodovi;
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
	
	
}
