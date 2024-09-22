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
    private Instant timestamp = Instant.now();
//    private Instant previousRequestTime;
    private float windSpeed;
    private float windMaxSpeed;
    private float windMinSpeed;
    private int windDirection;
    private float powerPercentages;
    private float temperature;
    private float latitude;
    private float longitude;
    private int satellites;
}
