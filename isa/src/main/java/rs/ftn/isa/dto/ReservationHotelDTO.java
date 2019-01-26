package rs.ftn.isa.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import rs.ftn.isa.model.StatusRezervacije;

public class ReservationHotelDTO {
	private String nazivHotela;
	private int brojSoba;
	private int brojLjudi;
	private int brojKreveta;
	//id rezervacije
	private Long id;
	private Date checkIn;
	private Date checkOut;
	private double cena;
	private boolean ocenjenHotel;
	private StatusRezervacije status;
	
	
	public ReservationHotelDTO() {
		super();
	}
	
	public ReservationHotelDTO(String nazivHotela, Long id, Date checkIn, Date checkOut) {
		super();
		this.nazivHotela = nazivHotela;
		this.id = id;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
	}

	public ReservationHotelDTO(String nazivHotela, int brojSoba, int brojLjudi, Date checkIn, Date checkOut) {
		super();
		this.nazivHotela = nazivHotela;
		this.brojSoba = brojSoba;
		this.brojLjudi = brojLjudi;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
	}

	public ReservationHotelDTO(String nazivHotela, Long id, Date checkIn, Date checkOut, double cena,
			boolean ocenjenHotel, StatusRezervacije status) {
		super();
		this.nazivHotela = nazivHotela;
		this.id = id;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.cena = cena;
		this.ocenjenHotel = ocenjenHotel;
		this.status = status;
	}
	public ReservationHotelDTO(int brojSoba, int brojLjudi, Date checkIn, Date checkOut) {
		super();
		this.brojSoba = brojSoba;
		this.brojLjudi = brojLjudi;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
	}

	public boolean isOcenjenHotel() {
		return ocenjenHotel;
	}

	public void setOcenjenHotel(boolean ocenjenHotel) {
		this.ocenjenHotel = ocenjenHotel;
	}

	public StatusRezervacije getStatus() {
		return status;
	}

	public void setStatus(StatusRezervacije status) {
		this.status = status;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNazivHotela() {
		return nazivHotela;
	}
	public void setNazivHotela(String nazivHotela) {
		this.nazivHotela = nazivHotela;
	}
	public int getBrojSoba() {
		return brojSoba;
	}
	public void setBrojSoba(int brojSoba) {
		this.brojSoba = brojSoba;
	}
	public int getBrojLjudi() {
		return brojLjudi;
	}
	public void setBrojLjudi(int brojLjudi) {
		this.brojLjudi = brojLjudi;
	}
	public Date getCheckIn() {
		return checkIn;
	}
	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}
	public Date getCheckOut() {
		return checkOut;
	}
	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}
    
	public int getBrojKreveta() {
		return brojKreveta;
	}

	public void setBrojKreveta(int brojKreveta) {
		this.brojKreveta = brojKreveta;
	}

	@Override
	public String toString() {
		return "ReservationHotelDTO [nazivHotela=" + nazivHotela + ", brojSoba=" + brojSoba + ", brojLjudi=" + brojLjudi
				+ ", brojKreveta=" + brojKreveta + ", id=" + id + ", checkIn=" + checkIn + ", checkOut=" + checkOut
				+ ", cena=" + cena + ", ocenjenHotel=" + ocenjenHotel + ", status=" + status + "]";
	}


}
