package com.sinol.ratelimiter.core;

import org.springframework.util.StringUtils;

import com.sinol.ratelimiter.core.tokenbucket.InMemoryTokenBucketRateLimiter;
import com.sinol.ratelimiter.core.tokenbucket.RedisTokenBucketRateLimiter;
import com.sinol.ratelimiter.exception.BadRequest;
import com.sinol.ratelimiter.exception.RateLimitExceededException;

/**
 * Validates a caller has exceeded the allocated maximum number of request count
 * for a particular time window
 *
 */

public class RateLimitValidator {

	private RateLimiter rateLimiter;

	private RateLimitValidator(RateLimiter rateLimiter) {
		this.rateLimiter = rateLimiter;
	}

	/**
	 * Create a {@code RateLimitValidator} setting up maximum allowed request count,
	 * time window and type of rate limit which has to be used to facilitate rate
	 * limiting
	 * 
	 */
	public static RateLimitValidator create(RateLimiterType rateLimiterType, int maximumRequestCount,
			long requestWindowInMillis) {

		RateLimiter rateLimiter = getRateLimiter(rateLimiterType, maximumRequestCount, requestWindowInMillis);
		RateLimitValidator rateLimiterInterceptor = new RateLimitValidator(rateLimiter);
		return rateLimiterInterceptor;

	}

	/**
	 * Validate a given API key is not exceeded the allocated rate limit
	 * 
	 * @return true if rate is not exceeded
	 * @throws BadRequest                 if provided api key is empty
	 * @throws RateLimitExceededException limit has exceeded
	 */
	public boolean validate(String apiKey) throws BadRequest, RateLimitExceededException {
		if (!StringUtils.hasText(apiKey)) {
			throw new BadRequest("API key is empty");
		}
		return rateLimiter.checkRateLimit(apiKey);
	}

	private static RateLimiter getRateLimiter(RateLimiterType rateLimiterType, int maximumRequestCount,
			long requestWindowInMillis) {

		if (RateLimiterType.REDIS_TOKEN_BUCKET.equals(rateLimiterType)) {
			return new RedisTokenBucketRateLimiter(maximumRequestCount, requestWindowInMillis);
		}
		return new InMemoryTokenBucketRateLimiter(maximumRequestCount, requestWindowInMillis);

	}

}
