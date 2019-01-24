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
public class Relation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//da li je tip veze jedan ili drugi
	@Column(name = "type")
	private String tip;
	
	//onaj koji salje zahtev 
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User relating;
	
	//koji prima zazhtev
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User related;

	public Relation()
	{
		
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

	public User getRelating() {
		return relating;
	}

	public void setRelating(User relating) {
		this.relating = relating;
	}

	public User getRelated() {
		return related;
	}

	public void setRelated(User related) {
		this.related = related;
	}
	
	
	
	
	
}
