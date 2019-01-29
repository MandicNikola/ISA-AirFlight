package rs.ftn.isa.dto;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class FlightDTO {

	
	
	private Long idAviona;
	private Long idKompanije;
	private String vremePoletanja;
	private String vremeSletanja;
	private double vreme;	
	private double duzina;
	private double cena;

	//**sva presedanja koja mi se desavaju u letu
	private ArrayList<Long> presedanja = new ArrayList<Long>();
	private Long lokacijaPoletanja;
	private Long lokacijaSletanja;
	
	
	public FlightDTO() {
		super();
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
	
	
	
	
	
	
}
