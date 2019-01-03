package rs.ftn.isa.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import rs.ftn.isa.model.Hotel;
import rs.ftn.isa.repository.HotelRepository;
import rs.ftn.isa.constants.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelServiceTest {
	@Mock
	private HotelRepository hotelRepositoryMock;
	
	@Mock
	private Hotel hotelMock;
	
	@InjectMocks
	private HotelServiceImpl hotelService;

	@Test
	public void testFindAll() {
	
		/*
		Kako za testove koristimo mokovane repository objekte moramo da definišemo šta će se desiti kada se
		pozove određena metoda kombinacijom "when"-"then" Mockito metoda.
		 */
		when(hotelRepositoryMock.findAll()).thenReturn(Arrays.asList(new Hotel(HotelConstants.DB_ID, HotelConstants.DB_NAZIV, HotelConstants.DB_ADRESA, HotelConstants.DB_OPIS)));
		List<Hotel> hotels = hotelService.findAll();
		assertThat(hotels).hasSize(1); //tvrdimo da hoteli zadovoljavaju uslov da im je size 1
		
		/*
		Možemo verifikovati ponašanje mokovanih objekata pozivanjem verify* metoda.
		 */
		verify(hotelRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(hotelRepositoryMock);
	}	

}
