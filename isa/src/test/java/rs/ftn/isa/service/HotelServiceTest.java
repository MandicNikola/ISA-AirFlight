package rs.ftn.isa.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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
		when(hotelRepositoryMock.findAll()).thenReturn(Arrays.asList(new Hotel(HotelConstants.NEW_ID, HotelConstants.NEW_NAZIV, HotelConstants.NEW_ADRESA, HotelConstants.NEW_OPIS)));
		List<Hotel> hotels = hotelService.findAll();
		assertThat(hotels).hasSize(1); //tvrdimo da hoteli zadovoljavaju uslov da im je size 1
		
		/*
		Možemo verifikovati ponašanje mokovanih objekata pozivanjem verify* metoda.
		 */
		verify(hotelRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(hotelRepositoryMock);
	}	

	@Test 
	public void testFindOneById() {
		
		when(hotelRepositoryMock.findOneById(HotelConstants.DB_ID)).thenReturn(hotelMock);
		Hotel dbHotel = hotelService.findHotelById(HotelConstants.DB_ID);
		assertEquals(hotelMock, dbHotel);
        verify(hotelRepositoryMock, times(1)).findOneById(HotelConstants.DB_ID);
        verifyNoMoreInteractions(hotelRepositoryMock);
	}	
	
	@Test
    @Transactional
    @Rollback(true) //it can be omitted because it is true by default
	public void testAdd() {
		
		when(hotelRepositoryMock.findAll()).thenReturn(Arrays.asList(new Hotel(HotelConstants.DB_ID, HotelConstants.DB_NAZIV, HotelConstants.DB_ADRESA, HotelConstants.DB_OPIS)));
		Hotel hotel = new Hotel();
		hotel.setAdresa(HotelConstants.NEW_ADRESA);
		hotel.setOpis(HotelConstants.NEW_OPIS);
		hotel.setNaziv(HotelConstants.NEW_NAZIV);
		
		when(hotelRepositoryMock.save(hotel)).thenReturn(hotel);
		
		int dbSizeBeforeAdd = hotelService.findAll().size();
		
		Hotel dbHotel;
		try {
			dbHotel = hotelService.saveHotel(hotel);
			//pojma nemam???
			assertThat(dbHotel).isNotNull();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		when(hotelRepositoryMock.findAll()).thenReturn(Arrays.asList(new Hotel(HotelConstants.DB_ID, HotelConstants.DB_NAZIV, HotelConstants.DB_ADRESA, HotelConstants.DB_OPIS), hotel));
		// Validate that new student is in the database
        List<Hotel> hoteli = hotelService.findAll();
        assertThat(hoteli).hasSize(dbSizeBeforeAdd + 1);
        dbHotel = hoteli.get(hoteli.size() - 1); //get last student
        assertThat(dbHotel.getNaziv()).isEqualTo(HotelConstants.NEW_NAZIV);
        assertThat(dbHotel.getOpis()).isEqualTo(HotelConstants.NEW_OPIS);
        assertThat(dbHotel.getAdresa()).isEqualTo(HotelConstants.NEW_ADRESA);
        verify(hotelRepositoryMock, times(2)).findAll();
        verify(hotelRepositoryMock, times(1)).save(hotel);
        verifyNoMoreInteractions(hotelRepositoryMock);
	}
	@Test
	@Transactional
	@Rollback(true)
	public void testRemove() {
		
		when(hotelRepositoryMock.findAll()).thenReturn(Arrays.asList(new Hotel(HotelConstants.DB_ID, HotelConstants.DB_NAZIV, HotelConstants.DB_ADRESA, HotelConstants.DB_OPIS),new Hotel(HotelConstants.NEW_ID, HotelConstants.NEW_NAZIV, HotelConstants.NEW_ADRESA, HotelConstants.NEW_OPIS)));
		int dbSizeBeforeRemove = hotelService.findAll().size();
		doNothing().when(hotelRepositoryMock).deleteById(HotelConstants.DB_ID_TO_DELETE);
		hotelService.removeHotel(HotelConstants.DB_ID_TO_DELETE);
		
		when(hotelRepositoryMock.findAll()).thenReturn(Arrays.asList(new Hotel(HotelConstants.DB_ID, HotelConstants.DB_NAZIV, HotelConstants.DB_ADRESA, HotelConstants.DB_OPIS)));
		List<Hotel> hoteli = hotelService.findAll();
		assertThat(hoteli).hasSize(dbSizeBeforeRemove - 1);
		
		when(hotelRepositoryMock.findById(HotelConstants.DB_ID_TO_DELETE)).thenReturn(null);
		Hotel dbHotel = hotelService.findHotelById(HotelConstants.DB_ID_TO_DELETE);
		assertThat(dbHotel).isNull();
		verify(hotelRepositoryMock, times(1)).deleteById(HotelConstants.DB_ID_TO_DELETE);
		verify(hotelRepositoryMock, times(2)).findAll();
        verify(hotelRepositoryMock, times(1)).findOneById(HotelConstants.DB_ID_TO_DELETE);
        verifyNoMoreInteractions(hotelRepositoryMock);
	}
	
}
