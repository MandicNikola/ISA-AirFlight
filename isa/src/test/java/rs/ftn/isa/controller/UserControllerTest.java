package rs.ftn.isa.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import rs.ftn.isa.TestUtil;
import rs.ftn.isa.constants.UserConstants;
import rs.ftn.isa.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

	
	
	private static final String URL_PREFIX = "/api/korisnici";

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
	public void testGetAllUsers()  {
		try {
			mockMvc.perform(get(URL_PREFIX + "/all")).andExpect(status().isOk())
			.andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(UserConstants.DB_COUNT)))
			.andExpect(jsonPath("$.[*].id").value(hasItem(UserConstants.DB_ID.intValue())))
			.andExpect(jsonPath("$.[*].ime").value(hasItem(UserConstants.DB_IME)))
			.andExpect(jsonPath("$.[*].prezime").value(hasItem(UserConstants.DB_PREZIME)))
			.andExpect(jsonPath("$.[*].mail").value(hasItem(UserConstants.DB_MAIL)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return;
		}
	}
	 
}
