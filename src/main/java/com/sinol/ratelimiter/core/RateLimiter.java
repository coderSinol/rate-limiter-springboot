package com.sinol.ratelimiter.core;

import com.sinol.ratelimiter.exception.RateLimitExceededException;

/**
 * 
 * Allows implementations of rate limiting algorithms
 * 
 */
public interface RateLimiter {

	/**
	 * Check a particular requester which is identified by an API key has not
	 * exceeded the allowed rate limit for a given time window.
	 * 
	 * @return true if rate limit is not exceeded
	 * @throws {@link RateLimitExceededException} if rate limit is exceeded
	 */
	boolean checkRateLimit(String apiKey) throws RateLimitExceededException;

}
