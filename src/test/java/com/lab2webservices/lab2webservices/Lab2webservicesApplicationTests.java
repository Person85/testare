package com.lab2webservices.lab2webservices;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@WebMvcTest(PhoneController.class)
@Slf4j
@Import({PhoneDataModelAssembler.class})
class Lab2webservicesApplicationTests {
	@Autowired
	MockMvc mockMvc;
	@MockBean
	PhoneRepository repository;
	@BeforeEach
	void setup(){
		when(repository.findAll()).thenReturn(List.of(new Phone(1, "Iphone"), new Phone(2, "Samsung")));
		when(repository.findById((long) 1)).thenReturn(Optional.of(new Phone(1, "Iphone")));
		when(repository.findByPhoneName("Iphone X")).thenReturn(Optional.of(new Phone(1, "Iphone")));
		when(repository.save(any(Phone.class))).thenAnswer(invocationOnMock -> {
			Object[] args = invocationOnMock.getArguments();
			var p = (Phone) args[0];
			return new Phone(1, p.getPhoneName());
		});
	}

	@Test
	void getAllReturnsListOfAllPhones() throws Exception {
		mockMvc.perform(
				get("/api/v1/phones").contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("_embedded.phoneList[1]._links.self.href", is("http://localhost/api/v1/phones/Samsung")))
				.andExpect(jsonPath("_embedded.phoneList[1].phoneName", is("Samsung")));
	}

	@Test
	@DisplayName("Calls Get method with url /api/v1/phones/1")
	void getOnePhoneWithValidIdOne() throws Exception {
		mockMvc.perform(
				get("/api/v1/phones/1").accept("application/json"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("_links.self.href", is("http://localhost/api/v1/phones/Iphone")));
	}

	@Test
	@DisplayName("Calls Get method with invalid id url /api/v1/phones/3")
	void getOnePhoneWithInValidIdThree() throws Exception {
		mockMvc.perform(
				get("/api/phones/3").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void addNewPhoneWithPostReturnsCreatedPhone() throws Exception {
		mockMvc.perform(
				post("/api/v1/phones/")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"id\":0,\"name\":\"Iphone\"}"))
				.andExpect(status().isCreated());
	}
}
