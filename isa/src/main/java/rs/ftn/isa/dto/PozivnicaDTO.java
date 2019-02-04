package rs.ftn.isa.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import rs.ftn.isa.model.Ticket;
import rs.ftn.isa.model.User;

public class PozivnicaDTO {


	private Long id;
			
	private boolean rezervisano;
	
	private Long ticketID;
	 
	private Long korisnikID;
	 
	public PozivnicaDTO()
	{
		 
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public boolean isRezervisano() {
		return rezervisano;
	}
	
	public void setRezervisano(boolean rezervisano) {
		this.rezervisano = rezervisano;
	}

	public Long getTicketID() {
		return ticketID;
	}

	public void setTicketID(Long ticketID) {
		this.ticketID = ticketID;
	}

	public Long getKorisnikID() {
		return korisnikID;
	}

	public void setKorisnikID(Long korisnikID) {
		this.korisnikID = korisnikID;
	}
	
	

	
}
