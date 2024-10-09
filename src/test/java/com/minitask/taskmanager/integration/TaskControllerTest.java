package com.minitask.taskmanager.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.minitask.taskmanager.config.Messages;
import com.minitask.taskmanager.model.Task;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

	@Autowired
	MockMvc mockMvc;

	private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
	Task task = new Task();


	@WithMockUser(roles="USER")
	@Test
	@Order(1)
	public void testGetAllTasks() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/all"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$",hasSize(3)));
	}
	@WithMockUser(roles="USER")
	@Test
	public void testInsertTaskWithoutTitleFail() throws Exception {

		task.setTitle("Study Kanji");
		task.setId(4L);
		String json = mapper.writeValueAsString(task);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
						.content(json).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(Matchers.containsString("cannot be null")));
	}

	@WithMockUser(roles="USER")
	@Test
	@Order(2)
	public void testInsertTaskOk() throws Exception {

		task.setTitle("Study Kanji");
		//task.setId(4L);
		task.setTitle("Study Kanji");
		task.setDescription("lets go");
		String json = mapper.writeValueAsString(task);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
						.content(json).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", Matchers.equalTo(4)))
				.andExpect(jsonPath("$.title", Matchers.equalTo("Study Kanji")))
				.andExpect(jsonPath("$.description", Matchers.equalTo("lets go")));
	}

	@WithMockUser(roles="USER")
	@Test
	public void testGetTaskById() throws Exception{

		mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/{id}","2")
						.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title",Matchers.equalTo("autoTask 2"))).andReturn();
	}

	@WithMockUser(roles="USER")
	@Test
	public void testUpdateTaskNoDescriptionReturnsException() throws Exception {
		Task updatedTask = new Task();
		updatedTask.setId(4L);
		updatedTask.setTitle("Study Kanji");
		updatedTask.setDueDate(LocalDate.of(2024, 10, 30));

		String json = mapper.writeValueAsString(updatedTask);

		mockMvc.perform(MockMvcRequestBuilders.put("/api/tasks")
						.contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
						.content(json))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(Matchers.containsString(Messages.ERROR_NOT_EMPTY)));

	}

	@WithMockUser(roles="USER")
	@Test
	public void testUpdateTaskOK() throws Exception {

		Task updatedTask = new Task();
		updatedTask.setId(1L);
		updatedTask.setTitle("autoTask 1");
		updatedTask.setDescription("first 200");
		updatedTask.setDueDate(LocalDate.of(2024, 10, 30));

		String json = mapper.writeValueAsString(updatedTask);

		mockMvc.perform(MockMvcRequestBuilders.put("/api/tasks")
						.contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
						.content(json))
				.andExpect(status().isAccepted())
				.andExpect(jsonPath("$.title",Matchers.equalTo("autoTask 1")))
				.andExpect(jsonPath("$.description",Matchers.equalTo("first 200")))
				.andExpect(jsonPath("$.dueDate",Matchers.equalTo("2024-10-30")));
	}


	@Test
	@Order(3)
	public void testDeteleTaskNoRoleFails() throws Exception{
		String json = mapper.writeValueAsString(task);
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/tasks").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
						.content(json))
				.andExpect(status().isUnauthorized())
				.andExpect(content().string(""))
				.andReturn();
	}

	@WithMockUser(username="ADMIN", roles="ADMIN")
	@Test
	@Order(4)
	public void testDeteleTaskWithRoleOK() throws Exception{
		String json = mapper.writeValueAsString(task);
		MvcResult requestResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/tasks").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
						.content(json))
				.andExpect(status().isOk())
				.andReturn();

		String result = requestResult.getResponse().getContentAsString();
		assertEquals("Task is deleted",result);
	}

	@WithMockUser(roles="USER")
	@Test
	@Order(5)
	public void testGetAllPagedAndSorted() throws Exception{

		mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/allPagAndSort")
						.param("sort","id,desc"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content[0].id",Matchers.equalTo(3)))
				.andExpect(jsonPath("$.content[0].title").value("autoTask 3"))
				.andExpect(jsonPath("$.content[0].description").value("c desc"));

	}
}
