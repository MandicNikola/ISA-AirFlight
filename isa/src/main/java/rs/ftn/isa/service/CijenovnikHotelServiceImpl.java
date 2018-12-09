package rs.ftn.isa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ftn.isa.repository.CijenovnikHotelRepository;
@Service
public class CijenovnikHotelServiceImpl implements CijenovnikHotelService {
	
	
	
	@Autowired
	private CijenovnikHotelRepository cijenovnikRepozitorijum;
	
	
}
