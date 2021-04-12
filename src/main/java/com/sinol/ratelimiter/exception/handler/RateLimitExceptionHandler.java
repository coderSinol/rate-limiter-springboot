package com.sinol.ratelimiter.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sinol.ratelimiter.exception.BadRequest;
import com.sinol.ratelimiter.exception.RateLimitExceededException;

@ControllerAdvice
public class RateLimitExceptionHandler {

	@ExceptionHandler({ RateLimitExceededException.class, BadRequest.class })
	public final ResponseEntity<Object> handleException(Exception ex) throws Exception {
		
		if (ex instanceof BadRequest) {
			return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		if (ex instanceof RateLimitExceededException) {
			return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.TOO_MANY_REQUESTS);
		}
		
		throw ex;
	}

}
