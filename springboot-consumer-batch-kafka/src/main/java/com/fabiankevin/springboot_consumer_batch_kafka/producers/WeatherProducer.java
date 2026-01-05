package com.fabiankevin.springboot_consumer_batch_kafka.producers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Slf4j
@RequiredArgsConstructor
public class WeatherProducer {

//
//    private final KafkaTemplate<String, String> kafkaTemplate;
//
//
//
//    @Scheduled(initialDelay = 5000l, fixedDelay = 120000l)
//    public void sendWeather() {
//        List<String> weatherList = List.of("Windy", "Sunny", "Clouldy", "Partly Sunny", "Stormy", "Rainy", "Rainbow", "Foggy", "Snowy");
//
////        a
//        log.info("Weather sent.");
//    }
}
