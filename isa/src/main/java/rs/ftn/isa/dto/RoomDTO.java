package rs.ftn.isa.dto;

public class RoomDTO {
	private Long id;
	private String tip; 
	private int kreveti;
	private double ocena;
	private String hotel; //hotel kome pripada soba
	private Long idRez;
	
	public RoomDTO() {}
	
	public RoomDTO(Long id, String tip, double ocena, String hotel, Long idRez) {
		super();
		this.id = id;
		this.tip = tip;
		this.ocena = ocena;
		this.hotel = hotel;
		this.idRez = idRez;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public int getKreveti() {
		return kreveti;
	}
	public void setKreveti(int kreveti) {
		this.kreveti = kreveti;
	}
	public double getOcena() {
		return ocena;
	}
	public void setOcena(double ocjena) {
		this.ocena = ocjena;
	}
	public String getHotel() {
		return hotel;
	}
	public void setHotel(String hotel) {
		this.hotel = hotel;
	}
	public Long getIdRez() {
		return idRez;
	}
	public void setIdRez(Long idRez) {
		this.idRez = idRez;
	}
	
	
	
	
}
