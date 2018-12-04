package rs.ftn.isa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class Service {

//klasa za usluge
	
	private Long id;
	private String naziv;
	private int cena;
	
	public Service() {}

	
	public Service(String naziv, int cena) {
		super();
		this.naziv = naziv;
		this.cena= cena;
	}


	public Service(Long id, String naziv, int cena) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.cena= cena;
	}


	public int getCena() {
		return cena;
	}


	public void setCena(int cena) {
		this.cena = cena;
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


	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}


	@Override
	public String toString() {
		return "Service [id=" + id + ", naziv=" + naziv + "]";
	}
	
	
	
}
