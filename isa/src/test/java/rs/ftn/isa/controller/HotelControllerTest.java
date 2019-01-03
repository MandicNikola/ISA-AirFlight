package rs.ftn.isa.controller;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

import rs.ftn.isa.TestUtil;
import rs.ftn.isa.constants.*;
import rs.ftn.isa.dto.HotelDTO;
import rs.ftn.isa.model.Hotel;
@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelControllerTest {
	private static final String URL_PREFIX = "/api/hoteli";

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
	public void testGetAllHotels() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/all")).andExpect(status().isOk())
		.andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(HotelConstants.DB_COUNT)))
		.andExpect(jsonPath("$.[*].id").value(hasItem(HotelConstants.DB_ID.intValue())))
		.andExpect(jsonPath("$.[*].naziv").value(hasItem(HotelConstants.DB_NAZIV)))
		.andExpect(jsonPath("$.[*].adresa").value(hasItem(HotelConstants.DB_ADRESA)))
		.andExpect(jsonPath("$.[*].opis").value(hasItem(HotelConstants.DB_OPIS)));
	}

	@Test
	public void testGetHotelById() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/findById/" + HotelConstants.DB_ID)).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.id").value(HotelConstants.DB_ID.intValue()))
		.andExpect(jsonPath("$.naziv").value(HotelConstants.DB_NAZIV))
		.andExpect(jsonPath("$.adresa").value(HotelConstants.DB_ADRESA))
		.andExpect(jsonPath("$.opis").value(HotelConstants.DB_OPIS));
	}
	@Test
	@Transactional
	@Rollback(true)
	public void testSaveHotel() throws Exception {
		Hotel hotel= new Hotel();
		hotel.setNaziv(HotelConstants.DB_NAZIV);
		hotel.setAdresa(HotelConstants.DB_ADRESA);
		hotel.setOpis(HotelConstants.DB_OPIS);

		String json = TestUtil.json(hotel);
		this.mockMvc.perform(post(URL_PREFIX+"/newhotel").contentType(contentType).content(json)).andExpect(status().isCreated());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteStudent() throws Exception {
		this.mockMvc.perform(post(URL_PREFIX + "/obrisiHotel/" + HotelConstants.DB_ID)).andExpect(status().isOk());
	}
	

}
