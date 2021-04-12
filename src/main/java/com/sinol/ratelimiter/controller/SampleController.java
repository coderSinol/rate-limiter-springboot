package com.sinol.ratelimiter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinol.ratelimiter.core.RateLimitValidator;
import com.sinol.ratelimiter.core.RateLimiterType;

/**
 * Simple controller to test the functionality of the rate limiter module
 */

@RestController
@RequestMapping("/ratelimit")
public class SampleController {

	private RateLimitValidator rateLimiter;
	
	public SampleController() {
		rateLimiter = RateLimitValidator.create(RateLimiterType.IN_MEMORY_TOKEN_BUCKET, 100, 3600000);
	}
	
	
	@GetMapping
	public String hello(@RequestHeader("X-api-key") String apiKey) throws Exception {
		rateLimiter.validate(apiKey);
		return "Hi there!";
	}
}
