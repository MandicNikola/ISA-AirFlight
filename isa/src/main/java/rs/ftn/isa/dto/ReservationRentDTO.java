package rs.ftn.isa.dto;

import java.util.Date;

public class ReservationRentDTO {
	private String nazivRent;
	private Long rentId;
	private int putnici;
	private Date pickUp;
	private Date dropOff;
	private String startLocation;
	private String endLocation;
	private String tip;
	
	
	public ReservationRentDTO() {
		
	}


	public ReservationRentDTO(Long rentId, int putnici, Date pickUp, Date dropOff, String startLocation,
			String endLocation,String tip) {
		super();
		this.rentId = rentId;
		this.putnici = putnici;
		this.pickUp = pickUp;
		this.dropOff = dropOff;
		this.startLocation = startLocation;
		this.endLocation = endLocation;
		this.tip=tip;
	}


	public ReservationRentDTO(String nazivRent, Long rentId, int putnici, Date pickUp, Date dropOff, String startLocation,
			String endLocation, String tip) {
		super();
		this.nazivRent = nazivRent;
		this.rentId = rentId;
		this.putnici = putnici;
		this.pickUp = pickUp;
		this.dropOff = dropOff;
		this.startLocation = startLocation;
		this.endLocation = endLocation;
		this.tip = tip;
	}


	public String getTip() {
		return tip;
	}


	public void setTip(String tip) {
		this.tip = tip;
	}


	public String getNazivRent() {
		return nazivRent;
	}


	public void setNazivRent(String nazivRent) {
		this.nazivRent = nazivRent;
	}


	public Long getRentId() {
		return rentId;
	}


	public void setRentId(Long rentId) {
		this.rentId = rentId;
	}


	public int getPutnici() {
		return putnici;
	}


	public void setPutnici(int putnici) {
		this.putnici = putnici;
	}


	public Date getPickUp() {
		return pickUp;
	}


	public void setPickUp(Date pickUp) {
		this.pickUp = pickUp;
	}


	public Date getDropOff() {
		return dropOff;
	}


	public void setDropOff(Date dropOff) {
		this.dropOff = dropOff;
	}


	public String getStartLocation() {
		return startLocation;
	}


	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}


	public String getEndLocation() {
		return endLocation;
	}


	public void setEndLocation(String endLocation) {
		this.endLocation = endLocation;
	}
	
	
	
}
