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
	@Column(name = "red",nullable = false)
	private int red;
	
	@Column(name = "mesto",nullable = false)
	private int mesto;
	
	@Column(name = "popust", nullable = true)
	private int popust;
	
	
	@Column(name = "rezervisano", nullable = false)
	private boolean rezervisano;
	
	//da znamo kom letu pripada kada mi treba
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Flight let;
	
	@OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
	private Pozivnica pozivnica;
	
	
	public Ticket()
	{
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public int getRed() {
		return red;
	}


	public void setRed(int red) {
		this.red = red;
	}


	public int getMesto() {
		return mesto;
	}


	public void setMesto(int mesto) {
		this.mesto = mesto;
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
	
	
	
	
	
}
