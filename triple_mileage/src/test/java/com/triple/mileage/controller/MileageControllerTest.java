package com.triple.mileage.controller;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.triple.mileage.service.MileageService;
import com.triple.mileage.service.ReviewService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MileageControllerTest {
	
	private MockMvc mvc;
	@MockBean private ReviewService reviewSerice;
	@MockBean private MileageService mileagSerce;
	
	@BeforeEach
	public void setup(){
		mvc = MockMvcBuilders.standaloneSetup(new MileageController(reviewSerice, mileagSerce))
				.addFilters(new CharacterEncodingFilter("UTF-8", true))
				.build();
	}

	@Test
	void testReviewEvents() {
		
	}

	@Test
	void testGetMileages() {
		
		String testUserId = "435o4rg-45jkj-345jk43-j435k4j3k54j";
		
		try {
			final ResultActions actions = mvc.perform(get("/mileage/"+testUserId)
										.characterEncoding("UTF-8")
										.contentType(MediaType.APPLICATION_JSON)
										.accept(MediaType.APPLICATION_JSON));
		
			actions.andDo(print())
					.andExpect(status().isOk());
		
		}catch (Exception e) {
			fail("FAILED");
		}
	}

}
