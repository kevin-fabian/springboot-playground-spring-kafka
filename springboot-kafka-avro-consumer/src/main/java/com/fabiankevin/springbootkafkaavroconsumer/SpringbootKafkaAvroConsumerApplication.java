package com.fabiankevin.springbootkafkaavroconsumer;

import com.fabiankevin.EmployeeSchema;
import com.fabiankevin.springbootkafkaavroconsumer.exceptions.ShouldRetryOnlyBlockingException;
import com.fabiankevin.springbootkafkaavroconsumer.exceptions.ShouldRetryViaBothException;
import com.fabiankevin.springbootkafkaavroconsumer.exceptions.ShouldSkipBothRetriesException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.retrytopic.RetryTopicHeaders;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

@SpringBootApplication
@Slf4j
public class SpringbootKafkaAvroConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootKafkaAvroConsumerApplication.class, args);
	}

	@KafkaListener(topics = "employees")
	public void consumeEmployeeDetails(
			@Header(KafkaHeaders.OFFSET) int offset,
			@Header(name = RetryTopicHeaders.DEFAULT_HEADER_ATTEMPTS, required = false) Integer attempt,
			EmployeeSchema employeeDetails  ) {
		log.info("object={}, offset={}, attempt={}",
				employeeDetails,
				offset,
				attempt);
//		VALUE_DESERIALIZER_CLASS;
//		KEY_DESERIALIZER_CLASS_CONFIG

		if("robin".equalsIgnoreCase(employeeDetails.getFirstName().toString())) {
			throw new ShouldRetryOnlyBlockingException();
		}
		if("robin2".equalsIgnoreCase(employeeDetails.getFirstName().toString())) {
			throw new ShouldRetryViaBothException();
		}
		if("robin1".equalsIgnoreCase(employeeDetails.getFirstName().toString())){
			throw new ShouldSkipBothRetriesException();
		}
		log.info("{} has been received successfully.", employeeDetails.getFirstName());
	}

	public void dlt(String in) {
		log.info("DLT Received= {}", in);
	}
}
