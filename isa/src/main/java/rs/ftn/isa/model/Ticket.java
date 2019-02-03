package rs.ftn.isa.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//fale mi jos dorade za tabele i sve ostalo
@Entity
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//sediste cena i popust
	
	@Column(name = "popust", nullable = true)
	private int popust;
	
	@Column(name = "klasa", nullable = false)
	private String klasa;
	
	@Column(name = "rezervisano", nullable = false)
	private boolean rezervisano;
	
	//da znamo kom letu pripada kada mi treba
	@ManyToOne(fetch = FetchType.EAGER)
	private Flight let;
	
	@OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
	private Pozivnica pozivnica;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Seat sediste;
	
	public Ticket()
	{
		
	}

	
	
	
	public Ticket(boolean rezervisano, String klasa) {
		super();
		this.rezervisano = rezervisano;
		this.klasa = klasa;
	}




	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	


	public int getPopust() {
		return popust;
	}


	public void setPopust(int popust) {
		this.popust = popust;
	}


	public boolean isRezervisano() {
		return rezervisano;
	}


	public void setRezervisano(boolean rezervisano) {
		this.rezervisano = rezervisano;
	}


	public Flight getLet() {
		return let;
	}


	public void setLet(Flight let) {
		this.let = let;
	}


	public Pozivnica getPozivnica() {
		return pozivnica;
	}


	public void setPozivnica(Pozivnica pozivnica) {
		this.pozivnica = pozivnica;
	}


	public String getKlasa() {
		return klasa;
	}


	public void setKlasa(String klasa) {
		this.klasa = klasa;
	}


	public Seat getSediste() {
		return sediste;
	}


	public void setSediste(Seat sediste) {
		this.sediste = sediste;
	}
	
	
	
	
	
}
