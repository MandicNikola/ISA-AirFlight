package rs.ftn.isa.service;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
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
	
	@Test
    @Transactional
    @Rollback(true) //it can be omitted because it is true by default
	public void testAddRent() {
		
		when(rentRepositoryMock.findAll()).thenReturn(Arrays.asList(new RentACar(RentACarConstants.DB_ID, RentACarConstants.DB_NAZIV, RentACarConstants.DB_ADRESA, RentACarConstants.DB_OPIS)));
		
		//rent servis koji treba da se doda
		RentACar newRent = new RentACar(RentACarConstants.DB_NEW_NAZIV, RentACarConstants.DB_NEW_ADRESA, RentACarConstants.DB_NEW_OPIS);
		//kad se pozove save sacuvaj novi rent
		when(rentRepositoryMock.save(newRent)).thenReturn(newRent);
		
		int sizeBeforeAdd = rentService.findAll().size();
		
		RentACar dbRent = rentService.saveRentACar(newRent);
		assertThat(dbRent).isNotNull(); //uspesno sacuvano vozilo
		
		when(rentRepositoryMock.findAll()).thenReturn(Arrays.asList(new RentACar(RentACarConstants.DB_ID, RentACarConstants.DB_NAZIV, RentACarConstants.DB_ADRESA, RentACarConstants.DB_OPIS), newRent));
		// Validate that new Rent is in the database
        List<RentACar> rents = rentService.findAll();
        assertThat(rents).hasSize(sizeBeforeAdd + 1);
        dbRent = rents.get(rents.size() - 1); //get last rent
        assertThat(dbRent.getNaziv()).isEqualTo(RentACarConstants.DB_NEW_NAZIV);
        assertThat(dbRent.getAdresa()).isEqualTo(RentACarConstants.DB_NEW_ADRESA);
        assertThat(dbRent.getOpis()).isEqualTo(RentACarConstants.DB_NEW_OPIS);
        verify(rentRepositoryMock, times(2)).findAll();
        verify(rentRepositoryMock, times(1)).save(newRent);
        verifyNoMoreInteractions(rentRepositoryMock);
	}
}