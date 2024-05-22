package com.micro.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactoryFriend;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircuitBreakerController {

	private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
	
	@GetMapping("/retry-api")
	@Retry(name = "retry", fallbackMethod="retryHardcodedResponse")
	public String sampleApi() {
		logger.warn("sample api hitted");
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
	//	return "Sample API";
	}
	
	public String retryHardcodedResponse(Exception ex) {
		logger.info("retry hardCOded reponse generated");
		return "retry hardCoded Response";
	}
	
	@GetMapping("/circuit-breaker-api")
	//@CircuitBreaker(name ="default", fallbackMethod="circuitHardcodedResponse")
	@RateLimiter(name="circuitRateLimiter")
	//no of services allowed per second
	//2 request per 10s
	public String circuitApi() {
		return "RateLimiter called";
	}
	
	
	public String circuitHardcodedResponse(Exception ex) {
		logger.info("retry hardCOded reponse generated");
		return "retry hardCoded Response";
	}
	
}
