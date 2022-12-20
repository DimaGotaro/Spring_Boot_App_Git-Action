package com.example.demo.integration;

import com.example.demo.controllers.Demo_controller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LoginTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private Demo_controller demo_controller;

	@Test
	public void contextLoads() { }

	@Test
	public void contextLoads2() {
		assertThat(demo_controller).isNotNull();
	}

	@Test
	public void test1() throws Exception {
		this.mockMvc.perform(get("/rest/c"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(content().string(containsString("Hello, Priblyda!")));
	}

	@Test
	public void test2() throws Exception {
		this.mockMvc.perform(get("/"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Sweater")))
				.andExpect(content().string(containsString("Hello World!")));
	}

	@Test
	public void test3login() throws Exception {
		this.mockMvc.perform(get("/all"))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"));
	}

	@Test
	public void test4loginCorrect() throws Exception {
		RequestBuilder requestBuilder = post("/login")
				.param("username", " ")
				.param("password", " ");
		this.mockMvc.perform(requestBuilder)
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/login?error"));
	}

	@Test
	public void test5loginCorrect() throws Exception {
		RequestBuilder requestBuilder = post("/login")
				.param("username", "Goga")
				.param("password", "a");
		this.mockMvc.perform(requestBuilder)
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"));
	}

	@Test
	public void test6Registration() throws Exception {
		this.mockMvc.perform(post("/regis")
				.param("username", "")
				.param("password", ""))
				.andDo(print())
				.andExpect(status().isOk()) // 200 статус, всё работает
				.andExpect(content().string(containsString("Поле должно содержать от 1 до 10 знаков!")))
				.andExpect(content().string(containsString("Поле не должно быть пустым!")));
	}

	@Test
	public void test7Registration() throws Exception {
		this.mockMvc.perform(post("/regis")
				.param("username", " ")
				.param("password", " "))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Поле не может содержать только пробелы!")));
	}

	@Test
	public void test8Registration() throws Exception {
		this.mockMvc.perform(post("/regis")
				.param("username", "Goga")
				.param("password", "a"))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/regis?messag=%D0%9F%D0%BE%D0%BB%D1" +
						"%8C%D0%B7%D0%BE%D0%B2%D0%B0%D1%82%D0%B5%D0%BB%D1%8C+" +
						"%D0%B7%D0%B0%D0%BD%D1%8F%D1%82%21"));
	}
}















