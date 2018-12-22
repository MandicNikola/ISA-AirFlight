package rs.ftn.isa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Destination {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "naziv", nullable = false)
	private String naziv;

	
	//trebam ubaciti odnose koji mi fale jos 
	
	public Destination()
	{
		super();
	}
	
	public Destination(Long id, String naziv) {
		super();
		this.id = id;
		this.naziv = naziv;
	}
	
	
}