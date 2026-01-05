package com.fabiankevin.springbootspringkafkastream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringbootSpringKafkaStreamApplicationTests {
	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	void actuatorHealth_shouldReturn200() {
		ResponseEntity<String> forEntity = testRestTemplate.getForEntity("/actuator/health", String.class);
		assertTrue(forEntity.getStatusCode().is2xxSuccessful());
	}
}
