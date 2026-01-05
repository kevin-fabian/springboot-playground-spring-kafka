package com.fabiankevin.springboot_consumer_batch_kafka.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherRequest {
    private List<String> weathers;
}
