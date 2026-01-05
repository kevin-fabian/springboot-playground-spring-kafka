package com.fabiankevin.springkafkatransactional.web;

import com.fabiankevin.springkafkatransactional.models.PlanetEvent;
import com.fabiankevin.springkafkatransactional.persistence.PlanetEntity;
import com.fabiankevin.springkafkatransactional.persistence.PlanetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("planets")
@Slf4j
public class PlanetController {

    @Autowired
    private KafkaTemplate<String, PlanetEvent> kafkaTemplate;
    @Autowired
    private PlanetRepository planetRepository;

    @GetMapping("/{name}")
    @Transactional(transactionManager = "transactionManager")
    PlanetEntity test(@PathVariable String name){

        PlanetEntity entity = PlanetEntity.builder()
//                .id(UUID.randomUUID())
                .createdDate(Instant.now())
                .name(name)
                .weight(3.2)
                .status("PUBLISHED")
                .build();

        PlanetEntity entity1 = planetRepository.saveAndFlush(entity);

        kafkaTemplate.send("test-planets", entity.getName(), PlanetEvent.builder()
                .id(entity1.getId())
                .createdDate(entity1.getCreatedDate())
                .name(entity1.getName())
                .status(entity1.getStatus())
                .weight(entity1.getWeight())
                .build());
        log.info("published.");

        if("test".equalsIgnoreCase(name)){
            throw new RuntimeException();
        }
        return entity1;
    }
}
