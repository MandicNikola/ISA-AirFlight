package rs.ftn.isa.dto;

import java.util.ArrayList;
import java.util.Date;

public class ChartDTO {
	private	ArrayList<Date> datum;
	private ArrayList<Integer>  broj;

	public ChartDTO() {}

	
	public ChartDTO(ArrayList<Date> datum, ArrayList<Integer> broj) {
		super();
		this.datum = datum;
		this.broj = broj;
	}


	public ArrayList<Date> getDatum() {
		return datum;
	}

	public void setDatum(ArrayList<Date> datum) {
		this.datum = datum;
	}

	public ArrayList<Integer> getBroj() {
		return broj;
	}

	public void setBroj(ArrayList<Integer> broj) {
		this.broj = broj;
	}

	
}
