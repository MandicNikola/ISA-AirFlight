package rs.ftn.isa.dto;

import java.util.Date;

public class ChartDTO implements Comparable<ChartDTO> {
	private	Date datum;
	private int  broj;

	public ChartDTO() {}

	public ChartDTO(Date datum, int broj) {
		super();
		this.datum = datum;
		this.broj = broj;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public int getBroj() {
		return broj;
	}

	public void setBroj(int broj) {
		this.broj = broj;
	}

	@Override
	public int compareTo(ChartDTO o) {
		// TODO Auto-generated method stub
	    return getDatum().compareTo(o.getDatum());
	}

	@Override
	public String toString() {
		return "ChartDTO [datum=" + datum + ", broj=" + broj + "]";
	}
	
	
}
