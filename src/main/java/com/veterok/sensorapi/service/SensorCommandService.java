package com.veterok.sensorapi.service;

import com.veterok.sensorapi.enums.Commands;
import com.veterok.sensorapi.response.ResponseCommand;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SensorCommandService {
    private final Map<UUID, Commands> sensorCommands = new ConcurrentHashMap<>();

    public ResponseCommand getCommand(UUID sensorId) {
        Commands sensorCommand = sensorCommands.getOrDefault(sensorId, Commands.ALL_IS_OK);

        return ResponseCommand.of(sensorCommand);
    }
}
