package com.fabiankevin.springbootkafkaavro.web;

import com.fabiankevin.EmployeeSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("employees")
@Slf4j
public class EmployeeController {

    @Autowired
    private KafkaTemplate<String, EmployeeSchema> kafkaTemplate;

    @GetMapping("/{name}")
    public void publishRandomEmployee(@PathVariable String name) {
        EmployeeSchema employee = new EmployeeSchema();
        Random random = new Random();
        employee.setId(random.nextInt());
        employee.setFirstName(name);
        employee.setLastName("random last name");
        employee.setGender("MALE");
        employee.setEmail("someemail@test.com");
        kafkaTemplate.send("employees", employee);
        log.info("Employee sent={}", employee);
    }
}
