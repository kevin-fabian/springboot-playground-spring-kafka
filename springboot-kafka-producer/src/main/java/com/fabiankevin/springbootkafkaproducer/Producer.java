package com.fabiankevin.springbootkafkaproducer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("v1/greetings")
public class Producer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping("/{name}")
    public void produceMessage(@PathVariable String name){
        log.info("Producing message");
        kafkaTemplate.send("TEST-sample-topic-4", new Greetings(name, "hello world"));
//        kafkaTemplate.send("TEST-sample-topic-4", "test");

    }
}
