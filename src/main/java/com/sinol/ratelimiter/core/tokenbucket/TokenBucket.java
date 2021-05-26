package com.sinol.ratelimiter.core.tokenbucket;

import com.sinol.ratelimiter.exception.RateLimitExceededException;

/**
 * An implementation of Token bucket algorithm to provide rate limiter
 * functionality. For more information about this algorithm, please visit
 * <a href="https://en.wikipedia.org/wiki/Token_bucket">Token bucket</a>
 *
 */
public class TokenBucket {

	private static final String rateLimitExceededErrorMessage = "Rate limit exceeded. Try again in %d seconds";
	private int maximumRequestCount;
	private long requestWindowInMillis;
	private int availableTokens;
	private long lastRefillTimestamp;

	public TokenBucket(int maximumRequestCount, long requestWindowInMillis) {
		this.maximumRequestCount = maximumRequestCount;
		this.requestWindowInMillis = requestWindowInMillis;
		this.availableTokens = maximumRequestCount;
		this.lastRefillTimestamp = System.currentTimeMillis();
	}

	synchronized public boolean checkRateLimit() throws RateLimitExceededException {
		refill();
		if (availableTokens < 1) {
			long timeToNextRefill = (lastRefillTimestamp + requestWindowInMillis) - System.currentTimeMillis();
			// throw new RateLimitExceededException(rateLimitExceededErrorMessage.formatted(timeToNextRefill / 1000));
		} else {
			availableTokens--;
			return true;
		}
	}

	private void refill() {
		long currentTimeMillis = System.currentTimeMillis();
		if (currentTimeMillis - lastRefillTimestamp >= requestWindowInMillis) {
			this.availableTokens = maximumRequestCount;
			this.lastRefillTimestamp = currentTimeMillis;
		}
	}

}
