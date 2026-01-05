package com.fabiankevin.springbootkafkaavro;

import com.fabiankevin.EmployeeSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Slf4j
@EnableScheduling
public class SpringbootKafkaAvroApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootKafkaAvroApplication.class, args);
	}


	@Autowired
	private KafkaTemplate<String, EmployeeSchema> kafkaTemplate;


//	@Scheduled(fixedDelay = 10000)
//	public void produceEmployeeDetails() throws ExecutionException, InterruptedException {
//		EmployeeSchema employee = new EmployeeSchema();
//		employee.setId(1);
//		employee.setFirstName("random first name");
//		employee.setLastName("random last name");
//		employee.setGender("MALE");
//		employee.setEmail("someemail@test.com");
//		kafkaTemplate.send("TEST-employee-details", UUID.randomUUID().toString(), employee);
//		log.info("Employee sent={}", employee);
//	}

}
