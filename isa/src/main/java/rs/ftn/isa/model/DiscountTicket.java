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
public class DiscountTicket {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "vrijednost", nullable = false)	
	private int vrednost;
	
	@Column(name = "bodovi", nullable = false)	
	private int bodovi;
	
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Ticket kartaPopust;
	
	
	
	public DiscountTicket()
	{
		
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public int getVrednost() {
		return vrednost;
	}



	public void setVrednost(int vrednost) {
		this.vrednost = vrednost;
	}



	public int getBodovi() {
		return bodovi;
	}



	public void setBodovi(int bodovi) {
		this.bodovi = bodovi;
	}



	public Ticket getKartaPopust() {
		return kartaPopust;
	}



	public void setKartaPopust(Ticket kartaPopust) {
		this.kartaPopust = kartaPopust;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DiscountTicket c = (DiscountTicket) o;
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
