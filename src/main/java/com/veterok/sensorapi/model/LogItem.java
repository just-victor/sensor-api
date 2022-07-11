package com.veterok.sensorapi.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Data
@Document(collection = "logs")
public class LogItem {
    UUID sensorId;
    Instant time;
    String log;
}
