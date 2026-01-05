package com.fabiankevin.springboot_micrometer_kafka;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootTest
@EmbeddedKafka(kraft = true)
class SpringbootMicrometerKafkaApplicationTests {

	@Test
	void contextLoads() {

	}

}
