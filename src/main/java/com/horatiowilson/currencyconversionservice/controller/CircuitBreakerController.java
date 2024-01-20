package com.horatiowilson.currencyconversionservice.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
    @RateLimiter(name = "default")
    @Retry(name = "sample-api", fallbackMethod = "hardcodedResponse")
    public String sampleApi() {
        logger.info("Sample API call received");
        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url",
                String.class);
        return forEntity.getBody();
    }

    @GetMapping("/sample-api-circuit-breaker")
    @RateLimiter(name = "default")
    @CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    public String sampleApiCircuitBreaker() {
        logger.info("Sample API circuit breaker call received");
        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url",
                String.class);
        return forEntity.getBody();
    }

    //Every period of 10s, maximum of 2 requests is permitted
    @GetMapping("/sample-api-rate-limiting")
    @RateLimiter(name = "sample-api-rate-limiting")
    public String sampleApiRateLimited() {
        logger.info("Sample API rate limited call received");
        return "Sample API rate limiting";
    }

    @GetMapping("/sample-api-bulkhead")
    @Bulkhead(name = "sample-api-bulkhead")
    public String sampleApiBulkhead() {
        logger.info("Sample API bulkhead call received");
        return "Sample API bulkhead";
    }

    private String hardcodedResponse(Exception ex) {
        logger.error(ex.getMessage());
        return "fallback-response";
    }
}
