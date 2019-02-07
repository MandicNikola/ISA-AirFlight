package rs.ftn.isa.dto;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class FlightDTO {

	
	private Long idLeta;
	private Long idAviona;
	private Long idKompanije;
	
	private String nazivKompanije;
	
	private String datumPoletanja;
	private String datumSletanja;
	private String vremePoletanja;
	private String vremeSletanja;
	
	private String datumPovratka;
	
	private double vreme;	
	private double duzina;
	private double cena;
	
	
	private String tip;
	private String klasa;

	//**sva presedanja koja mi se desavaju u letu
	private ArrayList<Long> presedanja = new ArrayList<Long>();
	private Long lokacijaPoletanja;
	private Long lokacijaSletanja;
	
	private int brojEkonomska;
	private int brojBiznis;
	private int brojPrva;
	
	private String lokPoletanja;
	private String lokSletanja;
	private int brojLjudi;
	private int brojPresedanja;
	
	public String avion;
	
	
	public FlightDTO() {
		super();
	}

	
	
	
	
	public String getLokPoletanja() {
		return lokPoletanja;
	}





	public String getAvion() {
		return avion;
	}





	public void setAvion(String avion) {
		this.avion = avion;
	}





	public String getNazivKompanije() {
		return nazivKompanije;
	}





	public void setNazivKompanije(String nazivKompanije) {
		this.nazivKompanije = nazivKompanije;
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





	public int getBrojLjudi() {
		return brojLjudi;
	}





	public void setBrojLjudi(int brojLjudi) {
		this.brojLjudi = brojLjudi;
	}





	public String getDatumPovratka() {
		return datumPovratka;
	}





	public void setDatumPovratka(String datumPovratka) {
		this.datumPovratka = datumPovratka;
	}





	public String getTip() {
		return tip;
	}





	public void setTip(String tip) {
		this.tip = tip;
	}





	public String getKlasa() {
		return klasa;
	}





	public int getBrojPresedanja() {
		return brojPresedanja;
	}





	public void setBrojPresedanja(int brojPresedanja) {
		this.brojPresedanja = brojPresedanja;
	}





	public void setKlasa(String klasa) {
		this.klasa = klasa;
	}





	public Long getIdLeta() {
		return idLeta;
	}





	public void setIdLeta(Long idLeta) {
		this.idLeta = idLeta;
	}





	public String getDatumPoletanja() {
		return datumPoletanja;
	}





	public void setDatumPoletanja(String datumPoletanja) {
		this.datumPoletanja = datumPoletanja;
	}





	public String getDatumSletanja() {
		return datumSletanja;
	}





	public void setDatumSletanja(String datumSletanja) {
		this.datumSletanja = datumSletanja;
	}





	public Long getIdAviona() {
		return idAviona;
	}





	public void setIdAviona(Long idAviona) {
		this.idAviona = idAviona;
	}





	public Long getIdKompanije() {
		return idKompanije;
	}





	public void setIdKompanije(Long idKompanije) {
		this.idKompanije = idKompanije;
	}





	public ArrayList<Long> getPresedanja() {
		return presedanja;
	}





	public void setPresedanja(ArrayList<Long> presedanja) {
		this.presedanja = presedanja;
	}





	public Long getLokacijaPoletanja() {
		return lokacijaPoletanja;
	}





	public void setLokacijaPoletanja(Long lokacijaPoletanja) {
		this.lokacijaPoletanja = lokacijaPoletanja;
	}





	public Long getLokacijaSletanja() {
		return lokacijaSletanja;
	}





	public void setLokacijaSletanja(Long lokacijaSletanja) {
		this.lokacijaSletanja = lokacijaSletanja;
	}





	public String getVremePoletanja() {
		return vremePoletanja;
	}

	public void setVremePoletanja(String vremePoletanja) {
		this.vremePoletanja = vremePoletanja;
	}

	public String getVremeSletanja() {
		return vremeSletanja;
	}



	public void setVremeSletanja(String vremeSletanja) {
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





	public int getBrojEkonomska() {
		return brojEkonomska;
	}





	public void setBrojEkonomska(int brojEkonomska) {
		this.brojEkonomska = brojEkonomska;
	}





	public int getBrojBiznis() {
		return brojBiznis;
	}





	public void setBrojBiznis(int brojBiznis) {
		this.brojBiznis = brojBiznis;
	}





	public int getBrojPrva() {
		return brojPrva;
	}





	public void setBrojPrva(int brojPrva) {
		this.brojPrva = brojPrva;
	}
	
	
	
	
	
	
}
