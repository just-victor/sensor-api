package com.veterok.sensorapi.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Document
public class Sensor {
    @Id
    private UUID id;
    private String title;
    private UUID userId;
    private double longitude;
    private double latitude;
    private int pingPeriodInMin;
    private int nightPingPeriodInMin;
    private LocalTime nightStart;
    private LocalTime nightEnd;
    @LastModifiedDate
    private Instant lastPingTime;
    private Instant createdTime = Instant.now();
    private Instant registeredTime;
}
