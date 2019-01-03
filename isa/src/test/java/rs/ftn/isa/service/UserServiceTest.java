package rs.ftn.isa.service;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ftn.isa.constants.*;
import rs.ftn.isa.model.User;
import rs.ftn.isa.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	@Mock
	private UserRepository userRepositoryMock;
	
	@Mock
	private User userMock;
	
	@InjectMocks
	private UserServiceImpl userService;
	
	@Test
	public void testFindAll() {
	
		/*
		Kako za testove koristimo mokovane repository objekte moramo da definišemo šta će se desiti kada se
		pozove određena metoda kombinacijom "when"-"then" Mockito metoda.
		 */
		when(userRepositoryMock.findAll()).thenReturn(Arrays.asList(new User(UserConstants.DB_NEW_ID, UserConstants.DB_NEW_IME, UserConstants.DB_NEW_PREZIME, UserConstants.DB_NEW_MAIL, UserConstants.DB_NEW_TELEFON, UserConstants.DB_NEW_GRAD)));
		List<User> users = userService.findAll();
		assertThat(users).hasSize(1);
		
		/*
		Možemo verifikovati ponašanje mokovanih objekata pozivanjem verify* metoda.
		 */
		verify(userRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(userRepositoryMock);
	}
	

}
