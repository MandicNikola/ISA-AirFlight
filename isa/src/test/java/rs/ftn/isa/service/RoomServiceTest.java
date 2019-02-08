package rs.ftn.isa.service;

import static org.assertj.core.api.Assertions.assertThat;
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

import rs.ftn.isa.constants.HotelConstants;
import rs.ftn.isa.constants.RoomConstants;
import rs.ftn.isa.model.Hotel;
import rs.ftn.isa.model.Room;
import rs.ftn.isa.repository.HotelRepository;
import rs.ftn.isa.repository.RoomRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoomServiceTest {
	@Mock
	private RoomRepository roomRepositoryMock;
	
	@Mock
	private Room roomMock;
	
	@InjectMocks
	private RoomServiceImp roomService;

	@Test
	public void testFindAll() {
	
		/*
		Kako za testove koristimo mokovane repository objekte moramo da definišemo šta će se desiti kada se
		pozove određena metoda kombinacijom "when"-"then" Mockito metoda.
		 */
		//(String tip, int kreveti, int sprat,int kapacitet,String balkon)
		when(roomRepositoryMock.findAll()).thenReturn(Arrays.asList(new Room(RoomConstants.NEW_TIP, RoomConstants.NEW_SPRAT,RoomConstants.NEW_SPRAT,RoomConstants.NEW_SPRAT,RoomConstants.NEW_BALKON)));
		List<Room> rooms = roomService.findAll();
		assertThat(rooms).hasSize(1); 
		/*
		Možemo verifikovati ponašanje mokovanih objekata pozivanjem verify* metoda.
		 */
		verify(roomRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(roomRepositoryMock);
	}	
}
