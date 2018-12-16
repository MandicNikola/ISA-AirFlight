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
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "naziv", nullable = false)	
	private String naziv;
	
	   //jedna soba pripada jednog hotelu.
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
     private Hotel hotelKat;
		
	public Category( String naziv) {
		super();
		this.naziv = naziv;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	
	
	
}
