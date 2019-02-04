package rs.ftn.isa.model;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	private ReservationTicket reservationTicket;
	
	 @OneToOne(fetch = FetchType.LAZY,
	            cascade =  CascadeType.ALL,
	            mappedBy = "karta")
	 private PassengerInfo passengerInfo;
	
	
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

	


	public ReservationTicket getReservationTicket() {
		return reservationTicket;
	}




	public void setReservationTicket(ReservationTicket reservationTicket) {
		this.reservationTicket = reservationTicket;
	}




	public PassengerInfo getPassengerInfo() {
		return passengerInfo;
	}




	public void setPassengerInfo(PassengerInfo passengerInfo) {
		this.passengerInfo = passengerInfo;
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
	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket c = (Ticket) o;
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
