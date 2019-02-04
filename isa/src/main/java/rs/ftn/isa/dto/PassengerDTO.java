package rs.ftn.isa.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class PassengerDTO {

	
	
	private Long id;
	
	private Long idKarte;
	
	private String ime;

	
	private String prezime;
	
	
	private String mail;
	
	
	private String telefon;
	
	
	
	private String passport;
	
	
	private String datumRodjenja;
	
	
	public PassengerDTO()
	{
		
	}
	
	


	public PassengerDTO(String ime, String prezime, String mail, String telefon, String passport, String datumRodjenja) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.mail = mail;
		this.telefon = telefon;
		this.passport = passport;
		this.datumRodjenja = datumRodjenja;
	}




	public Long getIdKarte() {
		return idKarte;
	}




	public void setIdKarte(Long idKarte) {
		this.idKarte = idKarte;
	}




	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getIme() {
		return ime;
	}


	public void setIme(String ime) {
		this.ime = ime;
	}


	public String getPrezime() {
		return prezime;
	}


	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public String getTelefon() {
		return telefon;
	}


	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}


	public String getPassport() {
		return passport;
	}


	public void setPassport(String passport) {
		this.passport = passport;
	}


	public String getDatumRodjenja() {
		return datumRodjenja;
	}


	public void setDatumRodjenja(String datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}
	
	
	
	
}
