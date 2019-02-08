package rs.ftn.isa.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import rs.ftn.isa.model.StatusRezervacije;

public class ReservationTicketDTO {

		private String nazivKompanije;
		private String klasa;
		private Date datumPoletanja;
		private Date datumSletanja;
		private String mestoSletanja;
		private String mestoPoletanja;
		private double cena;
		private boolean ocenjenaKompanija;
		private boolean ocenjenLet;
		private StatusRezervacije status;
		private Long id;
		private Long idKompanije;
		private Long idLet;

		public ReservationTicketDTO() {}
		
		
		public ReservationTicketDTO(StatusRezervacije status, Long id) {
			super();
			this.status = status;
			this.id = id;
		}


		public String getNazivKompanije() {
			return nazivKompanije;
		}
		public void setNazivKompanije(String nazivKompanije) {
			this.nazivKompanije = nazivKompanije;
		}
		public String getKlasa() {
			return klasa;
		}
		public void setKlasa(String klasa) {
			this.klasa = klasa;
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
		public String getMestoSletanja() {
			return mestoSletanja;
		}
		public void setMestoSletanja(String mestoSletanja) {
			this.mestoSletanja = mestoSletanja;
		}
		public String getMestoPoletanja() {
			return mestoPoletanja;
		}
		public void setMestoPoletanja(String mestoPoletanja) {
			this.mestoPoletanja = mestoPoletanja;
		}


		public double getCena() {
			return cena;
		}


		public void setCena(double cena) {
			this.cena = cena;
		}




		public boolean isOcenjenaKompanija() {
			return ocenjenaKompanija;
		}


		public void setOcenjenaKompanija(boolean ocenjenaKompanija) {
			this.ocenjenaKompanija = ocenjenaKompanija;
		}


		public boolean isOcenjenLet() {
			return ocenjenLet;
		}


		public void setOcenjenLet(boolean ocenjenLet) {
			this.ocenjenLet = ocenjenLet;
		}


		public StatusRezervacije getStatus() {
			return status;
		}


		public void setStatus(StatusRezervacije status) {
			this.status = status;
		}


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public Long getIdKompanije() {
			return idKompanije;
		}


		public void setIdKompanije(Long idKompanije) {
			this.idKompanije = idKompanije;
		}


		public Long getIdLet() {
			return idLet;
		}


		public void setIdLet(Long idLet) {
			this.idLet = idLet;
		}
		
		
		

}
