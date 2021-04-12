package com.sinol.ratelimiter.exception;

public class BadRequest extends RuntimeException {

	private static final long serialVersionUID = 4763286182598250112L;

	public BadRequest(String message) {
		super(message);
	}
}
