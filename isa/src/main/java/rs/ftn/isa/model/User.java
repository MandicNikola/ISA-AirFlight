package rs.ftn.isa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "ime", nullable = false)
	private String ime;

	@Column(name = "prezime", nullable = false)
	private String prezime;
	
	@Column(name = "mail", nullable = false)
	private String mail;
	
	@Column(name="telefon", nullable = false)
	private int telefon;
	
	@Column(name="grad", nullable= false)
	private String grad;
	
	@Column(name="verifikovan")
	private String verifikovan;

	@Column(name="lozinka")
	private String lozinka;
	
	@Column(name="tip")
	private String tip;

	//jedna korisnik moze da ima vise rezervacija u jednom hotelu 
	@OneToMany(mappedBy = "userHotel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<RezervacijaHotel> rezHotela = new HashSet<RezervacijaHotel>();

	@OneToMany(mappedBy = "korisnik", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<RezervacijaRentCar> rezRent = new HashSet<RezervacijaRentCar>();

	
	public User() {
		super();
	}



	public User(Long id, String ime, String prezime, String mail, int telefon, String grad, String verifikovan,
			String lozinka, String tip) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.mail = mail;
		this.telefon = telefon;
		this.grad = grad;
		this.verifikovan = verifikovan;
		this.lozinka = lozinka;
		this.tip = tip;
	}



	public User(String ime, String prezime, String mail, int telefon, String grad, String lozinka) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.mail = mail;
		this.telefon = telefon;
		this.grad = grad;
		this.lozinka = lozinka;
		this.tip = "nereg";
	}


	public String getTip() {
		return tip;
	}



	public void setTip(String tip) {
		this.tip = tip;
	}



	public Set<RezervacijaHotel> getRezHotela() {
		return rezHotela;
	}



	public void setRezHotela(Set<RezervacijaHotel> rezHotela) {
		this.rezHotela = rezHotela;
	}



	public Set<RezervacijaRentCar> getRezRent() {
		return rezRent;
	}



	public void setRezRent(Set<RezervacijaRentCar> rezRent) {
		this.rezRent = rezRent;
	}



	public String getLozinka() {
		return lozinka;
	}





	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}





	public int getTelefon() {
		return telefon;
	}


	public void setTelefon(int telefon) {
		this.telefon = telefon;
	}


	public String getGrad() {
		return grad;
	}


	public void setGrad(String grad) {
		this.grad = grad;
	}




	public String getVerifikovan() {
		return verifikovan;
	}


	public void setVerifikovan(String verifikovan) {
		this.verifikovan = verifikovan;
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
