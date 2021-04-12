package com.sinol.ratelimiter.core.tokenbucket;

import com.sinol.ratelimiter.core.RateLimiter;
import com.sinol.ratelimiter.exception.RateLimitExceededException;

/**
 * Checks rate limit of incoming requests per API key using Token Bucket
 * algorithm. Create a {@link TokenBucket} record for each API key and kept them
 * in external Redis cache.
 *
 */
public class RedisTokenBucketRateLimiter implements RateLimiter {

	private int maximumRequestCount;

	private long requestWindowInMillis;

	public RedisTokenBucketRateLimiter(int maximumRequestCount, long requestWindowInMillis) {
		this.maximumRequestCount = maximumRequestCount;
		this.requestWindowInMillis = requestWindowInMillis;
	}

	@Override
	public boolean checkRateLimit(String apiKey) throws RateLimitExceededException {
		// TODO implement this
		return false;
	}

}
