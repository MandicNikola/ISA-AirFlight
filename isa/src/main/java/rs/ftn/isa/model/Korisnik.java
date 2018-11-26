package rs.ftn.isa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Korisnik {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "ime", nullable = false)
	private String ime;

	@Column(name = "prezime", nullable = false)
	private String prezime;
	
	@Column(name = "mail", nullable = false)
	private String mail;

	
	public Korisnik() {
		super();
	}

	public Korisnik(String ime, String prezime, String mail) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.mail = mail;
	}

	public Korisnik( Long id, String ime, String prezime, String mail) {
		super();
		this.id=id;
		this.ime = ime;
		this.prezime = prezime;
		this.mail = mail;
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


		
}
