package rs.ftn.isa.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import rs.ftn.isa.model.UslugaAvion;

public class UslugaAvionDTO {

	
	
	private Long id;
	
	
	private String naziv;

	
	private String klasa;
	
	
	private double cena;

	
	private String opis;
	
	
	
	public UslugaAvionDTO()
	{
		
	}

	public UslugaAvionDTO(UslugaAvion usluga)
	{
		this.id = usluga.getId();
		this.naziv = usluga.getNaziv();
		this.klasa = usluga.getKlasa();
		this.cena = usluga.getCena();
		this.opis = usluga.getOpis();
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
	
	
	
}
