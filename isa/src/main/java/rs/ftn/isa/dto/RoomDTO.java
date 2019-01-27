package rs.ftn.isa.dto;

public class RoomDTO {
	private Long id;
	private String tip; 
	private int kreveti;
	private double ocjena;
	private String hotel; //hotel kome pripada soba
	private Long idRez;
	
	public RoomDTO() {}
	
	public RoomDTO(Long id, String tip, int kreveti, double ocjena, String hotel, Long idRez) {
		super();
		this.id = id;
		this.tip = tip;
		this.kreveti = kreveti;
		this.ocjena = ocjena;
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
	public double getOcjena() {
		return ocjena;
	}
	public void setOcjena(double ocjena) {
		this.ocjena = ocjena;
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
