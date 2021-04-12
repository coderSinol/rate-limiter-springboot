# Rate limiting Module

This module restricts a particular requester from making too many requests within a particular time window. If a requester makes too many requests, it will return HTTP status 429 with an error message "Rate limit exceeded. Try again in #{n} seconds".

### Run and test locally
```bash
mvn spring-boot:run
```

The default HTTP port is set to 8090

### Usage
* Add the following dependency to the project and update the version

```
		<dependency>
			<groupId>com.sinol</groupId>
			<artifactId>ratelimiter</artifactId>
			<version>LATEST</version>
		</dependency>
```

* Create a RateLimitValidator instance passing the required type of rate limiter, maximumRequestCount and requestWindowInMillis.

Ex: Create RateLimitValidator with the type IN_MEMORY_TOKEN_BUCKET and maximum request count 100 per hour

```
RateLimitValidator rateLimiterInterceptor = RateLimitValidator.create(RateLimiterType.IN_MEMORY_TOKEN_BUCKET, 100, 3600000);
```

* Pass API key to the RateLimitValidator

```
rateLimiterInterceptor.validate(apiKey);
```

# How to use in your code
There is no one specific way to use RateLimitValidator in your code.

* Use directly in a controller
	
```
	@GetMapping
	public String hello(@RequestHeader("X-api-key") String apiKey) throws Exception {
		rateLimiter.validate(apiKey);
		return "Hi there!";
	}
```

* Use in preHandle() in a Spring Interceptor or in a Filter. This allows validating rate limit before executing the actual controller method

* Use in an Aspect. In this case, you will need to introduce AspectJ to your application

# Limitations

* Currently, the rate limiter module is implemented only Token bucket algorithm with in-memory cache. This can be extended by implementing another algorithm. Please see RateLimit.java interface for more details.

* Current implementation is not suitable when an application running in a cluster. If application running in a cluster, the type REDIS_TOKEN_BUCKET should be used. (The complete implementation of this yet to be done) 



