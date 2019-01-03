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

import rs.ftn.isa.constants.RentACarConstants;
import rs.ftn.isa.constants.UserConstants;
import rs.ftn.isa.model.RentACar;
import rs.ftn.isa.model.User;
import rs.ftn.isa.repository.RentACarRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RentACarServiceTest {

	@Mock
	private RentACarRepository rentRepositoryMock;
	
	@Mock
	private RentACar rentMock;
	
	@InjectMocks
	private RentACarServiceImpl rentService;

	@Test
	public void testFindAll() {
	
		when(rentRepositoryMock.findAll()).thenReturn(Arrays.asList(new RentACar(RentACarConstants.DB_ID, RentACarConstants.DB_NAZIV, RentACarConstants.DB_ADRESA, RentACarConstants.DB_OPIS)));
		List<RentACar> rents = rentService.findAll();
		assertThat(rents).hasSize(1);
		
		verify(rentRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(rentRepositoryMock);
	}
	
}