package com.sinol.ratelimitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.sinol.ratelimiter.RatelimiterApplication;
import com.sinol.ratelimiter.controller.SampleController;

@Tag("integration")
@WebMvcTest(SampleController.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { RatelimiterApplication.class })
class RatelimitorApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testGetRequest_rateLimitNotExceeded() throws Exception {
		IntStream.range(0, 100).forEach(n -> {
			MvcResult result;
			try {
				result = mockMvc.perform(get("/ratelimit").header("X-api-key", "within-rate-limit"))
						.andExpect(status().isOk()).andReturn();
				assertNotNull(result);
			} catch (Exception e) {
				assertNull(e);
			}
		});
	}

	// @Test
	// void testGetRequest_rateLimitExceeded() throws Exception {
		
	// 	// First 100 calls will be successful
	// 	IntStream.range(0, 100).forEach(n -> {
	// 		MvcResult result;
	// 		try {
	// 			result = mockMvc.perform(get("/ratelimit").header("X-api-key", "exceed-rate-limit"))
	// 					.andExpect(status().isOk()).andReturn();
	// 			assertNotNull(result);
	// 		} catch (Exception e) {
	// 			assertNull(e);
	// 		}
	// 	});
		
	// 	// The next call will fail with rate limit exceeded
	// 	MvcResult result = mockMvc.perform(get("/ratelimit").header("X-api-key", "exceed-rate-limit"))
	// 				.andExpect(status().is4xxClientError()).andReturn();
	// 	assertEquals(HttpStatus.TOO_MANY_REQUESTS.value(), result.getResponse().getStatus());
	// }

}
