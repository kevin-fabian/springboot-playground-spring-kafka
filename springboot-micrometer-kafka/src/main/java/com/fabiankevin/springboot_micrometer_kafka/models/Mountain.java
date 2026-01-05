package com.fabiankevin.springboot_micrometer_kafka.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mountain {
    private String name;
    private String address;
    private Instant createdDate;
}
