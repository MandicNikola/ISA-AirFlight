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

import rs.ftn.isa.constants.FilijalaConstants;
import rs.ftn.isa.constants.RentACarConstants;
import rs.ftn.isa.model.Filijala;
import rs.ftn.isa.model.RentACar;
import rs.ftn.isa.repository.FilijalaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilijalaServiceTest {


	@Mock
	private FilijalaRepository filijalaRepositoryMock;
	
	@Mock
	private Filijala filMock;
	
	@InjectMocks
	private FilijalaServiceImpl filijalaService;
	
	
	@Test
	public void testFindAll() {
	
		when(filijalaRepositoryMock.findAll()).thenReturn(Arrays.asList(new Filijala(FilijalaConstants.DB_ID, FilijalaConstants.DB_GRAD,FilijalaConstants.DB_ULICA)));
		List<Filijala> filijale = filijalaService.findAll();
		assertThat(filijale).hasSize(1);
		
		verify(filijalaRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(filijalaRepositoryMock);
	}
	
	@Test
	public void testFindById() {
		
		when(filijalaRepositoryMock.findOneById(FilijalaConstants.DB_ID)).thenReturn(new Filijala(FilijalaConstants.DB_ID, FilijalaConstants.DB_GRAD,FilijalaConstants.DB_ULICA));
		Filijala dbFilijala = filijalaService.findFilijalaById(FilijalaConstants.DB_ID);
		
		assertThat(dbFilijala).isNotNull();
		
		assertThat(dbFilijala.getId()).isEqualTo(FilijalaConstants.DB_ID);
		assertThat(dbFilijala.getGrad()).isEqualTo(FilijalaConstants.DB_GRAD);
	    assertThat(dbFilijala.getUlica()).isEqualTo(FilijalaConstants.DB_ULICA);
	    verify(filijalaRepositoryMock, times(1)).findOneById(FilijalaConstants.DB_ID);
        verifyNoMoreInteractions(filijalaRepositoryMock);
	}
	
	@Test
    @Transactional
    @Rollback(true) 
	public void testSaveFilijala() {
		
		when(filijalaRepositoryMock.findAll()).thenReturn(Arrays.asList(new Filijala(FilijalaConstants.DB_ID, FilijalaConstants.DB_GRAD,FilijalaConstants.DB_ULICA)));
		
		Filijala newFilijala = new Filijala(FilijalaConstants.NEW_ID,FilijalaConstants.NEW_GRAD,FilijalaConstants.NEW_ULICA);
		when(filijalaRepositoryMock.save(newFilijala)).thenReturn(newFilijala);
		
		int sizeBeforeAdd = filijalaService.findAll().size();
		
		Filijala dbFilijala = filijalaService.saveFilijala(newFilijala);
		assertThat(dbFilijala).isNotNull(); 
		
		when(filijalaRepositoryMock.findAll()).thenReturn(Arrays.asList(new Filijala(FilijalaConstants.DB_ID, FilijalaConstants.DB_GRAD,FilijalaConstants.DB_ULICA), newFilijala));
		List<Filijala> filijale = filijalaService.findAll();
        assertThat(filijale).hasSize(sizeBeforeAdd + 1);
        dbFilijala = filijale.get(filijale.size() - 1); 
        assertThat(dbFilijala.getGrad()).isEqualTo(FilijalaConstants.NEW_GRAD);
        assertThat(dbFilijala.getUlica()).isEqualTo(FilijalaConstants.NEW_ULICA);
        verify(filijalaRepositoryMock, times(2)).findAll();
        verify(filijalaRepositoryMock, times(1)).save(newFilijala);
        
        verifyNoMoreInteractions(filijalaRepositoryMock);
	}
	
	
	
	
	

}
