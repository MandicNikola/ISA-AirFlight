package rs.ftn.isa.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
//moram jos doraditi klase koje mi trebaju
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
public class Flight {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Temporal(TemporalType.TIME)
	@Column(name = "vremePoletanja", nullable = false)
	private Date vremePoletanja;
	
	@Temporal(TemporalType.TIME)
	@Column(name = "vremeSletanja", nullable = false)
	private Date vremeSletanja;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "datumPoletanja", nullable = false)
	private Date datumPoletanja;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "datumSletanja", nullable = false)
	private Date datumSletanja;
	
	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "vremePovratka", nullable = true)
	private Date vremePovratka;
	
	
	@Column(name = "vreme", nullable = false)
	private double vreme;
	
	@Column(name = "duzina", nullable = false)
	private double duzina;
	
	@Column(name = "cena", nullable = false)
	private double cena;
	
	@Column(name = "tip", nullable = false)
	private String tip;
	
	@Column(name = "ocena", nullable = true)
	private double ocena;
	
	@Column(name = "brOcena", nullable =true)
	private int brOcena;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	private AirplaneCompany avioKomp;
	
	//karte vezane za let koje su rezervisane
	@OneToMany(mappedBy = "let",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private  Set<Ticket> karte = new HashSet<Ticket>();
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private AirPlane plane;
	
	
	 @ManyToMany(cascade = { CascadeType.ALL })
	    @JoinTable(
	        name = "let_destinacija", 
	        joinColumns = { @JoinColumn(name="flight_id",referencedColumnName="id")}, 
	        inverseJoinColumns = { @JoinColumn(name="destination_id",referencedColumnName="id")}
	    )
	 Set<Destination> presedanja = new HashSet<Destination>();
	
	 
	 @ManyToOne(fetch = FetchType.EAGER)
	 @JoinColumn(name = "poletanje_id")
     private Destination poletanje;
	 
	 @ManyToOne(fetch = FetchType.EAGER)
	 @JoinColumn(name = "sletanje_id")
     private Destination sletanje;
	 
	
	
	
	public Flight() {
		super();
	}

	
	
	
	
	public double getOcena() {
		return ocena;
	}





	public void setOcena(double ocena) {
		this.ocena = ocena;
	}





	public int getBrOcena() {
		return brOcena;
	}





	public void setBrOcena(int brOcena) {
		this.brOcena = brOcena;
	}





	public Set<Destination> getPresedanja() {
		return presedanja;
	}





	public void setPresedanja(Set<Destination> presedanja) {
		this.presedanja = presedanja;
	}





	public Destination getPoletanje() {
		return poletanje;
	}





	public String getTip() {
		return tip;
	}





	public void setTip(String tip) {
		this.tip = tip;
	}





	public void setPoletanje(Destination poletanje) {
		this.poletanje = poletanje;
	}





	public Destination getSletanje() {
		return sletanje;
	}





	public void setSletanje(Destination sletanje) {
		this.sletanje = sletanje;
	}





	public Date getVremePovratka() {
		return vremePovratka;
	}





	public void setVremePovratka(Date vremePovratka) {
		this.vremePovratka = vremePovratka;
	}





	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public AirplaneCompany getAvioKomp() {
		return avioKomp;
	}

	public void setAvioKomp(AirplaneCompany avioKomp) {
		this.avioKomp = avioKomp;
	}

	public Set<Ticket> getKarte() {
		return karte;
	}

	public void setKarte(Set<Ticket> karte) {
		this.karte = karte;
	}

	public AirPlane getPlane() {
		return plane;
	}

	public void setPlane(AirPlane plane) {
		this.plane = plane;
	}
	
	

	public Date getDatumPoletanja() {
		return datumPoletanja;
	}





	public void setDatumPoletanja(Date datumPoletanja) {
		this.datumPoletanja = datumPoletanja;
	}





	public Date getDatumSletanja() {
		return datumSletanja;
	}





	public void setDatumSletanja(Date datumSletanja) {
		this.datumSletanja = datumSletanja;
	}





	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Flight c = (Flight) o;
        if(c.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, c.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
	
	
	
	
	
	
}
