package rs.ftn.isa.dto;

public class DiscountDTO {

	Long idPopusta;
	int bodovi;
	int popust;
	
	
	public DiscountDTO()
	{
		
	}


	public Long getIdPopusta() {
		return idPopusta;
	}


	public void setIdPopusta(Long idPopusta) {
		this.idPopusta = idPopusta;
	}


	public int getBodovi() {
		return bodovi;
	}


	public void setBodovi(int bodovi) {
		this.bodovi = bodovi;
	}


	public int getPopust() {
		return popust;
	}


	public void setPopust(int popust) {
		this.popust = popust;
	}
	
	
	
}
