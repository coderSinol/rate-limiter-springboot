package com.sinol.ratelimiter.core.tokenbucket;

import java.util.HashMap;
import java.util.Map;

import com.sinol.ratelimiter.core.RateLimiter;
import com.sinol.ratelimiter.exception.RateLimitExceededException;

/**
 * Checks rate limit of incoming requests per API key using Token Bucket
 * algorithm. Create a {@link TokenBucket} for each incoming API key and kept in
 * memory.
 * 
 * This implementation is not suitable when application is running in a cluster
 * with a load balancer. Use {@link RedisTokenBucketRateLimiter} instead
 *
 */
public class InMemoryTokenBucketRateLimiter implements RateLimiter {

	private int maximumRequestCount;

	private long requestWindowInMillis;

	private Map<String, TokenBucket> rateLimiterHolder = new HashMap<>();

	public InMemoryTokenBucketRateLimiter(int maximumRequestCount, long requestWindowInMillis) {
		this.maximumRequestCount = maximumRequestCount;
		this.requestWindowInMillis = requestWindowInMillis;
	}

	@Override
	public boolean checkRateLimit(String apiKey) throws RateLimitExceededException {

		TokenBucket tokenBucketRateLimiter = rateLimiterHolder.get(apiKey);
		if (tokenBucketRateLimiter == null) {
			tokenBucketRateLimiter = new TokenBucket(maximumRequestCount, requestWindowInMillis);
			rateLimiterHolder.put(apiKey, tokenBucketRateLimiter);
		}

		return tokenBucketRateLimiter.checkRateLimit();
	}

}
