package rs.ftn.isa.service;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import rs.ftn.isa.model.RentACar;
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

	
}
