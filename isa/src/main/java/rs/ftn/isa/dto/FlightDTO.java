package rs.ftn.isa.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class FlightDTO {

	
	private Date vremePoletanja;
	private Date vremeSletanja;	
	private double vreme;	
	private double duzina;
	private double cena;

	
	
	
	public FlightDTO() {
		super();
	}
	public Date getVremePoletanja() {
		return vremePoletanja;
	}
	public void setVremePoletanja(Date vremePoletanja) {
		this.vremePoletanja = vremePoletanja;
	}
	public Date getVremeSletanja() {
		return vremeSletanja;
	}
	public void setVremeSletanja(Date vremeSletanja) {
		this.vremeSletanja = vremeSletanja;
	}
	public double getVreme() {
		return vreme;
	}
	public void setVreme(double vreme) {
		this.vreme = vreme;
	}
	public double getDuzina() {
		return duzina;
	}
	public void setDuzina(double duzina) {
		this.duzina = duzina;
	}
	public double getCena() {
		return cena;
	}
	public void setCena(double cena) {
		this.cena = cena;
	}
	
	
	
	
	
	
}
