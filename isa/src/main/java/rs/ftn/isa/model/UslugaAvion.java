package rs.ftn.isa.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class UslugaAvion {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="naziv", nullable = false)
	private String naziv;

	@Column(name="klasa", nullable = true)
	private String klasa;
	
	@Column(name="cena", nullable = false)
	private double cena;

	@Column(name="opis", nullable = false)
	private String opis;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Segment segment;
	
	
	public UslugaAvion()
	{
		
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}

	

	public String getNaziv() {
		return naziv;
	}



	public Segment getSegment() {
		return segment;
	}



	public void setSegment(Segment segment) {
		this.segment = segment;
	}



	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}



	public String getKlasa() {
		return klasa;
	}



	public void setKlasa(String klasa) {
		this.klasa = klasa;
	}



	public double getCena() {
		return cena;
	}



	public void setCena(double cena) {
		this.cena = cena;
	}



	public String getOpis() {
		return opis;
	}



	public void setOpis(String opis) {
		this.opis = opis;
	}
	
	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UslugaAvion c = (UslugaAvion) o;
        if(c.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, c.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
	
	
}
