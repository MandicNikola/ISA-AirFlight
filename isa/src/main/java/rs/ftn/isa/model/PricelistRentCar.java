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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "cenovnik")
public class PricelistRentCar {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="datum_primene", nullable = false)
	private Date datum_primene;
	
	//cenovnik ima vise usluga
	
	@OneToMany(mappedBy = "lista", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Usluga> usluge = new HashSet<Usluga>();

	
	@OneToOne(mappedBy = "cenovnik", fetch = FetchType.LAZY,cascade =  CascadeType.ALL)
	@JsonIgnore
	private RentACar rentcar; 
	
    public PricelistRentCar() {}
    
	public PricelistRentCar(Date datum_primene) {
		super();
		this.datum_primene=datum_primene;
	}

	
	public Date getDatum_primene() {
		return datum_primene;
	}


	public void setDatum_primene(Date datum_primene) {
		this.datum_primene = datum_primene;
	}


	public Set<Usluga> getUsluge() {
		return usluge;
	}

	public void setUsluge(Set<Usluga> usluge) {
		this.usluge = usluge;
	}

	public int getId() {
		return id;
	}

	public RentACar getRentcar() {
		return rentcar;
	}

	public void setRentcar(RentACar rentcar) {
		this.rentcar = rentcar;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
