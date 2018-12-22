package rs.ftn.isa.dto;

import java.util.Date;

public class ReservationHotelDTO {
	private String nazivHotela;
	private int brojSoba;
	private int brojLjudi;
	private Date checkIn;
	private Date checkOut;
	public ReservationHotelDTO() {
		super();
	}
	
	public ReservationHotelDTO(String nazivHotela, int brojSoba, int brojLjudi, Date checkIn, Date checkOut) {
		super();
		this.nazivHotela = nazivHotela;
		this.brojSoba = brojSoba;
		this.brojLjudi = brojLjudi;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
	}
	
	
	public ReservationHotelDTO(int brojSoba, int brojLjudi, Date checkIn, Date checkOut) {
		super();
		this.brojSoba = brojSoba;
		this.brojLjudi = brojLjudi;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
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
	
}
