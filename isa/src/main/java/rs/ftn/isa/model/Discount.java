package rs.ftn.isa.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
}
