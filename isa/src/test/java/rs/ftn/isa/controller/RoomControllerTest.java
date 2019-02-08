package rs.ftn.isa.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import rs.ftn.isa.constants.RoomConstants;
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoomControllerTest {
	private static final String URL_PREFIX = "/api/rooms";

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
	public void testGetAllRooms() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/all")).andExpect(status().isOk())
		.andExpect(content().contentType(contentType)).andExpect(jsonPath("$", hasSize(RoomConstants.DB_COUNT)))
		.andExpect(jsonPath("$.[*].id").value(hasItem(RoomConstants.DB_ID.intValue())))
		.andExpect(jsonPath("$.[*].sprat").value(hasItem(RoomConstants.DB_SPRAT)))
		.andExpect(jsonPath("$.[*].tip").value(hasItem(RoomConstants.DB_TIP)));
	}
	@Test
	public void testGetRoomById() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/vratiSobu/" + RoomConstants.DB_ID)).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.id").value(RoomConstants.DB_ID.intValue()))
		.andExpect(jsonPath("$.sprat").value(RoomConstants.DB_SPRAT))
		.andExpect(jsonPath("$.balkon").value(RoomConstants.DB_BALKON));
	}	

}
