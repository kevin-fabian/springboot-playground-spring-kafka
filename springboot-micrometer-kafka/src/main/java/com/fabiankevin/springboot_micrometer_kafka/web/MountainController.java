package com.fabiankevin.springboot_micrometer_kafka.web;

import com.fabiankevin.springboot_micrometer_kafka.models.Mountain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/mountains")
@RequiredArgsConstructor
@Slf4j
public class MountainController {

    private final KafkaTemplate<String, Mountain> kafkaTemplate;

    @PostMapping
    public void sendMountain(@RequestBody Mountain mountain) throws InterruptedException {
        log.info("Sending {}", mountain);
        kafkaTemplate.send("mountains", mountain);
    }

    @PostMapping("/random/{count}")
    public void sendMountain(@PathVariable int count) throws InterruptedException {
        IntStream.range(0, count).boxed()
                .map(i -> new Mountain(UUID.randomUUID().toString(), "Philippines", Instant.now()))
                .forEach(mountain -> {
                    kafkaTemplate.send("mountains", mountain);
                });
        log.info("{} mountains have been sent", count);
    }
}
