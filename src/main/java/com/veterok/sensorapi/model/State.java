package com.veterok.sensorapi.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Data
@Document
public class State {
    @Id
    private UUID id = UUID.randomUUID();
    private UUID sensorId;
    private Instant timestamp;
    private Instant previousRequestTime;
    private double windSpeed;
    private double windMaxSpeed;
    private double windMinSpeed;
    private int windDirection;
    private double powerPercentages;
    private double temperature;
}
