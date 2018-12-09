package rs.ftn.isa.service;

import java.util.List;
import rs.ftn.isa.model.PricelistHotel;

public interface CijenovnikHotelService {
	PricelistHotel findCijenovnikById(Long id);
	
	List<PricelistHotel> findAll();
	
	PricelistHotel sacuvajCijenovnik(PricelistHotel cijenovnik);
	void removeCijenovnik(Long id);

}
