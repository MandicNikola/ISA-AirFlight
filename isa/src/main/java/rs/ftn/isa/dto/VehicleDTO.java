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
	private double cena;
	private double cenaPopust;
	private int popust;
	private int bodovi;

	
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
	
	public int getBodovi() {
		return bodovi;
	}
	public void setBodovi(int bodovi) {
		this.bodovi = bodovi;
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
	public double getCena() {
		return cena;
	}
	public void setCena(double cena) {
		this.cena = cena;
	}
	public double getCenaPopust() {
		return cenaPopust;
	}
	public void setCenaPopust(double cenaPopust) {
		this.cenaPopust = cenaPopust;
	}
	public int getPopust() {
		return popust;
	}
	public void setPopust(int popust) {
		this.popust = popust;
	}
	@Override
	public String toString() {
		return "VehicleDTO [id=" + id + ", marka=" + marka + ", model=" + model + ", godiste=" + godiste + ", sedista="
				+ sedista + ", kategorija=" + kategorija + ", imapopusta=" + imapopusta + ", cena=" + cena
				+ ", cenaPopust=" + cenaPopust + ", popust=" + popust + "]";
	} 

    
}
