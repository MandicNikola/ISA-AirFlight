package rs.ftn.isa.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Destination {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "naziv", nullable = false)
	private String naziv;

	@ManyToMany(mappedBy="destinacije")
	Set<AirplaneCompany> kompanije;
	
	@ManyToMany(mappedBy="presedanja")
	Set<Flight> letovi;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "poletanje")
    private Set<Flight> poletanja;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sletanje")
    private Set<Flight> sletanja;

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
