package rs.ftn.isa.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import rs.ftn.isa.constants.FilijalaConstants;
import rs.ftn.isa.constants.RentACarConstants;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilijalaControllerTest {

	private static final String URL_PREFIX = "/api/filijale";

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
	public void testGetAllFilijale() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/all")).andExpect(status().isOk())
		.andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(FilijalaConstants.DB_COUNT)))
		.andExpect(jsonPath("$.[*].id").value(hasItem(FilijalaConstants.DB_ID.intValue())))
		.andExpect(jsonPath("$.[*].grad").value(hasItem(FilijalaConstants.DB_GRAD)))
		.andExpect(jsonPath("$.[*].ulica").value(hasItem(FilijalaConstants.DB_ULICA)));
	}
	

	@Test
	public void testGetFilijaleByGrad() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/poGradu/"+FilijalaConstants.DB_GRAD2)).andExpect(status().isOk())
		.andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(FilijalaConstants.DB_COUNT-1)));
	}
	

}
