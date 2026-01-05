package com.fabiankevin.springbootkafkaavroconsumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DltHandler {

    private void handle(String in){
        log.info("dlt received {}", in);
    }
}
