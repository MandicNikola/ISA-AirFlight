package rs.ftn.isa.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class PassengerInfo {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	@Column(name = "ime", nullable = false)
	private String ime;

	@Column(name = "prezime", nullable = false)
	private String prezime;
	
	@Column(name = "mail", nullable = false)
	private String mail;
	
	@Column(name="telefon", nullable = true)
	private String telefon;
	
	
	@Column(name="passport", nullable = true)
	private String passport;
	
	@Temporal(TemporalType.DATE)
	@Column(name="datumRodjenja", nullable = true)
	private Date datumRodjenja;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private Ticket karta;
	
	
	
	public PassengerInfo()
	{
		
	}
	
	


	public PassengerInfo(String ime, String prezime, String mail, String telefon, String passport, Date datumRodjenja) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.mail = mail;
		this.telefon = telefon;
		this.passport = passport;
		this.datumRodjenja = datumRodjenja;
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


	public Date getDatumRodjenja() {
		return datumRodjenja;
	}


	public void setDatumRodjenja(Date datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}




	public Ticket getKarta() {
		return karta;
	}




	public void setKarta(Ticket karta) {
		this.karta = karta;
	}
	
	
	
	
	
	
	
	
}
