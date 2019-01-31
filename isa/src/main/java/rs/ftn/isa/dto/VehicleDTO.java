package rs.ftn.isa.dto;

import rs.ftn.isa.model.CategoryCar;

public class VehicleDTO {
	private Long id;
	private String marka;
	private String model;
	private int godiste;
	private int sedista;
	private CategoryCar kategorija;
    private boolean imapopusta;
    public VehicleDTO() {
    	super();
    }
	public VehicleDTO(Long id, String marka, String model, int godiste, int sedista, CategoryCar kategorija,
			boolean imapopusta) {
		super();
		this.id = id;
		this.marka = marka;
		this.model = model;
		this.godiste = godiste;
		this.sedista = sedista;
		this.kategorija = kategorija;
		this.imapopusta = imapopusta;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMarka() {
		return marka;
	}
	public void setMarka(String marka) {
		this.marka = marka;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getGodiste() {
		return godiste;
	}
	public void setGodiste(int godiste) {
		this.godiste = godiste;
	}
	public int getSedista() {
		return sedista;
	}
	public void setSedista(int sedista) {
		this.sedista = sedista;
	}
	public CategoryCar getKategorija() {
		return kategorija;
	}
	public void setKategorija(CategoryCar kategorija) {
		this.kategorija = kategorija;
	}
	public boolean isImapopusta() {
		return imapopusta;
	}
	public void setImapopusta(boolean imapopusta) {
		this.imapopusta = imapopusta;
	} 

    
}
