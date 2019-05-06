package com.arved;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.arved.controller.UserController;
import com.arved.model.User;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BeBeApplicationTests {

	@Autowired
	private UserController controller;

	@Autowired
	private MockMvc mvc;

	private ObjectMapper mapper = new ObjectMapper();

	@Test
	public void contextLoads() {
		assertThat(mvc).isNotNull();
		assertThat(controller).isNotNull();
	}

	/**
	 * Check if static list is size 6 and first element is John Lennon
	 * 
	 * @throws Exception
	 */
	@Test
	public void checkAcheckUser() throws Exception {
		MvcResult result = invokeAllusers().andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(6)))
				.andExpect(jsonPath("$[0].firstName", is("John"))).andExpect(jsonPath("$[0].lastName", is("Lennon")))
				.andReturn();
	}

	/**
	 * Create new user and except return object increase to size 7 And last element
	 * is with firstname Cont, lastName Tre
	 * 
	 * @throws Exception
	 */
	@Test
	public void checkBaddUser() throws Exception {
		byte[] userOfJson = toJson(new User(99, "Cont", "Tre", "Pro", 11));
		MvcResult results = addUser(userOfJson).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(7)))
				.andExpect(jsonPath("$[6].firstName", is("Cont"))).andExpect(jsonPath("$[6].lastName", is("Tre")))
				.andReturn();
	}

	/**
	 * Update first element to Name -> John Gerry and check changes
	 * 
	 * @throws Exception
	 */
	@Test
	public void checkCupdateUser() throws Exception {
		byte[] userOfJson = toJson(new User(1, "John", "Gerry", "Boston", 22));
		MvcResult results = updateUser(userOfJson).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].firstName", is("John"))).andExpect(jsonPath("$[0].lastName", is("Gerry")))
				.andReturn();

	}

	/**
	 * Delete ID nr 3 from object
	 * 
	 * @throws Exception
	 */
	@Test
	public void checkDdeleteUser() throws Exception {
		int id = 3;

		// before
		MvcResult resultBefore = invokeAllusers().andExpect(status().isOk()).andExpect(jsonPath("$[2].id", is(3)))
				.andReturn();

		// after removing ID 3
		MvcResult results = deleteUser(id).andExpect(status().isOk()).andExpect(jsonPath("$[2].id", is(4))).andReturn();
	}

	private ResultActions addUser(byte[] heroJson) throws Exception {
		return mvc.perform(post("/adduser").content(heroJson).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
	}

	private ResultActions invokeAllusers() throws Exception {
		return mvc.perform(get("/getusers").accept(MediaType.APPLICATION_JSON));
	}

	private ResultActions updateUser(byte[] heroJson) throws Exception {
		return mvc.perform(put("/updateuser").content(heroJson).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
	}

	private ResultActions deleteUser(int id) throws Exception {
		return mvc.perform(delete("/deleteuser?id=" + id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
	}

	<T> T fromJsonResult(MvcResult result, Class<T> tClass) throws Exception {
		return this.mapper.readValue(result.getResponse().getContentAsString(), tClass);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

	private byte[] toJson(Object object) throws Exception {
		return this.mapper.writeValueAsString(object).getBytes();
	}
}
