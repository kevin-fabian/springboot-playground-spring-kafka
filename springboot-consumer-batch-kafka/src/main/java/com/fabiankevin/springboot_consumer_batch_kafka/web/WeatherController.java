package com.fabiankevin.springboot_consumer_batch_kafka.web;

import com.fabiankevin.springboot_consumer_batch_kafka.web.dto.WeatherRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("weathers")
@RestController
@RequiredArgsConstructor
@Slf4j
public class WeatherController {
    private final KafkaTemplate<String, String> kafkaTemplate;


    @PostMapping
    public void produceWeather(@RequestBody WeatherRequest request) {
        for (String weather : request.getWeathers()) {
            kafkaTemplate.send("weathers", weather);
        }
        log.info("Weathers have been sent.");
    }
}
