package rs.ftn.isa.dto;

import java.util.ArrayList;

public class RoomDTO {
	private Long id;
	private String tip; 
	private int kreveti;
	private double ocena;
	private String hotel; //hotel kome pripada soba
	private Long idRez;
	private int sprat;
	private int kapacitet;
	private double cijena;
	private String balkon;
	private boolean imapopust;
	private boolean imarez;
	private Long popust;
	private int vrijednostPopusta;
	private int bodoviPopusta;
	
	private ArrayList<String> nazivUsluga;
	private boolean imaNazive;
	public RoomDTO() {}
	
	public RoomDTO(Long id, String tip, double ocena, String hotel, Long idRez) {
		super();
		this.id = id;
		this.tip = tip;
		this.ocena = ocena;
		this.hotel = hotel;
		this.idRez = idRez;
	}
	public RoomDTO(Long id, String tip, int kapacitet, int sprat,boolean imapopust,double ocena) {
		super();
		this.id = id;
		this.tip = tip;
		this.kapacitet = kapacitet;
		this.sprat = sprat;
		this.imapopust = imapopust;
		this.ocena = ocena;
	}
	public RoomDTO(Long id, String tip, double ocena, int sprat, int kapacitet, double cijena, String balkon) {
		super();
		this.id = id;
		this.tip = tip;
		this.ocena = ocena;
		this.sprat = sprat;
		this.kapacitet = kapacitet;
		this.cijena = cijena;
		this.balkon = balkon;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public int getSprat() {
		return sprat;
	}

	public void setSprat(int sprat) {
		this.sprat = sprat;
	}

	public int getKapacitet() {
		return kapacitet;
	}

	public void setKapacitet(int kapacitet) {
		this.kapacitet = kapacitet;
	}

	public boolean isImarez() {
		return imarez;
	}

	public void setImarez(boolean imarez) {
		this.imarez = imarez;
	}

	public double getCijena() {
		return cijena;
	}

	public void setCijena(double cijena) {
		this.cijena = cijena;
	}

	public String getBalkon() {
		return balkon;
	}

	public void setBalkon(String balkon) {
		this.balkon = balkon;
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

	public boolean isImapopust() {
		return imapopust;
	}

	public void setImapopust(boolean imapopust) {
		this.imapopust = imapopust;
	}

	public Long getPopust() {
		return popust;
	}

	public void setPopust(Long popust) {
		this.popust = popust;
	}

	public int getVrijednostPopusta() {
		return vrijednostPopusta;
	}

	public void setVrijednostPopusta(int vrijednostPopusta) {
		this.vrijednostPopusta = vrijednostPopusta;
	}

	public ArrayList<String> getNazivUsluga() {
		return nazivUsluga;
	}

	public void setNazivUsluga(ArrayList<String> nazivUsluga) {
		this.nazivUsluga = nazivUsluga;
	}

	public boolean isImaNazive() {
		return imaNazive;
	}

	public void setImaNazive(boolean imaNazive) {
		this.imaNazive = imaNazive;
	}

	public int getBodoviPopusta() {
		return bodoviPopusta;
	}

	public void setBodoviPopusta(int bodoviPopusta) {
		this.bodoviPopusta = bodoviPopusta;
	}


	
}
