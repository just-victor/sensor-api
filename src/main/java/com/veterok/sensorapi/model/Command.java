package com.veterok.sensorapi.model;

import com.veterok.sensorapi.enums.CommandStatus;
import com.veterok.sensorapi.enums.Commands;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Data
@Document
public class Command {
    @Id
    private UUID id;
    private UUID sensorId;
    private Commands command;
    private CommandStatus status;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant executedAt;
}
