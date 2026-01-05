package com.fabiankevin.springkafkatransactional.models;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Builder
@Value
public class PlanetEvent {
    private UUID id;
    private String name;
    private String status;
    private Double weight;
    private Instant createdDate;
}
