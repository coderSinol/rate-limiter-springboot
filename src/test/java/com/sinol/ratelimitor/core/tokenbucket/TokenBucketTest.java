package com.sinol.ratelimitor.core.tokenbucket;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.sinol.ratelimiter.core.tokenbucket.TokenBucket;
import com.sinol.ratelimiter.exception.RateLimitExceededException;

@Tag("unit")
public class TokenBucketTest {

	@Test
	public void testRateLimit_notExceeded() {
		
		// 100 calls per hour are allowed
		int maximumRequestCount = 100;
		int requestWindowInMillis = 3600000;
		TokenBucket tokenBucket = new TokenBucket(maximumRequestCount,
				requestWindowInMillis);

		long startMillis = System.currentTimeMillis();
		IntStream.range(0, maximumRequestCount).forEach(n -> tokenBucket.checkRateLimit());
		long finishtMillis = System.currentTimeMillis();

		assertTrue((finishtMillis - startMillis) < requestWindowInMillis);
	}

	// @Test
	// public void testRateLimit_exceeded() {
		
	// 	// 10 calls per second are allowed
	// 	int maximumRequestCount = 10;
	// 	int requestWindowInMillis = 60000;
	// 	TokenBucket tokenBucket = new TokenBucket(maximumRequestCount,
	// 			requestWindowInMillis);

	// 	long startMillis = System.currentTimeMillis();
	// 	RateLimitExceededException rateLimitExceededException = assertThrows(RateLimitExceededException.class, () -> {
	// 		IntStream.range(0, 11).forEach(n -> tokenBucket.checkRateLimit());
	// 	});
	// 	long finishtMillis = System.currentTimeMillis();

	// 	assertTrue((finishtMillis - startMillis) < requestWindowInMillis);
	// 	assertNotNull(rateLimitExceededException);
	// }

	// @Test
	// public void testRateLimit_exceeded_thenWaitToCallAgin() throws InterruptedException {
		
	// 	// 10 calls per second are allowed
	// 	int maximumRequestCount = 10;
	// 	int requestWindowInMillis = 1000;
	// 	TokenBucket tokenBucketRate = new TokenBucket(maximumRequestCount,
	// 			requestWindowInMillis);

	// 	long startMillis = System.currentTimeMillis();
	// 	RateLimitExceededException rateLimitExceededException = assertThrows(RateLimitExceededException.class, () -> {
	// 		IntStream.range(0, 11).forEach(n -> tokenBucketRate.checkRateLimit());
	// 	});
	// 	long finishtMillis = System.currentTimeMillis();

	// 	assertTrue((finishtMillis - startMillis) < requestWindowInMillis);
	// 	assertNotNull(rateLimitExceededException);

	// 	// Retry after 1 second
	// 	Thread.sleep(1000);

	// 	startMillis = System.currentTimeMillis();
	// 	IntStream.range(0, 10).forEach(n -> tokenBucketRate.checkRateLimit());
	// 	finishtMillis = System.currentTimeMillis();
		
	// 	assertTrue((finishtMillis - startMillis) < requestWindowInMillis);
	// }
}
