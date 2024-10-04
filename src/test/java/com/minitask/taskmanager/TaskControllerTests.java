package com.minitask.taskmanager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.minitask.taskmanager.model.Task;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTests {

	@Autowired
	MockMvc mockMvc;

	private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
	Task task = new Task();

	@Test
	void contextLoads() {
	}

	@Test
	@Order(1)
	public void testGetAllTasks() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/all"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$",hasSize(0)));
	}

	@Test
	@Order(2)
	public void testInsertTask() throws Exception {

		task.setTitle("Study Kanji");
		task.setId(1L);
		String json = mapper.writeValueAsString(task);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(json).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", Matchers.equalTo(1)))
				.andExpect(jsonPath("$.title", Matchers.equalTo("Study Kanji")));
	}

	@Test
	@Order(3)
	public void testGetTaskById() throws Exception{

		mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/{id}","1")
				.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title",Matchers.equalTo("Study Kanji"))).andReturn();
	}

	@Test
	@Order(4)
	public void testUpdateTask() throws Exception {

		Task updatedTask = new Task();
		updatedTask.setId(1L);
		updatedTask.setTitle("Study Kanji");
		updatedTask.setDueDate(LocalDate.of(2024, 10, 30));

		String json = mapper.writeValueAsString(updatedTask);

		mockMvc.perform(MockMvcRequestBuilders.put("/api/tasks")
				.contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
						.content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title",Matchers.equalTo("Study Kanji")))
				.andExpect(jsonPath("$.dueDate",Matchers.equalTo("2024-10-30")));
	}

	@Test
	@Order(5)
	public void testDeteleTask() throws Exception{
		String json = mapper.writeValueAsString(task);
		MvcResult requestResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/tasks").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
						.content(json))
				.andExpect(status().isOk())
				.andReturn();

		String result = requestResult.getResponse().getContentAsString();
		assertEquals("Task is deleted",result);
	}

	@Test
	@Order(6)
	public void testGetAllPagedAndSorted() throws Exception{

		mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/allPagAndSort")
						.param("sort","id,desc"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content[0].id",Matchers.equalTo(3)))
				.andExpect(jsonPath("$.content[0].title").value("autoTask 3"))
				.andExpect(jsonPath("$.content[0].description").value("c desc"));

	}
}
