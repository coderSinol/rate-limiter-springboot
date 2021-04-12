package com.sinol.ratelimiter.exception;

public class RateLimitExceededException extends RuntimeException {

	private static final long serialVersionUID = 6971316376612506479L;

	public RateLimitExceededException(String message) {
		super(message);
	}

}
