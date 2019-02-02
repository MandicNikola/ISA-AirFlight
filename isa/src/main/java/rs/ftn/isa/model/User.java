package rs.ftn.isa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	
	@Column(name="servis")
	private Long servis;
	

	@Column(name="adminPotvrdio")
	private boolean adminPotvrdio;
	
	@Column(name="bodovi")
	private int bodovi;
	
	
	@Enumerated(EnumType.STRING)
	@Column(name="tip")
	private Role tip;
	
	
	//liste veza relacija u kojima se nalazi korisnik
	@OneToMany(mappedBy = "relating", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Relation> relatingRel = new HashSet<Relation>();
	
	@OneToMany(mappedBy = "related", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Relation> relatedRel = new HashSet<Relation>();
	
	//pregled pozivnica
	@OneToMany(mappedBy = "korisnik", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Pozivnica> pozivnice = new HashSet<Pozivnica>();
	
	//rez karata
	@OneToMany(mappedBy = "korisnik", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<ReservationTicket> resTicket = new HashSet<ReservationTicket>();
	
	
	//jedna korisnik moze da ima vise rezervacija u jednom hotelu 
	@OneToMany(mappedBy = "userHotel", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JsonIgnore
	private Set<RezervacijaHotel> rezHotela = new HashSet<RezervacijaHotel>();

	@OneToMany(mappedBy = "korisnik", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JsonIgnore
	private Set<RezervacijaRentCar> rezRent = new HashSet<RezervacijaRentCar>();

	
	public User() {
		super();
	}


	public User(Long id, String ime, String prezime, String mail, int telefon, String grad) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.mail = mail;
		this.telefon = telefon;
		this.grad = grad;
	}



	public User(Long id, String ime, String prezime, String mail, int telefon, String grad, String verifikovan,
			String lozinka, String tip,boolean adminPotvrdio) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.mail = mail;
		this.telefon = telefon;
		this.grad = grad;
		this.verifikovan = verifikovan;
		this.lozinka = lozinka;
		this.tip = Role.valueOf(tip);
		this.adminPotvrdio = adminPotvrdio;
	}



	public User(String ime, String prezime, String mail, int telefon, String grad, String lozinka, Role tip) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.mail = mail;
		this.telefon = telefon;
		this.grad = grad;
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
		this.tip = Role.NEREGISTROVAN;
	}






	public Role getTip() {
		return tip;
	}



	public void setTip(Role tip) {
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



	public Long getServis() {
		return servis;
	}



	public void setServis(Long servis) {
		this.servis = servis;
	}



	public Set<Relation> getRelatingRel() {
		return relatingRel;
	}



	public void setRelatingRel(Set<Relation> relatingRel) {
		this.relatingRel = relatingRel;
	}



	public Set<Relation> getRelatedRel() {
		return relatedRel;
	}



	public void setRelatedRel(Set<Relation> relatedRel) {
		this.relatedRel = relatedRel;
	}



	public Set<ReservationTicket> getResTicket() {
		return resTicket;
	}



	public void setResTicket(Set<ReservationTicket> resTicket) {
		this.resTicket = resTicket;
	}



	@Override
	public String toString() {
		return "User [id=" + id + ", ime=" + ime + ", prezime=" + prezime + ", mail=" + mail + ", telefon=" + telefon
				+ ", grad=" + grad + ", verifikovan=" + verifikovan + ", lozinka=" + lozinka + ", tip=" + tip + "]";
	}



	public boolean isAdminPotvrdio() {
		return adminPotvrdio;
	}



	public void setAdminPotvrdio(boolean adminPotvrdio) {
		this.adminPotvrdio = adminPotvrdio;
	}

	
	public void addRelationRelating(Relation relating)
	{
		addRelationRelating(relating,true);
	}
	
	public void addRelationRelating(Relation relating, boolean set)
	{
		if(relating != null)
		{
			getRelatingRel().add(relating);
		}
		if(set)
		{
			relating.setRelating(this,false);
		}
	}
	
	public void addRelationRelated(Relation related)
	{
		addRelationRelated(related,true);
	}
	
	public void addRelationRelated(Relation related, boolean set)
	{
		if(related != null)
		{
			getRelatedRel().add(related);
		}
		if(set)
		{
			related.setRelated(this,false);
		}
	}


	public int getBodovi() {
		return bodovi;
	}


	public void setBodovi(int bodovi) {
		this.bodovi = bodovi;
	}
	
		
}
