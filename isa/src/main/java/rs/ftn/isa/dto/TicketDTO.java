package rs.ftn.isa.dto;

import java.util.Comparator;

import rs.ftn.isa.model.Ticket;

public class TicketDTO implements Comparable<TicketDTO>{

	private Long id;
	private int red;
	private int kolona;
	
	private String klasa;
	private double cena;
	private int popust;
	
	private String lokPoletanja;
	private String lokSletanja;
	
	private String datumPoletanja;
	
	private Long idPopusta;
	
	//ostaje mi samo za cenu dorada
	public TicketDTO()
	{
		
	}
	
	public TicketDTO(Ticket t)
	{
		this.id = t.getId();
		this.klasa = t.getKlasa();
		this.red = t.getSediste().getRed();
		this.kolona = t.getSediste().getKolona();
	}
	
	
	public String getLokPoletanja() {
		return lokPoletanja;
	}

	public void setLokPoletanja(String lokPoletanja) {
		this.lokPoletanja = lokPoletanja;
	}

	public String getLokSletanja() {
		return lokSletanja;
	}

	public void setLokSletanja(String lokSletanja) {
		this.lokSletanja = lokSletanja;
	}

	public String getDatumPoletanja() {
		return datumPoletanja;
	}

	public void setDatumPoletanja(String datumPoletanja) {
		this.datumPoletanja = datumPoletanja;
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
	public int getKolona() {
		return kolona;
	}
	public void setKolona(int kolona) {
		this.kolona = kolona;
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

	@Override
	public int compareTo(TicketDTO o) {
		// TODO Auto-generated method stub
		return Comparator.comparing(TicketDTO::getId).compare(this, o);
	}

	public Long getIdPopusta() {
		return idPopusta;
	}

	public void setIdPopusta(Long idPopusta) {
		this.idPopusta = idPopusta;
	}

	public int getPopust() {
		return popust;
	}

	public void setPopust(int popust) {
		this.popust = popust;
	}
	
	
	
	
	
	
}
