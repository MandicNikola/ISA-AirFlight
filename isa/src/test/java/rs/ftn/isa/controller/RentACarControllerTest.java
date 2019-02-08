package rs.ftn.isa.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import rs.ftn.isa.TestUtil;
import rs.ftn.isa.constants.FilijalaConstants;
import rs.ftn.isa.constants.RentACarConstants;
import rs.ftn.isa.constants.UserConstants;
import rs.ftn.isa.model.Filijala;
import rs.ftn.isa.model.RentACar;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RentACarControllerTest {
	private static final String URL_PREFIX = "/api/rents";

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));


	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testGetAllRents() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/all")).andExpect(status().isOk())
		.andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(RentACarConstants.DB_COUNT)))
		.andExpect(jsonPath("$.[*].id").value(hasItem(RentACarConstants.DB_ID.intValue())))
		.andExpect(jsonPath("$.[*].naziv").value(hasItem(RentACarConstants.DB_NAZIV)))
		.andExpect(jsonPath("$.[*].adresa").value(hasItem(RentACarConstants.DB_ADRESA)));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSaveRent() throws Exception {
		RentACar newRent = new RentACar(RentACarConstants.DB_NEW_NAZIV, RentACarConstants.DB_NEW_GRAD,RentACarConstants.DB_NEW_ADRESA, RentACarConstants.DB_NEW_OPIS);
				
		String json = TestUtil.json(newRent);
		this.mockMvc.perform(post(URL_PREFIX+ "/newrentacar").contentType(contentType).content(json)).andExpect(status().isCreated());
	}
	
	@Test
	public void testGetRentById() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/vratiRentId/" + RentACarConstants.DB_ID)).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.id").value(RentACarConstants.DB_ID.intValue()))
		.andExpect(jsonPath("$.naziv").value(RentACarConstants.DB_NAZIV))
		.andExpect(jsonPath("$.adresa").value(RentACarConstants.DB_ADRESA));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteRent() throws Exception {
		this.mockMvc.perform(delete(URL_PREFIX + "/obrisiRent/" + RentACarConstants.DB_ID)).andExpect(status().isOk());
	}
	

	@Test
	@Transactional
	@Rollback(true)
	public void testAddFilijala() throws Exception {
		Filijala newFilijala = new Filijala();
		newFilijala.setGrad(FilijalaConstants.NEW_GRAD);
		newFilijala.setUlica(FilijalaConstants.NEW_ULICA);
		
				
		String json = TestUtil.json(newFilijala);
		this.mockMvc.perform(post(URL_PREFIX+ "/postavifilijalu/"+RentACarConstants.DB_UPDATE_ID).contentType(contentType).content(json)).andExpect(status().isOk())
		.andExpect(jsonPath("$.naziv").value(RentACarConstants.DB_NAZIV))
		.andExpect(jsonPath("$.id").value(RentACarConstants.DB_ID.intValue()));
	}
	
	
}
